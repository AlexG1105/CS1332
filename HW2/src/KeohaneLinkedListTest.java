import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/* Hey guys so I made this JUnit test for my own code to test any edge
 cases or common mistakes I could make, probably went a little overboard
 with the amount of tests but I want to be as thorough as possible.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KeohaneLinkedListTest {
    private SinglyLinkedList<String> stringList;
    private SinglyLinkedList<Integer> intList;
    private static final int TIMEOUT = 200;
    private static int testsPassed = 0;
    private static int exceptionsFailed = 0;

    @Before
    public void setUp() {
        stringList = new SinglyLinkedList<String>();
        intList = new SinglyLinkedList<Integer>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialList() {
        // special case: list is initially empty
        assertEquals("size should initially be 0", 0, stringList.size());
        assertEquals("isEmpty() method should initially be true", true, stringList.isEmpty());

        SinglyLinkedList<Integer> intlist2 = intList;
        // these should not throw errors
        stringList.addAtIndex(0, "A");
        intList.addToBack(2);
        intlist2.addToFront(4);

        System.out.println("All intial list tests passed");
        testsPassed++;
    }

    // this is redundant but I'm just being thorough
    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertEquals("Initial list should be empty", true, stringList.isEmpty());
        stringList.addAtIndex(0, "A");
        assertEquals("List should not be empty", false, stringList.isEmpty());

        System.out.println("All isEmpty() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        // testing adding when the list is empty
        stringList.addAtIndex(0, "A");

        LinkedListNode<String> expected = new LinkedListNode<String>("A");
        expected.setNext(expected);

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 1, stringList.size());
        assertEquals("isEmpty() does not return correct value", false, stringList.isEmpty());

        // make sure both head and head.next() are the same thing
        assertEquals(expected.getData(), stringList.getHead().getData());
        assertEquals(expected.getData(), stringList.getHead().getNext().getData());

        stringList.addAtIndex(0, "B");
        stringList.addAtIndex(1, "C");
        stringList.addAtIndex(1, "D");
        stringList.addAtIndex(2, "E");
        stringList.addAtIndex(stringList.size(), "F");

        // final check of size
        assertEquals("size incorrectly adjusted", 6, stringList.size());

        // check each node
        LinkedListNode<String> myNode = stringList.getHead();

        assertEquals("Elements not in correct order", "B", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "D", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "E", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "C", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "A", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "F", myNode.getData());
        myNode = myNode.getNext();

        // make sure loop is complete
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(), myNode.getData());

        System.out.println("All addAtIndex() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        // testing adding when the list is empty
        stringList.addToFront("A");

        LinkedListNode<String> expected = new LinkedListNode<String>("A");
        expected.setNext(expected);

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 1, stringList.size());
        assertEquals("isEmpty() does not return correct value", false, stringList.isEmpty());

        // make sure both head and head.next() are the same thing
        assertEquals(expected.getData(), stringList.getHead().getData());
        assertEquals(expected.getData(), stringList.getHead().getNext().getData());

        for (int i = 1; i <= 5; i++) {
            stringList.addToFront("" + (char) ('A' + i));
        }

        // final check of size
        assertEquals("size incorrectly adjusted", 6, stringList.size());

        // check each node
        LinkedListNode<String> myNode = stringList.getHead();

        assertEquals("Elements not in correct order", "F", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "E", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "D", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "C", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "B", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "A", myNode.getData());
        myNode = myNode.getNext();

        // make sure loop is complete
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(), myNode.getData());

        System.out.println("All addToFront() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        // testing adding when the list is empty
        stringList.addToBack("A");

        LinkedListNode<String> expected = new LinkedListNode<String>("A");
        expected.setNext(expected);

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 1, stringList.size());
        assertEquals("isEmpty() does not return correct value", false, stringList.isEmpty());

        // make sure both head and head.next() are the same thing
        assertEquals(expected.getData(), stringList.getHead().getData());
        assertEquals(expected.getData(), stringList.getHead().getNext().getData());

        for (int i = 1; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        // final check of size
        assertEquals("size incorrectly adjusted", 6, stringList.size());

        // check each node
        LinkedListNode<String> myNode = stringList.getHead();

        assertEquals("Elements not in correct order", "A", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "B", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "C", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "D", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "E", myNode.getData());
        myNode = myNode.getNext();

        assertEquals("Elements not in correct order", "F", myNode.getData());
        myNode = myNode.getNext();

        // make sure loop is complete
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(), myNode.getData());

        System.out.println("All addToBack() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        // testing adding when the list is empty
        stringList.addToBack("A");
        stringList.removeAtIndex(0);

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 0, stringList.size());
        assertEquals("isEmpty() does not return correct value", true, stringList.isEmpty());

        for (int i = 0; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        assertEquals("Incorrect value returned", "A", stringList.removeAtIndex(0));
        assertEquals("Incorrect value returned", "D", stringList.removeAtIndex(2));
        assertEquals("Incorrect value returned", "F", stringList.removeAtIndex(3));
        assertEquals("Incorrect value returned", "C", stringList.removeAtIndex(1));
        assertEquals("Incorrect value returned", "E", stringList.removeAtIndex(1));
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(),
                stringList.getHead().getNext().getData());
        assertEquals("Incorrect value returned", "B", stringList.removeAtIndex(0));

        // final check of size
        assertEquals("size incorrectly adjusted", 0, stringList.size());

        System.out.println("All removeAtIndex() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        // testing adding when the list is empty
        stringList.addToBack("A");
        stringList.removeFromFront();

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 0, stringList.size());
        assertEquals("isEmpty() does not return correct value", true, stringList.isEmpty());

        for (int i = 0; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        assertEquals("Incorrect value returned", "A", stringList.removeFromFront());
        assertEquals("Incorrect value returned", "B", stringList.removeFromFront());
        assertEquals("Incorrect value returned", "C", stringList.removeFromFront());
        assertEquals("Incorrect value returned", "D", stringList.removeFromFront());
        assertEquals("Incorrect value returned", "E", stringList.removeFromFront());
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(),
                stringList.getHead().getNext().getData());
        assertEquals("Incorrect value returned", "F", stringList.removeFromFront());
        assertEquals("Empty list should return null", null, stringList.removeFromFront());

        // final check of size
        assertEquals("size incorrectly adjusted", 0, stringList.size());

        System.out.println("All removeFromFront() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        // testing adding when the list is empty
        stringList.addToBack("A");
        stringList.removeFromBack();

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 0, stringList.size());
        assertEquals("isEmpty() does not return correct value", true, stringList.isEmpty());

        for (int i = 0; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        assertEquals("Incorrect value returned", "F", stringList.removeFromBack());
        assertEquals("Incorrect value returned", "E", stringList.removeFromBack());
        assertEquals("Incorrect value returned", "D", stringList.removeFromBack());
        assertEquals("Incorrect value returned", "C", stringList.removeFromBack());
        assertEquals("Incorrect value returned", "B", stringList.removeFromBack());
        assertEquals("End node does not correctly reference head ", stringList.getHead().getData(),
                stringList.getHead().getNext().getData());
        assertEquals("Incorrect value returned", "A", stringList.removeFromBack());
        assertEquals("Empty list should return null", null, stringList.removeFromBack());

        // final check of size
        assertEquals("size incorrectly adjusted", 0, stringList.size());

        System.out.println("All removeFromBack() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {
        // testing adding when the list is empty
        stringList.addToBack("A");
        stringList.removeLastOccurrence("A");

        // make sure size and isEmpty properly updated
        assertEquals("size not correctly updated", 0, stringList.size());
        assertEquals("isEmpty() does not return correct value", true, stringList.isEmpty());

        for (int x = 0; x < 2; x++) {
            for (int i = 0; i < 3; i++) {
                stringList.addToBack("" + (char) ('A' + i));
            }
        }

        assertEquals("Incorrect value returned", null, stringList.removeLastOccurrence("J"));
        assertEquals("Incorrect value returned", "A", stringList.removeLastOccurrence("A"));
        assertEquals("Last occurrence was not the occurrence removed",
                "A", stringList.getHead().getData());
        assertEquals("Incorrect value returned", "A", stringList.removeLastOccurrence("A"));
        assertEquals("Incorrect value returned", null, stringList.removeLastOccurrence("A"));
        assertEquals("Incorrect value returned", "B", stringList.removeLastOccurrence("B"));
        assertEquals("Incorrect value returned", "C", stringList.removeLastOccurrence("C"));

        // final check of size
        assertEquals("size incorrectly adjusted", 2, stringList.size());

        // make sure last node references head node
        assertEquals("End node does not correctly reference head ", stringList.getHead(),
                stringList.getHead().getNext().getNext());

        // make list empty
        assertEquals("Incorrect value returned", "B", stringList.removeLastOccurrence("B"));
        assertEquals("Incorrect value returned", "C", stringList.removeLastOccurrence("C"));

        assertEquals("Empty list should return null", null, stringList.removeLastOccurrence("X"));

        System.out.println("All removeLastOccurrence() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        for (int i = 0; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        assertEquals("Incorrect value returned", "A", stringList.get(0));
        assertEquals("Incorrect value returned", "B", stringList.get(1));
        assertEquals("Incorrect value returned", "C", stringList.get(2));
        assertEquals("Incorrect value returned", "D", stringList.get(3));
        assertEquals("Incorrect value returned", "E", stringList.get(4));
        assertEquals("Incorrect value returned", "F", stringList.get(5));

        // final check of size
        assertEquals("size should not be adjusted", 6, stringList.size());

        System.out.println("All get() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {
        // test if list is empty
        Object[] expectedEmpty = new Object[0];

        assertArrayEquals("Array created when size = 0 is incorrect",
                expectedEmpty, stringList.toArray());

        // add one
        stringList.addToBack("A");
        Object[] expectedOne = {"A"};

        assertArrayEquals("Incorrect array created",
                expectedOne, stringList.toArray());

        // fill list
        for (int i = 1; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }

        Object[] expectedFull = {"A", "B", "C", "D", "E", "F"};
        assertArrayEquals("Array created is incorrect",
                expectedFull, stringList.toArray());

        System.out.println("All toArray() tests passed");
        testsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // this should not cause an error
        stringList.clear();

        // fill list
        for (int i = 1; i <= 5; i++) {
            stringList.addToBack("" + (char) ('A' + i));
        }
        stringList.clear();

        assertEquals("Clear does not reset size", 0, stringList.size());
        assertEquals("Clear does not set head to null", null, stringList.getHead());

        System.out.println("All clear() tests passed");
        testsPassed++;
    }


    /*
      ===================
      |     TESTING     |
      |    EXCEPTIONS   |
      ===================
     */

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestAddIndexNegative() {
        stringList.addAtIndex(-1, "A");
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestAddIndexGreaterSize() {
        stringList.addAtIndex(1, "A");
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestAddIndexData() {
        stringList.addAtIndex(0, null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestAddFrontData() {
        stringList.addToFront(null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestAddBackData() {
        stringList.addToBack(null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestRemoveIndexNegative() {
        stringList.addToFront("A");
        stringList.removeAtIndex(-1);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestRemoveIndexSize() {
        stringList.addToFront("A");
        stringList.removeAtIndex(1);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestRemoveIndexGreaterSize() {
        stringList.addToFront("A");
        stringList.removeAtIndex(2);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestLastOccurrenceData() {
        stringList.removeLastOccurrence(null);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestGetNegative() {
        stringList.get(-1);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestGetSize() {
        stringList.addToFront("A");
        stringList.get(1);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void exceptionTestGetGreaterSize() {
        stringList.addToFront("A");
        stringList.get(2);
        exceptionsFailed++;
    }

    @Test(timeout = TIMEOUT)
    public void zResults() {
        System.out.println("\n\n\n\t\t\t\tRESULTS\n================================================");
        if (testsPassed == 12 && exceptionsFailed == 0) {
            System.out.println("  CONGRATULATIONS YOU'VE PASSED EVERY TEST!!!!");
        } else if (testsPassed == 12 && exceptionsFailed > 0) {
            System.out.println("You passed every test, but failed " + exceptionsFailed + " exception(s)");
        } else {
            System.out.println("     You failed " + (12 - testsPassed) + " test(s) and " + exceptionsFailed + " exception(s)");
        }

        System.out.println("================================================");
    }


}