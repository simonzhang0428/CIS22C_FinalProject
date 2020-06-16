import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MedPlan {

    private final int DATABASE_SIZE = 27;

    private BST<Doctor> bst1 = new BST<>(true); // contains objects compared by a primary key (NPI)
    private BST<Doctor> bst2 = new BST<>(false); // contains objects compared by a secondary key (Name)
    private Hash<Doctor> ht = new Hash<>(DATABASE_SIZE * 2); // thumb rule
    private boolean finished = false;

    public static void main(String[] args) throws FileNotFoundException {
        MedPlan medPlan = new MedPlan();

        Scanner keyboardInput = new Scanner(System.in);

        File file = new File("doctors.txt");
        Scanner fileInput = new Scanner(file);

        // Populate database here
        while (fileInput.hasNextLine()) {
            String name = fileInput.nextLine();
            String specialty = fileInput.nextLine();
            String clinic = fileInput.nextLine();
            String NPI = fileInput.nextLine();
            String gender = fileInput.nextLine();
            String isAccepting = fileInput.nextLine();

            Doctor doctor = new Doctor(name, specialty, clinic, NPI, gender, isAccepting.equals("true"));
            medPlan.ht.insert(doctor);
            medPlan.bst1.insert(doctor);
            medPlan.bst2.insert(doctor);

            // Deal with the empty line between data
            if (fileInput.hasNext()) {
                fileInput.nextLine();
            }
        }

        System.out.println("Welcome to MedPlan!\n");

        // While the users doesn't prompt "Q" in the console to quit, request further commands
        while (!medPlan.finished) {
            System.out.println("Please select from one of the following options:\n");

            System.out.println("A: Add new doctor");
            System.out.println("D: Delete a doctor");

            // two options: by primary(unique) key, by secondary
            System.out.println("S: Search for a doctor");

            // three options: unsorted(hash table), sorted1(by primary key), sorted2(by secondary key)
            System.out.println("P: Print doctors");
            System.out.println("W: Write MedPlan database to a file");
            System.out.println("Q: Quit\n");

            System.out.print("Enter your choice: ");
            String command = keyboardInput.nextLine();

            switch (command) {

                // Adding a new doctor object
                case "A": {
                    System.out.println("\nAdding New Doctor!\n"); // adding new doctor

                    System.out.print("Enter doctor's name: ");
                    String name = keyboardInput.nextLine();

                    System.out.print("Enter doctor's specialty: ");
                    String specialty = keyboardInput.nextLine();

                    System.out.print("Enter doctor's clinic: ");
                    String clinic = keyboardInput.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    String NPI = keyboardInput.nextLine();
                    while (containsCharacters(NPI)) {
                        System.out.println("\nNPI should contain only integers!\n");

                        System.out.print("Enter doctor's NPI: ");
                        NPI = keyboardInput.nextLine();
                    }

                    System.out.print("Enter doctor's gender: ");
                    String gender = keyboardInput.nextLine();

                    System.out.print("Is doctor accepting patients? (yes/no): ");
                    boolean isAccepting = keyboardInput.nextLine().equals("yes");

                    // After we got all the data, create a doctor object and save
                    // to BST1, BST2, and Hash Table (ht)
                    Doctor doc = new Doctor(name, specialty, clinic, NPI, gender, isAccepting);
                    medPlan.ht.insert(doc); // inserting a doctor into a hash table

                    // insert into bst1 (comparing by primary (npi) key)
                    // Comparable is set to comparing by NPI by default! (in constructor)
                    medPlan.bst1.insert(doc);

                    // after we inserted a doctor object by npi, change comparable method
                    // and insert to bst2 comparing by name, not NPI !
                    medPlan.bst2.insert(doc); // insert into bst2 (comparing by secondary (name) key)

                    System.out.println("\n" + name + " has been added!\n");

                    // There should also be a way of adding comparable insurance plans

                    // After we added all this, add doctor object to both BST's, and HT
                    break;
                }

                // Removing a doctor
                case "D": {
                    System.out.println("\nRemoving a Doctor!\n"); // removing a doctor (by name and NPI maybe??)

                    System.out.print("Enter doctor's name: ");
                    String name = keyboardInput.nextLine();

                    // Create a doctor with a name and a dummy NPI
                    Doctor doc = new Doctor(name, "0000000000");

                    // Since name is a secondary key, there can be several matches
                    ArrayList<Doctor> matches = medPlan.bst2.findAllMatches(doc);

                    System.out.println(matches.size() + " doctors found!\n");

                    // If no users found -> there is nothing to remove
                    if (matches.size() != 0) {
                        for (int i = 0; i < matches.size(); i++) {
                            System.out.println((i + 1) + ". " + matches.get(i).getName() + " with NPI: " + matches.get(i).getNpi());
                        }

                        int index;

                        if (matches.size() == 1) {
                            index = 0;
                        } else {
                            System.out.print("Please type which doctor you want to remove by prompting the number from the list (1 - " + matches.size() + "): ");
                            index = Integer.parseInt(keyboardInput.nextLine()) - 1;

                            while (index < 0 || index > matches.size() - 1) {
                                System.out.println("\nIndex should be between 1 and " + matches.size() + "!");
                                System.out.print("Enter your choice: ");
                                index = Integer.parseInt(keyboardInput.nextLine()) - 1;
                            }
                        }

                        Doctor removedDoctor = matches.get(index);

                        System.out.println("\nThe following doctor will be removed!\n");
                        System.out.println(removedDoctor);

                        medPlan.ht.remove(removedDoctor);
                        medPlan.bst1.remove(removedDoctor);
                        medPlan.bst2.remove(removedDoctor);
                    } else {
                        System.out.println("There are no doctors with name " + name + " that can be removed from the database!\n");
                    }

                    break;
                }

                // Searching for a doctor
                case "S": {
                    System.out.println("\nSearching For a Doctor!\n"); // searching a doctor (by name and NPI maybe??)
                    System.out.println("Please select one of the following options:\n");
                    System.out.println("P: Search by a primary key (NPI)");
                    System.out.println("S: Search by a secondary key (name)");

                    System.out.print("\nEnter your choice: ");
                    String choice = keyboardInput.nextLine();

                    switch (choice) {
                        // Searching for a doctor by a primary key (NPI)
                        case "P": {
                            System.out.print("Enter doctor's NPI: ");
                            String NPI = keyboardInput.nextLine();
                            while (containsCharacters(NPI)) {
                                System.out.println("\nNPI should contain only integers!\n");

                                System.out.print("Enter doctor's NPI: ");
                                NPI = keyboardInput.nextLine();
                            }

                            // This doctor object contains only NPI, so we could find it it hash table
                            Doctor dummy = new Doctor();
                            dummy.setNPI(NPI);

                            Doctor dbDoctor = medPlan.ht.search(dummy);

                            System.out.println();

                            if (dbDoctor != null) {
                                System.out.println("A doctor with NPI " + NPI + " is in the database!\n");
                                System.out.println(dbDoctor);
                            } else {
                                System.out.println("A doctor with NPI " + NPI + " is not in the database!\n");
                            }

                            break;
                        }

                        // Searching for a doctor by a secondary key (Name)
                        case "S": {
                            System.out.print("Enter doctor's name: ");
                            String name = keyboardInput.nextLine();

                            Doctor dummy = new Doctor();
                            dummy.setName(name);

                            ArrayList<Doctor> docs = medPlan.bst2.findAllMatches(dummy);

                            if (docs.size() == 0) {
                                System.out.println("\nNo doctors with name " + name + " were found\n");
                            } else {
                                System.out.println("\n" + docs.size() + " doctor(s) with name " + name + " were found\n");
                                for (Doctor doc : docs) {
                                    System.out.println(doc);
                                }
                            }

                            break;
                        }
                        default: {
                            System.out.println("\nInvalid Selection!\n");
                            break;
                        }
                    }

                    break;
                }
                case "P": {
                    System.out.println("\nPrinting data!\n");
                    System.out.println("Please select one of the following options:\n");
                    System.out.println("U: Unsorted");
                    System.out.println("S1: Sorted by NPI number");
                    System.out.println("S2: Sorted by name\n");

                    System.out.print("Enter your choice: ");
                    String choice = keyboardInput.nextLine();

                    switch (choice) {
                        case "U": {
                            System.out.println();
                            System.out.println("There are " + medPlan.ht.getNumElements() + " doctors in the database\n");
                            System.out.println(medPlan.ht);
                            break;
                        }
                        case "S1": {
                            System.out.println();
                            System.out.println("There are " + medPlan.bst1.getSize() + " doctors in the database\n");
                            medPlan.bst1.inOrderPrint();
                            break;
                        }
                        case "S2": {
                            System.out.println();
                            System.out.println("There are " + medPlan.bst2.getSize() + " doctors in the database\n");
                            medPlan.bst2.inOrderPrint();
                            break;
                        }
                        default: {
                            System.out.println("\nInvalid Selection!\n");
                            break;
                        }
                    }

                    break;
                }
                case "W": {
                    System.out.println("\nWriting data to a hard drive!\n");
                    System.out.print("Please specify where you want to save the data: ");
                    String path = keyboardInput.nextLine();

                    // call save function
                    save(medPlan.ht, path);
                    break;
                }
                case "Q": {
                    medPlan.finished = true;
                    break;
                }
                default: {
                    System.out.println("\nInvalid Selection!\n");
                    break;
                }
            }
        }

        // end
        // save before quitting - required by professor -> call save function
        save(medPlan.ht, "autoSave.txt");
        keyboardInput.close();

        System.out.println("Goodbye!");
    }

    private static void save(Hash<Doctor> hash, String path) throws FileNotFoundException {
        ArrayList<Doctor> docs = hash.getAllObjects();
        String result = getStringData(docs);

        System.out.println("\nThe following data of " + docs.size() + " doctors will be saved:\n");
        System.out.println(result);

        PrintStream out = new PrintStream(new FileOutputStream(path));
        out.print(result);
    }

    private static String getStringData(ArrayList<Doctor> docs) {
        StringBuilder sb = new StringBuilder();

        for (Doctor doc : docs) {
            sb.append(doc.getName()).append("\n");
            sb.append(doc.getSpecialty()).append("\n");
            sb.append(doc.getClinic()).append("\n");
            sb.append(doc.getNpi()).append("\n");
            sb.append(doc.getGender()).append("\n");
            sb.append(doc.isAcceptingNewPts()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    private static boolean containsCharacters(String npi) {
        for (char c : npi.toCharArray()) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }
}