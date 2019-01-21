import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tester {
    private ArrayList<Integer> list;

    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        list = new ArrayList<Integer>();
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        for (int i = 0; i < 9; i++) {
            list.addToBack(i);
        } //[0, 1, 2, 3, 4, 5, 6, 7, 8]
        assertSame("Element at index 0 is incorrect\n", 0, list.get(0));
        assertSame("Element at index 8 is incorrect\n", 8, list.get(8));
        assertSame("Element at index 4 is incorrect\n", 4, list.get(4));
        assertNotNull("Element at index 6 should be non-null\n", list.get(6));
        System.out.println("testGet passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAdd() { //based on initial capacity of 9
        assertSame("Does not have correct initial capacity\n", ArrayList.INITIAL_CAPACITY, list.getBackingArray().length);
        assertSame("Does not have correct initial size\n", 0, list.size());
        list.addAtIndex(0, 1); //[1]
        list.addToFront(0); //[0, 1]
        list.addToBack(2); //[0, 1, 2]
        for (int i = 3; i <= 5; i++) {
            list.addAtIndex(i, i);
            list.addToBack(i + 3);
        } //[0, 1, 2, 3, 4, 5, 6, 7, 8]
        Object[] testList = new Object[ArrayList.INITIAL_CAPACITY];
        for (int i = 0; i <= 8; i++) {
            testList[i] = i;
        }
        assertSame("Length of filled list is not equal to its size\n", list.getBackingArray().length, list.size());
        assertArrayEquals("Did not properly add elements\n", testList, list.getBackingArray());
        list.addToBack(10);
        list.addAtIndex(9, 9);
        //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, null, null, null, null, null, null, null]
        testList = new Object[ArrayList.INITIAL_CAPACITY * 2];
        assertSame("Did not resize properly\n", testList.length, list.getBackingArray().length);
        for (int i = 0; i <= 10; i++) {
            testList[i] = i;
        }
        assertSame("Does not have correct size\n", 11, list.size());
        assertArrayEquals("Does not have proper elements after resize\n", testList, list.getBackingArray());
        System.out.println("testResizeAdd passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testEmptinessAndClear() {
        assertTrue("List is not initially empty\n", list.isEmpty());
        assertSame("List size is not initially 0\n", 0, list.size());
        list.addToFront(0);
        for (int i = 1; i < 20; i++) {
            list.addToBack(i);
        }
        assertFalse("Returns empty when it should contain elements\n", list.isEmpty());
        list.clear();
        assertTrue("List was not properly cleared\n", list.isEmpty());
        assertSame("Clear did not set size to 0\n", 0, list.size());
        assertSame("Length is not equal to its initial capacity after clear\n", ArrayList.INITIAL_CAPACITY, list.getBackingArray().length);
        System.out.println("testEmptinessAndClear passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        for (int i = 0; i < 9; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, 3, 4, 5, 6, 7, 8]
        assertSame("Does not have correct size\n", 9, list.size());
        assertSame("Did not return correct element removed from the front\n", 0, list.removeFromFront());
        //[1, 2, 3, 4, 5, 6, 7, 8, null]
        assertSame("Did not update size properly after removal from front\n", 8, list.size());
        assertNull("Did not delete last element in list\n", list.getBackingArray()[8]);
        Object[] testList = new Object[ArrayList.INITIAL_CAPACITY];
        for (int i = 1; i < 9; i++) {
            testList[i - 1] = i;
        }
        assertArrayEquals("Did not shift elements after removal correctly\n", testList, list.getBackingArray());
        assertSame("Did not remove and return correct element from back\n", 8, list.removeFromBack());
        //[1, 2, 3, 4, 5, 6, 7, null, null]
        assertSame("Did not update size after removal from back\n", 7, list.size());
        assertSame("Did not remove and return correct element from specified index\n", 4, list.removeAtIndex(3));
        //[1, 2, 3, 5, 6, 7, null, null, null]
        testList = new Object[ArrayList.INITIAL_CAPACITY];
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                testList[i] = i + 1;
            } else {
                testList[i] = i + 2;
            }
        }
        assertArrayEquals("Did not handle shifting of elements or deletion correctly\n", testList, list.getBackingArray());
        System.out.println("testRemove passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveEmpty() {
        assertNull("Did not return null when removing from empty list\n", list.removeFromFront());
        assertNull("Did not return null when removing from empty list\n", list.removeFromBack());
        System.out.println("testRemoveEmpty passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testLastIndexOf() {
        list.addToBack(0);
        list.addToBack(2);
        list.addToBack(0);
        //[0, 2, 0]
        assertSame("Did not find correct index of 2\n", 1, list.lastIndexOf(2));
        assertSame("Did not return last index possible of 0\n", 2, list.lastIndexOf(0));
        list.addToFront(2);
        //[2, 0, 2, 0]
        assertSame("Did not find last index possible of 2\n", 2, list.lastIndexOf(2));
        list.removeFromBack();
        //[2, 0, 2]
        assertSame("Did not find correct index of 0 after removal\n", 1, list.lastIndexOf(0));
        assertSame("Did not properly account for element not in list\n", -1, list.lastIndexOf(20));
        System.out.println("testLastIndexOf passed!");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing() {
        list.addAtIndex(2, 2);
        System.out.println("testIndexExceptionThrowing failed..");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing2() {
        list.addAtIndex(-1, 1);
        System.out.println("testIndexExceptionThrowing2 failed..");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing3() {
        list.removeAtIndex(2);
        System.out.println("testIndexExceptionThrowing3 failed..");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing4() {
        list.removeAtIndex(-1);
        System.out.println("testIndexExceptionThrowing4 failed..");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing5() {
        list.get(2);
        System.out.println("testIndexExceptionThrowing5 failed..");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexExceptionThrowing6() {
        list.get(-1);
        System.out.println("testIndexExceptionThrowing6 failed..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentExceptionThrowing() {
        list.addAtIndex(0, null);
        System.out.println("testArgumentExceptionThrowing failed..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentExceptionThrowing2() {
        list.addToFront(null);
        System.out.println("testArgumentExceptionThrowing2 failed..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentExceptionThrowing3() {
        list.addToBack(null);
        System.out.println("testArgumentExceptionThrowing3 failed..");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentExceptionThrowing4() {
        list.lastIndexOf(null);
        System.out.println("testArgumentExceptionThrowing4 failed..");
    }

}