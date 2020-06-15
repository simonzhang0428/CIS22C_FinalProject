import java.util.ArrayList;

public class Hash<T extends Comparable<T>> {

    private int numElements;
    private ArrayList<List<T>> Table;

    /**
     * Constructor for the Hash.java class. Initializes the Table to be sized
     * according to value passed in as a parameter Inserts size empty Lists into the
     * table. Sets numElements to 0
     *
     * @param size the table size
     */
    public Hash(int size) {
        this.Table = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Table.add(i, new List<>());
        }

        this.numElements = 0;
    }

    /** Accessors */

    /**
     * Returns the hash value in the Table for a given key by taking modulus of the
     * hashCode value for that key and the size of the table
     *
     * @param t the key
     * @return the index in the Table
     */
    private int hash(T t) {
        return t.hashCode() % Table.size();
    }

    /**
     * Counts the number of keys at this index
     *
     * @param index the index in the Table
     * @return the count of keys at this index
     * @throws IndexOutOfBoundsException
     * @precondition 0 <= index < Table.length
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= Table.size()) {
            throw new IndexOutOfBoundsException("countBucket: your index is out of table's bounds!");
        }

        List<T> list = Table.get(index);
        return list.getLength();
    }

    /**
     * Returns total number of keys in the Table
     *
     * @return total number of keys
     */
    public int getNumElements() {
        return numElements;
    }

    /**
     * Searches for a specified key in the Table
     *
     * @param t the key to search for
     * @return the index in the Table at which the element is located or -1 if it is
     * not found
     */
    public T search(T t) {
        if (t == null) {
            return null;
        }

        int index = hash(t);
        List<T> list = Table.get(index);

        list.placeIterator();

        while (!list.offEnd()) {
            if(list.getIterator().equals(t)) {
                return list.getIterator();
            }

            list.advanceIterator();
        }

        return null;
    }

    /** Mutators */

    /**
     * Inserts a new key in the Table calls the hash method to determine placement
     *
     * @param t the key to insert
     */
    public void insert(T t) {
        if (t != null) {
            int index = hash(t);
            List<T> list = Table.get(index);
            list.addLast(t);
            numElements++;
        }
    }

    /**
     * Removes the key t from the Table calls the hash method on the key to
     * determine correct placement. Has no effect if t is not in the Table.
     *
     * @param t the key to remove
     */
    public void remove(T t) {
        if (t != null) {
            int index = hash(t);
            List<T> list = Table.get(index);

            list.placeIterator();
            while (!list.offEnd()) {
                if (list.getIterator().equals(t)) {
                    list.removeIterator();
                    numElements--;
                    return;
                }
                list.advanceIterator();
            }
        }
    }

    /** Additional Methods */

    /**
     * Prints all the keys at a specified bucket in the Table. Each key displayed on
     * its own line, with a blank line separating each key Above the keys, prints
     * the message "Printing bucket #<bucket>:" Note that there is no <> in the
     * output
     *
     * @param bucket the index in the Table
     */
    public void printBucket(int bucket) {
        System.out.println("Printing bucket #" + bucket + ":\n");

        List<T> list = Table.get(bucket);
        list.placeIterator();

        while (!list.offEnd()) {
            System.out.println(list.getIterator());
            list.advanceIterator();
        }
    }

    /**
     * This function retrieves all the objects from a has table and places them into an arraylist
     *
     * @return an arraylist with objects from a hash table
     */
    public ArrayList<T> getAllObjects() {
        ArrayList<T> result = new ArrayList<>();

        for (int i = 0; i < Table.size(); i++) {
            List<T> list = Table.get(i);
            list.placeIterator();

            while (!list.offEnd()) {
                result.add(list.getIterator());
                list.advanceIterator();
            }
        }

        return result;
    }

    /**
     * Starting at the first bucket, and continuing in order until the last bucket,
     * concatenates all elements at all buckets into one String
     */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < Table.size(); i++) {
            List<T> list = Table.get(i);
            list.placeIterator();

            while (!list.offEnd()) {
                s += list.getIterator() + "\n";
                list.advanceIterator();
            }
        }

        return s;
    }
}