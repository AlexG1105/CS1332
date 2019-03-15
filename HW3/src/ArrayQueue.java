import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed queue.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (e.g. gburdell3)
 * @GTID YOUR GT ID HERE (e.g. 900000000)
 * @version 1.0
 */
public class ArrayQueue<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * The initial capacity of a queue with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length. If a resize is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (size == backingArray.length) {
            T[] growArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                growArray[i] = backingArray[front];
                front = (front + 1) % size;
            }
            backingArray = growArray;
            front = 0;
        }
        backingArray[(front + size) % backingArray.length] = data;
        size++;
    }

    /**
     * Removes the data from the front of the queue.
     *
     * Do not shrink the backing array. If the queue becomes empty as a result
     * of this call, you should explicitly reset front to 0.
     *
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * See the homework pdf for more information on implementation details.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        T temp = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        if (size == 0) {
            front = 0;
        }
        return temp;
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return backingArray[front];
        }
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
