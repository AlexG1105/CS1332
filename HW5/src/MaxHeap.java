import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Alexander Guo
 * @userid aguo36
 * @GTID 903439488
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm thatthe same order as it appears
     * in the passed in ArrayList before you star was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * The data in the backingArray should be in t the Build Heap Algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Error, element or"
                    + "data is null!");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            backingArray[i + 1] = data.get(i);
        }
        for (int i = backingArray.length / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Helper method to perform the downheap operation
     *
     * @param index the index that you want to downheap;
     */
    private void downHeap(int index) {
        if (2 * index > size) {
            return;
        }

        T currentItem = backingArray[index];
        T leftItem = backingArray[2 * index];
        T rightItem;

        if (2 * index + 1 > size) {
            rightItem = null;
        } else {
            rightItem = backingArray[2 * index + 1];
        }

        if (rightItem == null) {
            if (currentItem.compareTo(leftItem) <= 0) {
                backingArray[index] = leftItem;
                backingArray[index * 2] = currentItem;
            }
        } else {
            if (currentItem.compareTo(leftItem) < 0
                    || currentItem.compareTo(rightItem) < 0) {
                if (leftItem.compareTo(rightItem) >= 0) {
                    backingArray[index] = leftItem;
                    backingArray[2 * index] = currentItem;
                    downHeap(2 * index);
                } else {
                    backingArray[index] = rightItem;
                    backingArray[2 * index + 1] = currentItem;
                    downHeap(2 * index + 1);
                }
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Error, item is null!");
        }

        if (size >= backingArray.length - 1) {
            T[] expanded = (T[]) new Comparable[(2 * backingArray.length)];
            for (int i = 1; i < backingArray.length; i++) {
                expanded[i] = backingArray[i];
            }
            backingArray = expanded;
        }
        size++;
        backingArray[size] = item;
        upHeap(size);
    }

    /**
     * Helper method to perform the upheap operation
     *
     * @param index the index that you want to upheap;
     */
    private void upHeap(int index) {
        if (index == 1) {
            return;
        }

        T currentItem = backingArray[index];
        T parent = backingArray[index / 2];

        if (currentItem.compareTo(parent) > 0) {
            backingArray[index / 2] = currentItem;
            backingArray[index] = parent;
            upHeap(index / 2);
        }
    }

    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Error, heap is empty!");
        }
        T root = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return root;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            return null;
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}
