import java.util.NoSuchElementException;

/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (e.g. gburdell3)
 * @GTID YOUR GT ID HERE (e.g. 900000000)
 * @version 1.0
 */
public class LinkedStack<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        head = new LinkedNode<>(data, head);
        size++;
    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty!");
        }
        LinkedNode<T> temp = head;
        head = head.getNext();
        size--;
        return temp.getData();
    }

    /**
     * Retrieves the next element to be popped without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return head.getData();
        }
    }

    /**
     * Return the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }
}
