import java.util.ArrayList;

public class Doctor implements Comparable<Doctor> {
    private String name;
    private String specialty;
    private String clinic;
    private String npi;
    private String gender;
    private boolean acceptingNewPts;
    private ArrayList<String> acceptedInsurancePlans;
    private Comparable<Doctor> comparable;

    /***CONSTRUCTORS***/

    /**
     * 6 argument constructor for Doctor object
     * Mostly used for add method and text file
     *
     * @param name            the name of the Doctor
     * @param specialty       the specialty of the Doctor
     * @param clinic          the clinic the Doctor works at
     * @param npi             the NPI (National Provider Identifier) of the Doctor
     * @param gender          the gender of the Doctor
     * @param acceptingNewPts true if the Doctor is accepting new patients
     */
    public Doctor(String name, String specialty, String clinic, String npi, String gender,
                  boolean acceptingNewPts) {
        this.name = name;
        this.specialty = specialty;
        this.clinic = clinic;
        this.npi = npi;
        this.gender = gender;
        this.acceptingNewPts = acceptingNewPts;
        this.comparable = compareByPrimaryKey();
        //Initialize ArrayList, will figure out how to add data later
        acceptedInsurancePlans = new ArrayList<>(4);
    }

    /**
     * Default constructor for Doctor object, initializes all variables to default values
     */
    public Doctor() {
        this("Unknown name", "Unknown specialty", "Unknown clinic", "0000000000",
                "Unknown gender", false);
    }

    /**
     * 2 argument constructor for Doctor object
     * Mostly used for remove and search methods
     *
     * @param name the name of the Doctor
     * @param npi  the NPI (National Provider Identifier) of the Doctor
     */
    public Doctor(String name, String npi) {
        this(name, "Unknown specialty", "Unknown clinic", npi,
                "Unknown gender", false);
    }

    /***ACCESSORS***/

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getClinic() {
        return clinic;
    }

    public String getNpi() {
        return npi;
    }

    public String getGender() {
        return gender;
    }

    public boolean isAcceptingNewPts() {
        return acceptingNewPts;
    }

    public ArrayList<String> getCompatibleInsurancePlans() {
        return acceptedInsurancePlans;
    }

    /**
     * Determines whether two Doctor objects are
     * equal by comparing name and npis
     *
     * @param o the second Doctor object
     * @return whether the Doctors are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Doctor)) {
            return false;
        } else {
            Doctor d = (Doctor) o;
            return this.npi.equals(d.getNpi()) || this.name.equals(d.getName());
        }
    }


    /**
     * Compares two Doctors.
     * Returns 0 if the two Doctors are equal
     * If the two Doctors have the same name
     * returns compareTo of the names
     * Otherwise, returns compareTo of the npis
     */
    @Override
    public int compareTo(Doctor doctor) {
        return comparable.compareTo(doctor);
    }

    public Comparable<Doctor> compareByPrimaryKey() {
        return new Comparable<Doctor>() {
            @Override
            public int compareTo(Doctor doctor) {
                return Doctor.this.npi.compareTo(doctor.npi);
            }
        };
    }

    public Comparable<Doctor> compareBySecondaryKey() {
        return new Comparable<Doctor>() {
            @Override
            public int compareTo(Doctor doctor) {
                return Doctor.this.name.compareTo(doctor.name);
            }
        };
    }

    /***MUTATORS***/

    public void addInsurancePlan(String plan) {
        acceptedInsurancePlans.add(plan);
    }

    public void setComparable(Comparable<Doctor> comparable) {
        this.comparable = comparable;
    }

    public void setNPI(String npi) {
        this.npi = npi;
    }

    public void setName(String name) {
        this.name = name;
    }

    /***ADDITIONAL OPERATIONS***/

    /**
     * Creates a String of the Doctor information
     * the following format:
     * Name: <name>
     * Specialty: <specialty>
     * Clinic: <clinic>
     * NPI: <npi>
     * Gender: <gender>
     * Accepting new patients: <acceptingNewPts>
     * Accepted insurance plans: Plan1 Plan2 Plan3 Plan4
     */
    @Override
    public String toString() {
        //We will add a DecimalFormat for npi formatting
        String result = "Name: " + name +
                "\nSpecialty: " + specialty +
                "\nClinic: " + clinic +
                "\nNPI: " + npi + // will format later
                "\nGender: " + gender +
                "\nAccepting new patients: " + acceptingNewPts +
                "\nAccepted insurance plans: ";
        for (int i = 0; i < acceptedInsurancePlans.size() - 1; i++) {
            result += acceptedInsurancePlans.get(i) + ", ";
        }
        //result += acceptedInsurancePlans.get(acceptedInsurancePlans.size() - 1);
        return result + "\n";
    }

    /**
     * Returns a consistent hash code for
     * each Doctor by summing the Unicode values
     * of each character in the key
     * Key = npi
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int sum = 0;
        for (int i = 0; i < npi.length(); i++) {
            sum += (int) npi.charAt(i);
        }
        return sum;
    }
}