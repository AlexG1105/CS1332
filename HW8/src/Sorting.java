import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Alexander Guo
 * @userid aguo36
 * @GTID 903439488
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array is null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator is null!");
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    T temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array is null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator is null!");
        }
        int maxIndex;
        for (int i = arr.length - 1; i >= 0; i--) {
            maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[maxIndex], arr[j]) < 0) {
                    maxIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[maxIndex];
            arr[maxIndex] = temp;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array is null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator is null!");
        }
        if (arr.length <= 1) {
            return;
        }
        int middleIndex = arr.length / 2;

        T[] leftArray = (T[]) new Object[middleIndex];
        T[] rightArray = (T[]) new Object[arr.length - middleIndex];

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = arr[i];
        }
        for (int i = leftArray.length; i < arr.length; i++) {
            rightArray[i - leftArray.length] = arr[i];
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int i = 0;
        int j = 0;
        int currentIndex = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[currentIndex] = leftArray[i];
                i++;
            } else {
                arr[currentIndex] = rightArray[j];
                j++;
            }
            currentIndex++;
        }

        while (i < leftArray.length) {
            arr[currentIndex] = leftArray[i];
            i++;
            currentIndex++;
        }

        while (j < rightArray.length) {
            arr[currentIndex] = rightArray[j];
            j++;
            currentIndex++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array is null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator is null!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Error, random is null!");
        }
        quickSortH(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Helper method to recursively implement quicksort
     * @param arr Passed in array to sort in place
     * @param start left of subarray
     * @param end right of subarray
     * @param comparator passed in comparator object so we can sort
     * @param rand random generator for pivot
     * @param <T> generic type in array
     */
    private static <T> void quickSortH(T[] arr, int start, int end,
                                       Comparator<T> comparator, Random rand) {
        //base case to terminate recursive loop
        if (start >= end) {
            return;
        }

        int i = start + 1;
        int j = end;

        int pivot = rand.nextInt(end - start + 1) + start;

        T pivotItem = arr[pivot];
        arr[pivot] = arr[start];
        arr[start] = pivotItem;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[start]) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], arr[start]) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        arr[start] = arr[j];
        arr[j] = pivotItem;

        quickSortH(arr, start, j - 1, comparator, rand);
        quickSortH(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, array is null!");
        }

        //Initial pass-through to determine the max number + iterations
        int max = arr[0];
        int iter = 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                max = Integer.MAX_VALUE;
            } else if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        while (max >= 10) {
            iter++;
            max = max / 10;
        }

        List<Integer>[] buckets = new LinkedList[19];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int divideBy = 1; //since we can't use Math.pow

        for (int i = 0; i < iter; i++) {
            for (Integer num: arr) {
                buckets[((num / divideBy) % 10) + 9].add(num);
            }

            int currentIndex = 0;
            for (int j = 0; j < buckets.length; j++) {
                while (!buckets[j].isEmpty()) {
                    arr[currentIndex] = buckets[j].remove(0);
                    currentIndex++;
                }

            }
            divideBy *= 10; //do this instead of pow
        }
    }
}
