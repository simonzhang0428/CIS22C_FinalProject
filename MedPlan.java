import java.util.Scanner;

public class MedPlan {
    public static void main(String[] args) {
        final int DATABASE_SIZE = 25;

        BST<Doctor> bst1 = new BST<>();
        BST<Doctor> bst2 = new BST<>();
        Hash<Doctor> ht = new Hash<>(DATABASE_SIZE * 2); // thumb rule
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
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
            System.out.println("W: Write data to file");
            System.out.println("Q: Quit\n");

            System.out.print("Enter your choice: ");
            String command = scanner.nextLine().toUpperCase();

            switch (command) {
                case "A": {
                    System.out.println("\nAdding New Doctor!\n"); // adding new doctor

                    System.out.print("Enter doctor's name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter doctor's specialty: ");
                    String specialty = scanner.nextLine();

                    System.out.print("Enter doctor's clinic: ");
                    String clinic = scanner.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    String NPI = scanner.nextLine();

                    System.out.print("Enter doctor's gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Is doctor accepting patients? (yes/no): ");
                    boolean isAccepting = scanner.nextLine().equals("yes");

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

                    System.out.println("Hash Table: " + ht.getNumElements());
                    System.out.println("BST1: " + bst1.getSize());
                    System.out.println("BST2: " + bst2.getSize());

                    // There should also be a way of adding comparable insurance plans

                    // After we added all this, add doctor object to both BST's, and HT
                    break;
                }
                case "D": {
                    System.out.println("\nRemoving a Doctor!\n"); // removing a doctor (by name and NPI maybe??)

                    System.out.print("Enter doctor's name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    int NPI = Integer.parseInt(scanner.nextLine());

                    // delete or deal with a case if doctor is not in the database
                }
                case "S": {
                    System.out.println("\nSearching For a Doctor!\n"); // searching a doctor (by name and NPI maybe??)

                    System.out.print("Enter doctor's name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter doctor's NPI: ");
                    int NPI = Integer.parseInt(scanner.nextLine());
                    break;
                }
                case "P": {
                    System.out.println("\nPrinting data!\n");
                    System.out.println("Please select one of the following options:\n");
                    System.out.println("S1: Sorted by specialty");
                    System.out.println("S2: Sorted by NPI number");
                    System.out.println("U: Unsorted");

                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "S1": {
                            break;
                        }
                        case "S2": {
                            break;
                        }
                        case "U": {
                            break;
                        }
                    }

                    // call either bst or ht depending on a choice above
                }
                case "W": {
                    System.out.println("\nWriting data to a hard drive!\n");
                    System.out.print("Please specify where you want to save the data: ");
                    String path = scanner.nextLine();

                    // call save function
                }
                case "Q": {
                    finished = true;
                    break;
                }
            }
        }

        System.out.println("Test");
        System.out.println("Hash Table");
        System.out.println("-------------------");
        System.out.println(ht);
        System.out.println("BST 1");
        System.out.println("-------------------");
        bst1.inOrderPrint();
        System.out.println("BST 2");
        System.out.println("-------------------");
        bst2.inOrderPrint();

        // end
        // save before quitting - required by professor -> call save function
        scanner.close();
    }

    private static void save() {
        // save data somewhere
    }
}
