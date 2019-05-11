import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.Rule;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Better tests for sorting algorithms (HW 8).
 *
 * @author Better Tech Inc. (Sumit Choudhury & Anish Thite)
 * @version 1.0
 */

public class BetterTests {
    private List<Item> itemsList;
    private Item[] items;
    private ComparatorPlus<Item> comp;
    private static final int TIMEOUT = 200;
    private static int yeets;
    private static int oofs;

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            oofs++;
        }
        @Override
        protected void succeeded(Description description) {
            yeets++;
        }
    };

    @Before
    public void setUp() {
        comp = Item.getComparator();
        yeets = 0;
    }


    @Test(expected = IllegalArgumentException.class)
    public void mergeNullArray() {
        Sorting.mergeSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class)
    public void mergeNullComparator() {
        Sorting.mergeSort(items, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void quickArrayNull() {
        Sorting.quickSort(null, comp, new Random());
    }
    @Test(expected = IllegalArgumentException.class)
    public void quickCompareNull() {
        Sorting.quickSort(items, null, new Random());
    }
    @Test(expected = IllegalArgumentException.class)
    public void quickRandNull() {
        Sorting.quickSort(items, comp, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void selectionArrayNull() {
        Sorting.selectionSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class)
    public void selectionCompareNull() {
        Sorting.selectionSort(items, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void insertionArrayNull() {
        Sorting.insertionSort(null, comp);
    }
    @Test(expected = IllegalArgumentException.class)
    public void insertionCompareNull() {
        Sorting.selectionSort(items, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void radixArrayNull() {
        Sorting.lsdRadixSort(null);
    }
    @Test
    public void lsdRadixSortTest() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);

        unsortedArray = new int[] {10, 1, 100, 1000000, 1000, 100000, 10000, 0, -1, -10, -1000, -10000};
        sortedArray = new int[] {-10000, -1000, -10, -1, 0, 1, 10, 100, 1000, 10000, 100000, 1000000};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);

        unsortedArray = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        sortedArray = new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals("Check overflow on Math.abs()", sortedArray, unsortedArray);

        unsortedArray = new int[] {9, 9, 9, 6, 9, 9, 9};
        sortedArray = new int[] {6, 9, 9, 9, 9, 9, 9};
        Sorting.lsdRadixSort(unsortedArray);
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals("Checking Stability.", sortedArray, unsortedArray);

        unsortedArray = new int[100000];
        Random rand = new Random();
        for (int i = 0; i < unsortedArray.length; i++) {
            unsortedArray[i] = rand.nextInt(100000);
        }
        sortedArray = unsortedArray;
        Arrays.sort(sortedArray);
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals("Using random data", sortedArray, unsortedArray);
    }

    /**
     * Edge cases:
     * check adaptability and stability for
     *
     */
    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {

        Item[] itemsSorted = new Item[] { new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.insertionSort(unsorted, comp);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 45 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortStability() {

        Item[] itemsSorted = new Item[] { new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.insertionSort(unsorted, comp);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 65 && comp.getCount() != 0);
        assertTrue("Object hashcodes are the same", itemsSorted[5].hashCode() == unsorted[5].hashCode());
        assertTrue("Object hashcodes are the same", itemsSorted[6].hashCode() == unsorted[6].hashCode());
    }


    @Test(timeout = TIMEOUT)
    public void testSelectionSort() {
        Item[] itemsSorted = new Item[] { new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.selectionSort(unsorted, comp);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 45 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Item[] itemsSorted = new Item[] {new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.mergeSort(unsorted, comp);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortStability() {
        Item[] itemsSorted = new Item[] { new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.mergeSort(unsorted, comp);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 28 && comp.getCount() != 0);
        assertTrue("Object hashcodes are the same", itemsSorted[5].hashCode() == unsorted[5].hashCode());
        assertTrue("Object hashcodes are the same", itemsSorted[6].hashCode() == unsorted[6].hashCode());
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Item[] itemsSorted = new Item[] { new Item("Bullet Bill", 0),
            new Item("Star", 1),
            new Item("Lightning", 2),
            new Item("Golden Mushroom", 3),
            new Item("Red Turtle Shells", 4),
            new Item("Three Mushrooms", 5),
            new Item("Blue Turtle Shells", 6),
            new Item("Green Turtle Shells", 7),
            new Item("Bananas", 8),
            new Item("Fake Item Box", 9)};
        Item[] unsorted = itemsSorted;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int a = rand.nextInt(unsorted.length);
            int b = rand.nextInt(unsorted.length);
            swap(unsorted, a, b);
        }
        Sorting.quickSort(unsorted, comp, rand);
        assertArrayEquals(itemsSorted, unsorted);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 43 && comp.getCount() != 0);
    }

    @AfterClass
    public static void testCompleted() {
        System.out.println(yeets + " " + oofs);
        if (oofs == 0) {
            try {
                Desktop.getDesktop().browse(
                    new URL("https://www.youtube.com/watch?v=eyLAEpAJG6I").toURI());
            } catch (Exception e) { }
        } else {
            try {
                Desktop.getDesktop().browse(
                    new URL("https://www.youtube.com/watch?v=OEfiVLnidgI").toURI());
            } catch (Exception e) { }
        }
    }

    private void swap(Item[] arr, int i, int j) {
        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Class for testing proper sorting.
     */
    private static class Item {
        private String name;
        private int order;

        public Item(String name, int order) {
            this.name = name;
            this.order = order;
        }

        //getters
        public String getName() {
            return name;
        }
        public int getOrder() {
            return order;
        }
        //setters
        public void setName(String name) {
            this.name = name;
        }
        public void setOrder(int order) {
            this.order = order;
        }
        //toString
        public String toString() {
            return "Name: " + name + ", Order: " + order;

        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<Item> getComparator() {
            return new ComparatorPlus<Item>() {
                @Override
                public int compare(Item item1,
                                   Item item2) {
                    incrementCount();
                    //if item 1 is larger, return +, if item 2 is larger return -, return 0 else
                    int comparision = item1.getOrder() - item2.getOrder();
                    if (comparision > 0) {
                        return 1;
                    } else if (comparision < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            };
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }

}