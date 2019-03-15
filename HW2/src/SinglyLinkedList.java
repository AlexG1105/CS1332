/**
 * Your implementation of a circular singly linked list.
 *
 * @author Alexander Guo
 * @userid aguo36
 * @GTID 903439488
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Error, index out"
                    + "of bounds!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Error, data is null!");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> cur = head;
            LinkedListNode<T> add = new LinkedListNode<T>(data);
            for (int i = 1; i < size; i++) {
                if (i == index) {
                    add.setNext(cur.getNext());
                    cur.setNext(add);
                }
                cur = cur.getNext();
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, data is null!");
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data);
            head.setNext(head);
        } else {
            LinkedListNode<T> add = new LinkedListNode<T>(null);
            add.setNext(head.getNext());
            head.setNext(add);
            add.setData(head.getData());
            head.setData(data);
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, data is null!");
        }
        /* redundant
        if (size == 0) {
            addToFront(data);
        } else {
            LinkedListNode<T> add = new LinkedListNode<T>(null);
            add.setNext(head.getNext());
            head.setNext(add);
            add.setData(head.getData());
            head.setData(data);
            head = head.getNext();
        }
        */
        addToFront(data);
        head = head.getNext();
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error, Index out of bounds!");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            T returnData = null;
            if (size == 1) {
                returnData = head.getData();
                clear();
                return returnData;
            }
            LinkedListNode<T> cur = head;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    returnData = cur.getData();
                    cur.setData(cur.getNext().getData());
                    cur.setNext(cur.getNext().getNext());
                }
                cur = cur.getNext();
            }
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        T returnData;

        if (size == 0) {
            return null;
        } else {
            if (size == 1) {
                returnData = head.getData();
                clear();
                return returnData;
            }
            returnData = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {

        if (size == 0) {
            return null;
        } else {
            T returnData;

            if (size == 1) {
                returnData = head.getData();
                clear();
                return returnData;
            }

            LinkedListNode<T> cur = head;
            for (int i = 0; i < size - 2; i++) {
                cur = cur.getNext();
            }
            returnData = cur.getNext().getData();
            cur.setNext(head);
            size--;
            if (size == 0) {
                return null;
            }
            return returnData;
        }
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, data is null!");
        } else {

            int removeIndex = -1;
            LinkedListNode<T> cur = head;
            for (int i = 0; i < size; i++) {
                if (cur.getData().equals(data)) {
                    removeIndex = i;
                }
                cur = cur.getNext();
            }
            if (removeIndex == -1) {
                return null;
            } else {
                return removeAtIndex(removeIndex);
            }
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error, index out of bounds");
        }
        if (index == 0) {
            return head.getData();
        } else {
            LinkedListNode<T> cur = head;
            T returnData = null;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    returnData = cur.getData();
                }
                cur = cur.getNext();
            }
            return returnData;
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        T[] array = (T[]) new Object[size];
        LinkedListNode<T> cur = head;
        for (int i = 0; i < size; i++) {
            array[i] = cur.getData();
            cur = cur.getNext();
        }
        return array;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}
