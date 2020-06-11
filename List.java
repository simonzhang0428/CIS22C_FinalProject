import java.util.NoSuchElementException;

public class List<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;
	private int indexIterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new list object with default values
	 *
	 * @postcondition a list object created with specific default values
	 */
	public List() {
		first = null;
		last = null;
		iterator = null;
		indexIterator = -1;
		length = 0;
	}

	/**
	 * Instantiates a new List by copying another List
	 *
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
	 */
	public List(List<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 *
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 * @precondition length != 0
	 */
	public T getFirst() throws NoSuchElementException {
		if (first == null) {
			throw new NoSuchElementException("getFirst: List is empty, no data to access.");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 *
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 * @precondition length != 0
	 */
	public T getLast() throws NoSuchElementException {
		if (last == null) {
			throw new NoSuchElementException("getLast: List is empty, no data to access.");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 *
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 *
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * returns the element currently pointed at by the iterator
	 *
	 * @return iterator node data
	 * @throws NullPointerException when precondition is violated
	 * @precondition iterator != null
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIterator: Iterator is null, can not access its data.");
		}
		return iterator.data;
	}

	/**
	 * @return whether iterator is off the end of the list
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Determines whether two Lists have the same data in the same order
	 *
	 * @param o is the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {
					if (!(temp1.data.equals(temp2.data))) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 *
	 * @param data is the data to insert at the front of the list
	 * @postcondition a new first element in the list
	 */
	public void addFirst(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 *
	 * @param data is the data to insert at the end of the list
	 * @postcondition a new last element in the list
	 */
	public void addLast(T data) {
		if (last == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * removes the element at the front of the list
	 *
	 * @throws NoSuchElementException when precondition is violated
	 * @precondition length != 0
	 * @postcondition first element in the list is removed
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeFirst: List is empty, nothing to remove.");
		} else if (length == 1) {
			first = last = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 *
	 * @throws NoSuchElementException when precondition is violated
	 * @precondition length != 0
	 * @postcondition last element in the list is removed
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeLast: list is empty, nothing to remove.");
		} else if (length == 1) {
			first = last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * places iterator at the start of the list
	 *
	 * @postcondition iterator is moved to the start of the list
	 */
	public void placeIterator() {
		iterator = first;
		indexIterator = 0;
	}

	/**
	 * removes the element currently pointed to by the iterator
	 *
	 * @throws NullPointerException if precondition is violated
	 * @precondition iterator != null
	 * @postcondition iterator points to null
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("removeIterator: iterator is null, cannot remove.");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			length--;
		}
		iterator = null;
		indexIterator = -1;
	}

	/**
	 * inserts an element after the node currently pointed to by the iterator
	 *
	 * @throws NullPointerException when precondition is violated
	 * @precondition iterator != null
	 * @postcondition adds a node after iterator
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("addIterator: iterator is off end, cannot add.");
		} else if (iterator == last) {
			addLast(data);
		} else {
			Node N = new Node(data);
			N.next = iterator.next;
			N.prev = iterator;
			iterator.next.prev = N;
			iterator.next = N;
			length++;
		}
	}

	/**
	 * moves the iterator up by one node
	 *
	 * @throws NullPointerException when precondition is violated
	 * @precondition iterator != null
	 * @postcondition iterator is moved up by one none
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: iterator is null, cannot advance.");
		}
		iterator = iterator.next;
		indexIterator++;
	}

	/**
	 * moves the iterator down by one node
	 *
	 * @throws NullPointerException when precondition is violated
	 * @precondition iterator != null
	 * @postcondition iterator is moved down by one node
	 */
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator: iterator is null, cannot reverse");
		}
		iterator = iterator.prev;
		indexIterator--;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value on its own line At the end of the List a new line
	 *
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + " ";
			temp = temp.next;
		}
		return result + "\n";
	}

	/**
	 * prints the contents of the linked list to the screen in the format #.
	 * <element>
	 *
	 * @return list items in a list format
	 */
	public String printNumberedList() {
		String result = "\n";
		Node temp = first;
		int i = 1;
		while (temp != null) {
			result += i + ". " + temp.data + "\n";
			temp = temp.next;
			i++;
		}
		return result;
	}

	/**
	 * Advances the iterator exactly n times
	 * 
	 * @param n the number of times to advance the iterator
	 * @precondition iterator != null
	 * @precondition (n + indexIterator) <= length
	 * @throws NullPointerException      when the iterator is offEnd
	 * @throws IndexOutOfBoundsException when (iterator position + n) > length
	 */
	public void advanceNTimes(int n) throws NullPointerException, IndexOutOfBoundsException {
		if (offEnd()) {
			throw new NullPointerException("advanceNTimes: Iterator is null, cannot advance!");
		} else if ((n + indexIterator) > length) {
			throw new IndexOutOfBoundsException("advanceNTimes: Cannot advance out of the list's bounds!");
		}

		for (int i = 0; i < n; i++) {
			advanceIterator();
		}
	}

	/**
	 * Searches the List for a specific element
	 * 
	 * @param data the element to search for
	 * @return the location of the element in the List from 1 to length or -1 if not
	 *         found
	 * @postcondition the position of the iterator remains unchanged
	 */
	public int linearSearch(T data) {
		if (data == null) {
			return -1;
		}

		Node temp = first;
		int index = 1;

		while (temp != null) {
			if (temp.data.equals(data)) {
				return index;
			}
			index++;
			temp = temp.next;
		}

		return -1;
	}
}