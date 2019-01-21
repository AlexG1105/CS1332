import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This file contains robust tests for adding operations and resizing
 * It also checks to see if the proper exceptions are thrown
 * It uses Strings
 * It does NOT test remove methods
 * @author Alexander Puckhaber
 * @version 1.0
 */

public class PuckhaberTests {
    private ArrayList<String> list;

    public static final int TIMEOUT = 200;

    @Before
    public void setUp() { list = new ArrayList<String>(); }

    @Test(timeout = TIMEOUT)
    public void testInitialCapacity() {
        assertEquals(ArrayList.INITIAL_CAPACITY, 9);
        assertEquals(ArrayList.INITIAL_CAPACITY, list.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void testInitialSize() {
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    // this is basically copy/pasted from the one we were given
    public void testAddStringsFront() {
        assertEquals(0, list.size());

        list.addToFront("0a");  // 0a
        list.addToFront("1a");  // 1a 0a
        list.addToFront("2a");  // 2a 1a 0a
        list.addToFront("3a");  // 3a 2a 1a 0a
        list.addToFront("4a");  // 4a 3a 2a 1a 0a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "4a";
        expected[1] = "3a";
        expected[2] = "2a";
        expected[3] = "1a";
        expected[4] = "0a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    // this adds more elements until the array is full
    public void testAddMoreStringsFront() {
        testAddStringsFront();

        // lets add some more...
        list.addToFront("5a");  // 5a 4a 3a 2a 1a 0a
        list.addToFront("6a");  // 6a 5a 4a 3a 2a 1a 0a
        list.addToFront("7a");  // 7a 6a 5a 4a 3a 2a 1a 0a
        list.addToFront("8a");  // 8a 7a 6a 5a 4a 3a 2a 1a 0a

        assertEquals(9, list.size());

        Object[] expected = { "8a", "7a", "6a", "5a", "4a", "3a", "2a", "1a",
                "0a" };

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    // this adds one more element and checks if the resize worked
    public void testResizeOnAddToFront() {
        // let's populate the ArrayList with strings
        testAddMoreStringsFront();

        assertEquals(9, list.size());

        // add one more to make it resize
        list.addToFront("9a");  // 9a 8a 7a 6a 5a 4a 3a 2a 1a 0a

        assertEquals(10, list.size());

        Object[] expected = { "9a", "8a", "7a", "6a", "5a", "4a", "3a", "2a",
                "1a", "0a", null, null, null, null, null, null, null, null };

        // check to see if backing array was resized to double the length
        assertEquals(ArrayList.INITIAL_CAPACITY * 2,
                list.getBackingArray().length);

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    // this also includes verifying the resize
    public void testResizeOnAddStringsBack() {
        assertEquals(0, list.size());

        list.addToBack("0a"); // 0a
        list.addToBack("1a"); // 0a 1a
        list.addToBack("2a"); // 0a 1a 2a
        list.addToBack("3a"); // 0a 1a 2a 3a

        assertEquals(4, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        assertArrayEquals(expected, list.getBackingArray());

        // now let's add some more
        list.addToBack("4a"); // 0a 1a 2a 3a 4a
        list.addToBack("5a"); // 0a 1a 2a 3a 4a 5a
        list.addToBack("6a"); // 0a 1a 2a 3a 4a 5a 6a
        list.addToBack("7a"); // 0a 1a 2a 3a 4a 5a 6a 7a
        list.addToBack("8a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a

        assertEquals(9, list.size());

        //add another to make it resize
        list.addToBack("9a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a 9a

        assertEquals(10, list.size());

        Object[] expected2 = { "0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a",
                "8a", "9a", null, null, null, null, null, null, null, null };

        // check to see if backing array was resized to double the length
        assertEquals(ArrayList.INITIAL_CAPACITY * 2,
                list.getBackingArray().length);

        assertArrayEquals(expected2, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    // again, checking to see if the resize worked, but in a different way
    public void testAddStringsGeneral() {
        assertEquals(0, list.size());

        list.addAtIndex(0, "4a"); // 4a
        list.addAtIndex(1, "7a"); // 4a 7a

        assertEquals(2, list.size());
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "4a";
        expected[1] = "7a";

        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(1, "6a"); // 4a 6a 7a
        expected[1] = "6a";
        expected[2] = "7a";
        assertEquals(3, list.size());

        assertArrayEquals(expected, list.getBackingArray());

        list.addAtIndex(0, "2a"); // 2a 4a 6a 7a
        list.addAtIndex(4, "8a"); // 2a 4a 6a 7a 8a
        list.addAtIndex(0, "0a"); // 0a 2a 4a 6a 7a 8a
        list.addAtIndex(3, "5a"); // 0a 2a 4a 5a 6a 7a 8a
        list.addAtIndex(1, "1a"); // 0a 1a 2a 4a 5a 6a 7a 8a
        list.addAtIndex(3, "3a"); // 0a 1a 2a 3a 4a 5a 6a 7a 8a

        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";

        assertArrayEquals(expected, list.getBackingArray());

        // now let's add one more to the middle to make it resize
        list.addAtIndex(4, "3.5a");

        assertEquals(10, list.size());

        Object[] expected2 = { "0a", "1a", "2a", "3a", "3.5a", "4a", "5a", "6a",
                "7a", "8a", null, null, null, null, null, null, null, null };

        assertArrayEquals(expected2, list.getBackingArray());
    }


    // These tests are for exceptions that your ArrayList methods
    // are supposed to be throwing
    // These tests will pass for any exception thrown while calling the method
    // It will not pass if you suppress the exception somehow, like if
    // you add error handling the assignment didn't ask for
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeAddAtLowIndex() {
        list.addAtIndex(-1, "this should not work");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeAddAtHighIndex() {
        list.addAtIndex(list.size() + 1, "this should not work");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeRemoveAtLowIndex() {
        Object o = list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeRemoveAtHighIndex() {
        Object o = list.removeAtIndex(list.size());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeGetAtLowIndex() {
        Object o = list.get(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testOutOfRangeGetAtHighIndex() {
        Object o = list.get(list.size());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullAddAtIndex() {
        list.addAtIndex(0, null);
    }


    @Test(timeout = TIMEOUT)
    // basically copy and pasted from the tests we were given
    public void testIsEmptyAndClear() {
        // Should be empty at initialization
        assertEquals(true, list.isEmpty());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");
        assertEquals(false, list.isEmpty());

        // Clearing the list should empty the array and reset size
        list.clear();
        assertEquals(true, list.isEmpty());
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }
}
