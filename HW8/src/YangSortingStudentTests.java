import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Student tests for sorting algorithms.
 *
 * @author Yang Zhang & CS1332
 * @version 1.0
 */
public class YangSortingStudentTests {
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;
    private Numbers[] nums;
    private Numbers[] numsSorted;
    private ComparatorPlus<Numbers> compInt;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Adrianna
                index 1: Brandon
                index 2: Destini
                index 3: Mahima
                index 4: Alok
                index 5: Robert
                index 6: Stephanie
                index 7: Jacob
                index 8: Nick
                index 9: Andrew
         */
        /*
            Sorted Names:
                index 0: Adrianna
                index 1: Alok
                index 2: Andrew
                index 3: Brandon
                index 4: Destini
                index 5: Jacob
                index 6: Mahima
                index 7: Nick
                index 8: Robert
                index 9: Stephanie
         */
        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Adrianna");
        tas[1] = new TeachingAssistant("Brandon");
        tas[2] = new TeachingAssistant("Destini");
        tas[3] = new TeachingAssistant("Mahima");
        tas[4] = new TeachingAssistant("Alok");
        tas[5] = new TeachingAssistant("Robert");
        tas[6] = new TeachingAssistant("Stephanie");
        tas[7] = new TeachingAssistant("Jacob");
        tas[8] = new TeachingAssistant("Nick");
        tas[9] = new TeachingAssistant("Andrew");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();

        nums = new Numbers[10];
        nums[0] = new Numbers(9);
        nums[1] = new Numbers(8);
        nums[2] = new Numbers(7);
        nums[3] = new Numbers(6);
        nums[4] = new Numbers(5);
        nums[5] = new Numbers(4);
        nums[6] = new Numbers(3);
        nums[7] = new Numbers(2);
        nums[8] = new Numbers(1);
        nums[9] = new Numbers(0);
        numsSorted = new Numbers[10];
        numsSorted[0] = nums[9];
        numsSorted[1] = nums[8];
        numsSorted[2] = nums[7];
        numsSorted[3] = nums[6];
        numsSorted[4] = nums[5];
        numsSorted[5] = nums[4];
        numsSorted[6] = nums[3];
        numsSorted[7] = nums[2];
        numsSorted[8] = nums[1];
        numsSorted[9] = nums[0];
        compInt = Numbers.getNumberComparator();

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortBestCase() {
        Sorting.insertionSort(numsSorted, compInt);
        assertArrayEquals(numsSorted, numsSorted);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(),
                compInt.getCount() <= 9 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWorstCase() {
        Sorting.insertionSort(nums, compInt);
        assertArrayEquals(numsSorted, nums);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(), compInt.getCount() <= 45 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSort() {
        Sorting.selectionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 45 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSortBestCase() {
        Sorting.selectionSort(numsSorted, compInt);
        assertArrayEquals(numsSorted, numsSorted);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(),
                compInt.getCount() <= 45 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSortWorstCase() {
        Sorting.selectionSort(nums, compInt);
        assertArrayEquals(numsSorted, nums);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(),
                compInt.getCount() <= 45 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 21 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortBestCase() {
        Sorting.mergeSort(numsSorted, compInt);
        assertArrayEquals(numsSorted, numsSorted);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(),
                compInt.getCount() <= 15 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortWorstCase() {
        Sorting.mergeSort(nums, compInt);
        assertArrayEquals(numsSorted, nums);
//        System.out.println(compInt.getCount());
        assertTrue("Number of comparisons: " + compInt.getCount(),
                compInt.getCount() <= 19 && compInt.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(tas, comp, new Random(234));
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 27 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixBucketOfZeros() {
        int[] unsortedArray = new int[] {402, 803, 201, 504, 2, 709};
        int[] sortedArray = new int[] {2, 201, 402, 504, 709, 803};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixNegative() {
        int[] unsortedArray = new int[] {-6, -632, -53, -6402, -43, -453, -98, 0, 3};
        int[] sortedArray = new int[] {-6402, -632, -453, -98, -53, -43, -6, 0, 3};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixIntegerMin() {
        int[] unsortedArray = new int[] {-9, 1, 2, Integer.MIN_VALUE, 5};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -9, 1, 2, 5};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the teaching assistant.
         *
         * @param name name of the teaching assistant
         */
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
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
    /**
     * Class for testing proper sorting.
     */
    private static class Numbers {
        private Integer number;

        /**
         * Create a teaching assistant.
         *
         * @param number name of TA
         */
        public Numbers(Integer number) {
            this.number = number;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public Integer getNumber() {
            return number;
        }

        /**
         * Set the name of the teaching assistant.
         *
         * @param number name of the teaching assistant
         */
        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Number: " + number;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<Numbers> getNumberComparator() {
            return new ComparatorPlus<Numbers>() {
                @Override
                public int compare(Numbers ta1,
                                   Numbers ta2) {
                    incrementCount();
                    return ta1.getNumber().compareTo(ta2.getNumber());
                }
            };
        }
    }

}
