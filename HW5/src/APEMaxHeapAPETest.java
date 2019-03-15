import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.AfterClass;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/*
    Dear anyone who reads this, hey
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APEMaxHeapAPETest {
    private MaxHeap<Integer> studentHeap;
    private ArrayList<Integer> myList;

    private static final int TIMEOUT = 200;
    private static int exceptionsFailed = 0;
    private static int testsPassed = 0;

    @Before
    public void setup() {
        studentHeap = new MaxHeap<>();
        // used for maxHeap
        myList = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void atestInitialValues() {
        assertEquals("size should be initialized to 0", 0, studentHeap.size());
        assertEquals("capacity should be initialized to INITIAL_CAPACITY",
                13, studentHeap.getBackingArray().length);
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void btestMaxHeap() {
        // add numbers 1-20
        for (int i = 1; i <= 10; i++) {
            myList.add(i);
        }
        MaxHeap<Integer> heapFromList = new MaxHeap<>(myList);
        // make sure size is correct
        assertEquals("Size not correctly updated", 10, heapFromList.size());
        // make sure capacity is correct
        assertEquals("Capacity should be set to 2n + 1", 21, heapFromList.getBackingArray().length);
        // make sure first index is null
        assertEquals("Index 0 should always be null, start at 1", null, heapFromList.getBackingArray()[0]);

        // I'm too lazy to draw what the tree should look like
        Integer[] answer = {null, 10, 9, 7, 8, 5, 6, 3, 1, 4, 2, null, null, null, null, null, null, null, null, null, null};

        assertArrayEquals("Final heap order is incorrect", answer, heapFromList.getBackingArray());
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void ctestMaxHeapEdges() {
        // testing if there are only 2 elements
        myList.add(3);
        myList.add(4);
        MaxHeap<Integer> heapFromList = new MaxHeap<>(myList);
        // make sure size is correct
        assertEquals("Size not correctly updated", 2, heapFromList.size());
        // make sure capacity is correct
        assertEquals("Capacity should be set to 2n + 1", 5, heapFromList.getBackingArray().length);
        // make sure first index is null
        assertEquals("Index 0 should always be null, start at 1", null, heapFromList.getBackingArray()[0]);

        Integer[] answer = {null, 4, 3, null, null};
        assertArrayEquals("Final heap order is incorrect", answer, heapFromList.getBackingArray());

        // testing if the list is in complete order except for the very last element
        myList.clear();
        myList.add(5);
        myList.add(4);
        myList.add(3);
        myList.add(1);
        myList.add(2);
        myList.add(6);

        heapFromList = new MaxHeap<>(myList);
        // make sure size is correct
        assertEquals("Size not correctly updated", 6, heapFromList.size());
        // make sure capacity is correct
        assertEquals("Capacity should be set to 2n + 1", 13, heapFromList.getBackingArray().length);
        // make sure first index is null
        assertEquals("Index 0 should always be null, start at 1", null, heapFromList.getBackingArray()[0]);

        Integer[] newAnswer = {null, 6, 4, 5, 1, 2, 3, null, null, null, null, null, null};
        assertArrayEquals("Final heap order is incorrect", newAnswer, heapFromList.getBackingArray());

        // test if the array is already sorted
        myList.clear();
        for (int i = 5; i > 0; i--) {
            myList.add(i);
        }
        heapFromList = new MaxHeap<>(myList);
        // make sure size is correct
        assertEquals("Size not correctly updated", 5, heapFromList.size());
        // make sure capacity is correct
        assertEquals("Capacity should be set to 2n + 1", 11, heapFromList.getBackingArray().length);
        // make sure first index is null
        assertEquals("Index 0 should always be null, start at 1", null, heapFromList.getBackingArray()[0]);

        Integer[] finalAnswer = {null, 5, 4, 3, 2, 1, null, null, null, null, null};
        assertArrayEquals("Final heap order is incorrect", finalAnswer, heapFromList.getBackingArray());
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void etestAdd() {
        for (int i = 1; i <= 10; i++) {
            studentHeap.add(i);
        }

        // make sure size is correct
        assertEquals("Size not correctly updated", 10, studentHeap.size());
        // make sure capacity is correct
        assertEquals("Capacity should still be 13", 13, studentHeap.getBackingArray().length);
        // make sure first index is null
        assertEquals("Index 0 should always be null, start at 1", null, studentHeap.getBackingArray()[0]);

        // I'm too lazy to draw what the tree should look like
        Integer[] answer = {null, 10, 9, 6, 7, 8, 2, 5, 1, 4, 3, null, null};

        assertArrayEquals("Final heap order is incorrect", answer, studentHeap.getBackingArray());
        testsPassed++;



    }

    @Test(timeout = TIMEOUT)
    public void ezTestAdd2() {
        setup2(true, false, true);
        assertTrue("Isn't Heap (using isHeap tester method): \n" + displayHeap(maxHeapAdd), isHeap(maxHeapAdd));
        assertTrue("Isn't heap (using isHeap tester method): \n" + displayHeap(maxHeapAdd), isHeap(maxHeapAdd));
        assertTrue("Size isn't correct.", maxHeapRand.size() == size);
        if (size == 420) {
            assertTrue("Resized size isn't correct.", maxHeapRand.getBackingArray().length == 832);
        }
        maxHeapAdd = new MaxHeap<>();
        for (int i = 0; i < 12; i++) {
            maxHeapAdd.add(i);
        }
        assertTrue("Should not have been resized.", maxHeapAdd.getBackingArray().length == MaxHeap.INITIAL_CAPACITY);
        assertTrue("Size isn't correct.", maxHeapAdd.size() == 12);
        maxHeapAdd.add(12);
        assertTrue("Should have been resized.", maxHeapAdd.getBackingArray().length == 26);
        assertTrue("Size isn't correct.", maxHeapAdd.size() == 13);
        for (int i = 0; i < 12; i++) {
            maxHeapAdd.add(i + 13);
        }
        assertTrue("Should not have been resized.", maxHeapAdd.getBackingArray().length == MaxHeap.INITIAL_CAPACITY * 2);
        assertTrue("Size isn't correct.", maxHeapAdd.size() == 25);

        maxHeapAdd.add(26);

        assertTrue("Should have been resized.", maxHeapAdd.getBackingArray().length == 26 * 2);
        assertTrue("Size isn't correct.", maxHeapAdd.size() == 26);
        assertTrue("Isn't heap (using isHeap tester method):\n" + displayHeap(maxHeapAdd), isHeap(maxHeapAdd));

        //ta Test
         /*
                89
               /  \
              64  43
             /  \
            15  17
        */
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.add(43);
        maxHeap.add(15);
        maxHeap.add(64);
        maxHeap.add(17);
        maxHeap.add(89);

        Integer[] expected = new Integer[MaxHeap.INITIAL_CAPACITY];
        expected[1] = 89;
        expected[2] = 64;
        expected[3] = 43;
        expected[4] = 15;
        expected[5] = 17;
        assertEquals(5, maxHeap.size());
        //System.out.println(Arrays.toString(expected));
        //System.out.println(Arrays.toString(maxHeap.getBackingArray()));
        assertArrayEquals(expected, maxHeap.getBackingArray());
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void ftestAddEdges() {
        // idk, maybe adding when list is empty?
        studentHeap.add(13);
        Integer[] answer = {null, 13, null, null, null, null, null, null, null, null, null, null, null};
        assertArrayEquals("error when adding to empty list", answer, studentHeap.getBackingArray());

        // test resizing
        for (int i = 12; i > 1; i--) {
            studentHeap.add(i);
        }
        assertEquals("Capacity should not change yet", 13, studentHeap.getBackingArray().length);
        studentHeap.add(1);
        assertEquals("Capacity should now be doubled", 26, studentHeap.getBackingArray().length);
        Integer[] afterResize = {null, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1,
                null, null, null, null, null, null, null, null, null, null, null, null};
        assertArrayEquals("Order shouldn't change after it gets doubled", afterResize, studentHeap.getBackingArray());
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void gtestRemove() {
        //
        // this method uses add, so if there are bugs in this make sure it's not because of bugs in add()
        //
        for (int i = 0; i < 15; i++) {
            studentHeap.add(i);
        }
        for (int i = 14; i >= 0; i--) {
            assertEquals("Remove returns incorrect value", (Integer) i, studentHeap.remove());
            assertEquals("size not correctly updated", i, studentHeap.size());
        }

        assertEquals("Remove doesn't remove elements properly", null, studentHeap.getMax());

        // make sure array doesn't resize
        assertEquals("Remove shouldn't change capacity of array", 26, studentHeap.getBackingArray().length);
        testsPassed++;
    }

    //relies on add and build heap
    @Test(timeout = TIMEOUT * 3)
    public void gzRemoveTests2() {
        setup2();
        for (int i = 0; i < size; i++) {
            maxHeapRand.remove();
            assertTrue("Isn't heap (using isHeap tester method):\n" + displayHeap(maxHeapRand, true),  isHeap(maxHeapRand));
        }
        int sizeAtStart = maxHeapAdd.size();
        for (int i = maxHeapAdd.size(); i > (sizeAtStart / 2); i--) {
            Integer large = maxHeapAdd.remove();
            //System.out.println(i - 1);
            assertEquals("removed elements are the max", (Object) large,   (Object) (i - 1));
            assertEquals("removed elements are botht eh max and the same", large,  maxHeapBuild.remove());

            assertEquals("correct size", (Object) large, (Object) maxHeapAdd.size());

            assertEquals("correct size", (Object) large, (Object) maxHeapBuild.size());
        }

        assertTrue("Isn't heap (using isHeap tester method):\n" + displayHeap(maxHeapAdd, true),  isHeap(maxHeapAdd));
        assertTrue("Isn't heap (using isHeap tester method):\n" + displayHeap(maxHeapBuild, true),  isHeap(maxHeapBuild));
        testsPassed++;

    }

    @Test(timeout = TIMEOUT)
    public void htestRemainingMethods() {
        // test isEmpty part 1
        assertTrue("isEmpty returns incorrect value", studentHeap.isEmpty());

        // test getMax
        assertEquals("getMax when heap is empty must return null", null, studentHeap.getMax());
        studentHeap.add(5);
        assertEquals("Incorrect max value returned", (Integer) 5, (Integer) studentHeap.getMax());
        studentHeap.add(4);
        assertEquals("Incorrect max value returned", (Integer) 5, (Integer) studentHeap.getMax());

        // test isEmpty part 2
        assertFalse("isEmpty returns incorrect value", studentHeap.isEmpty());

        // test clear
        // start by making array resize
        for (int i = 0; i < 15; i++) {
            studentHeap.add(i);
        }
        studentHeap.clear();
        assertEquals("Size not correctly reset", 0, studentHeap.size());
        assertEquals("Backing array not resized", 13, studentHeap.getBackingArray().length);
        assertEquals("All elements should be reset to null", null, studentHeap.getMax());

        // test isEmpty part 3
        assertTrue("isEmpty returns incorrect value", studentHeap.isEmpty());
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void hzclearTest() {
        setup2();
        maxHeapRand.clear();
        assertTrue("length doesn't equal initial capacity", maxHeapRand.getBackingArray().length == 13);
        assertTrue("size should be 0", maxHeapRand.size() == 0);
        Object[] a = {null, null, null, null, null, null, null, null, null, null, null, null, null};
        assertArrayEquals("All array elements should be null", maxHeapRand.getBackingArray(), a);
        assertTrue("isEmpty should return true", maxHeapRand.isEmpty());
        assertTrue("maximum should return null when empty", maxHeapRand.getMax() == null);
        testsPassed++;
    }
    @Test(timeout = TIMEOUT)
    public void hzzheapInitialEmptyTest() {
        maxHeapRand = new MaxHeap<Double>();
        assertTrue("Length doesn't equal initial capacity", maxHeapRand.getBackingArray().length == 13);
        assertTrue("Initial size should be 0", maxHeapRand.size() == 0);
        Object[] a = {null, null, null, null, null, null, null, null, null, null, null, null, null};
        assertArrayEquals("All array elements should be null", maxHeapRand.getBackingArray(), a);
        assertTrue("isEmpty should return true", maxHeapRand.isEmpty());
        assertTrue("maximum should return null when empty", maxHeapRand.getMax() == null);
        testsPassed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void iexceptionTestMaxHeapList() {
        // exception thrown when list is null
        MaxHeap<String> errorHeap = new MaxHeap<>(null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void iexceptionTestMaxHeapData() {
        ArrayList<String> nullList = new ArrayList<>();
        nullList.add("one");
        nullList.add("two");
        nullList.add(null);
        // exception should be thrown here (element is null)
        MaxHeap<String> errorHeap = new MaxHeap<>(nullList);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void iexceptionTestAdd() {
        MaxHeap<String> errorHeap = new MaxHeap<>();
        // exception thrown when item is null
        errorHeap.add(null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void iexceptionTestRemove() {
        MaxHeap<String> errorHeap = new MaxHeap<>();
        // removing from empty list should return null
        errorHeap.remove();
        exceptionsFailed++;
    }
    @AfterClass
    public static void launchGame() {
        if (testsPassed == 11 && exceptionsFailed == 0) {
            System.out.println("===================================================================");
            System.out.println("Congrats! You Passed Every Test!");
            System.out.println("===================================================================");
            try {
                Desktop.getDesktop().browse(new URL("https://mjkeo.github.io/Banandersnatch/").toURI());
            } catch (Exception e) {}
        } else {
            System.out.println("===================================================================");
            System.out.println("Sorry, you failed " + (11 - testsPassed) + " tests and " + exceptionsFailed
                    + " exception checks");
            System.out.println("===================================================================");
        }
    }





    private MaxHeap<Integer> maxHeapAdd;
    private MaxHeap<Integer> maxHeapBuild;
    private MaxHeap<Double> maxHeapRand;
    private int size = 420;



    //uses add and buildHeap constructor
    public void setup2() {
        setup2(true ,true,true);
    }
    public void setup2(boolean add, boolean build, boolean rand) {
        ArrayList<Integer> iLinkedList = new ArrayList<>();

        maxHeapAdd = new MaxHeap<>();
        for (int i = 0; i < 25; i++) {
            if (add)
                maxHeapAdd.add(i);
            if (build)
                iLinkedList.add(i);
        }
        //System.out.println(displayHeap(maxHeapAdd));
        if (build)
            maxHeapBuild = new MaxHeap<Integer>(iLinkedList);
        maxHeapRand = new MaxHeap<>();
       /* System.out.println(Arrays.toString(maxHeapAdd.getBackingArray()));;
        System.out.println(isHeap((Comparable[]) maxHeapAdd.getBackingArray(), maxHeapAdd.size(), 1));
        System.out.println(Arrays.toString(maxHeapBuild.getBackingArray()));
        System.out.println(isHeap((Comparable[]) maxHeapBuild.getBackingArray(), maxHeapBuild.size(), 1)); */
        for (int i = 0; i < size; i++) {
            if (rand)
                maxHeapRand.add(Double.parseDouble(i + "." + i));
        }
        //System.out.println(isHeap((Comparable[]) maxHeapRand.getBackingArray(), maxHeapRand.size(), 1));
    }

    /** Max Heap definition tester.
     *
     * @param backingAr defining heap
     * @param size from heap
     * @param i index (starts at 1)
     * @param <T> generic
     * @return boolean that defines if input is or isn't a max heap
     */
    private <T extends Comparable<? super T>> boolean isHeap(Comparable[] backingAr, int size,  int i) {

        T[] backingArr = (T[]) backingAr;

        if (i > size) {
            return true;
        }
        if (backingArr[i] == null) {
            return false;
        }
        //print out of test information
       /*if ( i * 2 + 1 <= size) {
            System.out.println(i + " => " + i * 2 + "\t\t\t" + backingArr[i] + " > " + backingArr[i * 2] + " & " + backingArr[i * 2 + 1]);
        }*/
        if (i * 2 + 1 <= size) {
            if (backingArr[i * 2].compareTo(backingArr[i]) < 0 && backingArr[i * 2 + 1].compareTo(backingArr[i]) < 0) {
                return isHeap(backingArr, size, i * 2) && isHeap(backingArr, size, i * 2 + 1);
            } else {
                return false;
            }
        } else if (i * 2 <= size) {
            if (backingArr[i * 2].compareTo(backingArr[i]) < 0) {
                return isHeap(backingArr, size, i * 2);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private <T extends Comparable<? super T>> boolean isHeap(MaxHeap heap) {
        return isHeap((Comparable[]) heap.getBackingArray(), heap.size(), 1);
    }


    // found this on stack overflow. useful for printing under size of 26
    private <T extends Comparable<? super T>> String displayHeap(MaxHeap heap, boolean b) {
        String s ="";
        T[] heapArray = (T[]) heap.getBackingArray();
        s += ("heapArray: ");    // array format
        s += Arrays.toString(heapArray);
        return s;
    }
    private <T extends Comparable<? super T>> String displayHeap(MaxHeap heap)
    {
        String s = "";
        T[] heapArray = (T[]) heap.getBackingArray();
        s += ("heapArray: ");    // array format
        s += Arrays.toString(heapArray);
        // heap format
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 1;                          // current item
        String dots = "...............................";
        s += "\n" + dots + dots + "\n";
        while (heap.size() > 0)              // for each heap item
        {
            if (column == 0)                  // first item in row?
                for(int k=0; k<nBlanks; k++)  // preceding blanks
                    s += (' ');
            // display item
            s += (heapArray[j]);

            if (++j == heap.size())           // done?
                break;

            if (++column == itemsPerRow)        // end of row?
            {
                nBlanks /= 2;                 // half the blanks
                itemsPerRow *= 2;             // twice the items
                column = 0;                   // start over on
                s += "\n";         //    new row
            }
            else                             // next item on row
                for(int k=0; k<nBlanks * 2 - 2; k++) {
                    s += (' ');     // interim blanks
                    if (itemsPerRow > 8 && j % 2 == 0)
                        s += (' ');
                }
        }  // end for
        s += "\n" + dots + dots;



        return s + "\n";
    }  // end displayHeap()

}