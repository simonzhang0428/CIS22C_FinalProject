import java.util.Scanner;

public class MedPlan {
    public static void main(String[] args) {
        final int DATABASE_SIZE = 25;

        BST<Doctor> bst1 = new BST<>();
        BST<Doctor> bst2 = new BST<>();
        Hash<Doctor> ht = new Hash<>(DATABASE_SIZE * 2); // thumb rule
        Scanner keyboardInput = new Scanner(System.in);
        boolean finished = false;
        Scanner fileInput;
        // Populate database here

        System.out.println("Welcome to MedPlan!\n");

        while (!finished) {
            System.out.println("Please select from one of the following options:\n");

            System.out.println("A: Add new doctor");
            System.out.println("D: Delete a doctor");

            // two options: by primary(unique) key, by secondary
            System.out.println("S: Search for a doctor");

            // three options: unsorted(hash table), sorted1(by primary key), sorted2(by secondary key)
            System.out.println("P: Print doctors");
            System.out.println("W: Write MedPlan database to file");
            System.out.println("Q: Quit\n");

            System.out.print("Enter your choice: ");
            String command = keyboardInput.nextLine();

            switch (command) {
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

                    System.out.print("Enter doctor's gender: ");
                    String gender = keyboardInput.nextLine();

                    System.out.print("Is doctor accepting patients? (yes/no): ");
                    boolean isAccepting = keyboardInput.nextLine().equals("yes");

                    // After we got all the data, create a doctor object and save
                    // to BST1, BST2, and Hash Table (ht)
                    Doctor doc = new Doctor(name, specialty, clinic, NPI, gender, isAccepting);
                    ht.insert(doc); // inserting a doctor into a hash table

                    // insert into bst1 (comparing by primary (npi) key)
                    // Comparable is set to comparing by NPI by default! (in constructor)
                    bst1.insert(doc);

                    // after we inserted a doctor object by npi, change comparable method
                    // and insert to bst2 comparing by name, not NPI !
                    doc.setComparable(doc.compareBySecondaryKey());
                    bst2.insert(doc); // insert into bst2 (comparing by secondary (name) key)

                    System.out.println("\n" + name + " has been added!\n");

                    // There should also be a way of adding comparable insurance plans

                    // After we added all this, add doctor object to both BST's, and HT
                    break;
                }
                case "D": {
                    System.out.println("\nRemoving a Doctor!\n"); // removing a doctor (by name and NPI maybe??)

                    System.out.print("Enter doctor's name: ");
                    String name = keyboardInput.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    String NPI = keyboardInput.nextLine();

                    Doctor doc = new Doctor(name, NPI);

                    if (ht.search(doc) == -1) {
                        System.out.println("Doctor " + name + " with NPI " + NPI + " is not in the database, can't remove!\n");
                    } else {
                        ht.remove(doc);
                        bst1.remove(doc);
                        doc.setComparable(doc.compareBySecondaryKey());
                        bst2.remove(doc);
                        System.out.println("Doctor " + name + " is removed from the database!\n");
                    }

                    // delete or deal with a case if doctor is not in the database
                    break;
                }
                case "S": {
                    System.out.println("\nSearching For a Doctor!\n"); // searching a doctor (by name and NPI maybe??)

                    System.out.print("Enter doctor's name: ");
                    String name = keyboardInput.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    String NPI = keyboardInput.nextLine();

                    // This doctor object contains only NPI, so we could find it it hash table
                    Doctor dummy = new Doctor();
                    dummy.setNPI(NPI);

                    boolean isPresent = ht.search(dummy) != -1;

                    if (isPresent) {
                        System.out.println(name + " with NPI " + NPI + " is in the database!");
                    } else {
                        System.out.println(name + " with NPI " + NPI + " is not in the database!");
                    }

                    break;
                }
                case "P": {
                    System.out.println("\nPrinting data!\n");
                    System.out.println("Please select one of the following options:\n");
                    System.out.println("S1: Sorted by NPI number");
                    System.out.println("S2: Sorted by specialty");
                    System.out.println("U: Unsorted");

                    String choice = keyboardInput.nextLine();

                    switch (choice) {
                        case "S1": {
                            System.out.println();
                            System.out.println("There are " + bst1.getSize() + " doctors in the database\n");
                            bst1.inOrderPrint();
                            break;
                        }
                        case "S2": {
                            System.out.println();
                            System.out.println("There are " + bst1.getSize() + " doctors in the database\n");
                            bst2.inOrderPrint();
                            break;
                        }
                        case "U": {
                            System.out.println();
                            System.out.println(ht);
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
                    save(path);
                    break;
                }
                case "Q": {
                    finished = true;
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
        keyboardInput.close();
    }

    private static void save(String path) {
        // save data somewhere
    }
}