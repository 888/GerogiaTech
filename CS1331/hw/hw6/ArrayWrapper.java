/**
 * This implements the SimpleCollection Interface to add functionality
 * to arrays.
 */
import java.util.NoSuchElementException;
public class ArrayWrapper<T> implements SimpleCollection<T> {
    protected T [] elements;
    protected int count;

    /**
     * initiates the ArrayWrapper with a backgin array of size 5
     * @return new ArrayWrapper object
     */
    public ArrayWrapper() {
        elements = (T[]) new Object[5];
    }

    /**
     * initiates the ArrayWrapper with a backgin array of the specified size
     * @return new ArrayWrapper object
     */
    public ArrayWrapper(int size) {
        elements = (T[]) new Object[size];
    }
    /**
     * Adds an element into the collection.
     * If the new element would exceed the size of the backing array,
     * instead resize the array, doubling it in size and copy over the
     * old elements.
     *
     * @param elem The element being added.
     */
    public void add(T elem) {
        if (elements.length == count) {
            doubleSize();
        }
        elements[count] = elem;
        count++;
    }

    private void doubleSize() {
        T [] newElems = (T[]) new Object[elements.length * 2];
        for (int i = 0; i < count; i++) {
            newElems[i] = elements [i];
        }
        elements = newElems;
    }
    /**
     * Adds all elements in elems to the collection.
     *
     * @param elems Array of elements to be added.
     */
    public void addAll(T[] elems) {
        for (int i = 0; i < elems.length; i++) {
            add(elems[i]);
        }
    }
    /**
     * Remove elem from the collection. Removing an element
     * should shift all the elements behind it forward, ensuring
     * that the backing array is contiguous. For example:
     *
     * Collection = ["hi", "hello", "wsup", "hey", null]
     * Collection after remove("hello") = ["hi", "wsup", "hey", null, null]
     *
     * @param elem Element to be removed.
     * @return true if the element was removed,
     *         false if it was not in the collection.
     */
    public boolean remove(T elem) {
        boolean removed = false;
        int numRemoved = 0;
        for (int i = 0; i < count; i++) {
            if (elem.equals(elements[i])) {
                removed = true;
                elements[i] = null;
                count--;
                numRemoved++;
            }
        }
        shift(numRemoved);
        System.err.println(count);
        return removed;
    }

    private void shift(int removed) {
        int shifted = 0;
        for (int i = 0; i < count + removed; i++) {
            if (elements[i] == null) {
                shifted++;
                for (int j = i; j < count; j++) {
                    elements[j] = elements[j + 1];
                }
            }
        }
    }

    /**
     * Removes each element in elems from the collection.
     * Hint: can this be implemented in terms of remove(T elem)?
     *
     * @param elems Array of elements to be removed.
     * @return true if any elements were removed,
     *         false if no elements were removed.
     */
    public boolean removeAll(T[] elems) {
        boolean removed = false;
        for (int i = 0; i < elems.length; i++) {
            if (remove(elems[i])) {
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Checks to see if the collection contains a given element.
     *
     * @param elem The element we are checking for.
     * @return true if the collection contains elem, false otherwise.
     */
    public boolean contains(T elem) {
        for (int i = 0; i < count; i++) {
            if (elements[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets an element from the collection, using its 0-based index.
     * If the index is within our backing array but more than our last
     * element, rather than returning null, this should throw
     * a java.util.NoSuchElementException.
     *
     * @param index The index of the element we want.
     * @return The element at the specified index.
     */
    public T get(int index) {
        if (index >= count) {
            throw new NoSuchElementException();
        }
        return (T) elements[index];
    }

    /**
     * Returns the current number of elements in the collection.
     *
     * @return The size of the collection.
     */
    public int size() {
        return count;
    }

    /**
     * Returns the current capacity of the collection - namely, the
     * size of its backing array.
     *
     * @return The total capacity of the collection.
     */
    public int capacity() {
        return elements.length;
    }

    /**
     * Clears the collection, resetting size and starting from a fresh
     * backing array of size 5.
     */
    public void clear() {
        elements = (T[]) new Object[5];
        count = 0;
    }

    /**
     * Tests if the collection is empty, i.e. it contains no elements.
     *
     * @return true if the collection has no elements, false otherwise.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * While having toString be defined in the interface doesn't force you
     * to override the method in the implementing class, the format we
     * expect the toString() is as follows:
     *
     * [element1, element2, element3, ..., elementN]
     *
     * The end of the list should not contain any nulls, even if the
     * backing array is larger than the number of elements.
     *
     * @return [element1, element2, element3, ..., elementN]
     */
    public String toString() {
        if (count == 0 ) {
            return "[]";
        }
        String string = "[";
        for (int i = 0; i < count; i++) {
            string += elements[i].toString() + ", ";
        }
        return string.substring(0, string.length() - 2) + "]";
    }
}