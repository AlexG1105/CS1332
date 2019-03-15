
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.LinkedList;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


/**
 * This is a basic set of APE tests for APEDSinglyLinkedList.
 * Passing these does guarantee a gr8 grAde on this assignment.
 * This is sanity.
 * Please check using the APE ASAP to help you get started
 * on the homework and writing JUnits in general.
 *
 * @author The APE
 * @version 42.00
 * @ APEfact Aping definition: "imitate the behavior or manner of
 * (someone or something), especially in an absurd or unthinking way."
 */
public class ApeUnitTests {
    private SinglyLinkedList<String> list;

    public static final int TIMEOUT = 250;

    private static int failures = 0;



    @Before
    public void setUp() {
        list = new SinglyLinkedList<String>();
    }


    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }
    };


    //Adding parameter exceptions.

    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeAddingAtIndexNegativeException() {
        //testing when adding at index < 0
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.addAtIndex(-1, "B");
    }
    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeAddingAtIndexLargerThanSizeException() {
        //testing when adding at index > size
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.addAtIndex(4, "D");
    }

    @Test(expected = java.lang.IllegalArgumentException.class,
            timeout = TIMEOUT)
    public void apeAddingAtIndexIllegalArgumentException() {
        //testing adding null argument
        list.addAtIndex(0, "APE");
        list.addAtIndex(1, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class,
            timeout = TIMEOUT)
    public void apeAddingAtFrontIllegalArgumentException() {
        //testing adding null argument
        list.addToFront("APE");
        list.addToFront(null);
    }
    @Test(expected = java.lang.IllegalArgumentException.class,
            timeout = TIMEOUT)
    public void apeAddingAtBackIllegalArgumentException() {
        //testing adding null argument
        list.addToBack("APE");
        list.addToBack(null);
    }



    //Adding Tests

    @Test(timeout = TIMEOUT)
    public void apeAddingAtIndex() {

        assertEquals(0, list.size());
        assertNull(list.getHead());

        LinkedList<String> javaCompare = new LinkedList<>();

        list.addAtIndex(0, "B");
        assertEquals(list.getHead(), list.getHead().getNext());
        list.addAtIndex(0, "A");
        list.addAtIndex(2, "E");
        list.addAtIndex(2, "D");
        list.addAtIndex(2, "C");
        list.addAtIndex(5, "G");
        list.addAtIndex(5, "F");

        javaCompare.add(0, "B");
        javaCompare.add(0, "A");
        javaCompare.add(2, "E");
        javaCompare.add(2, "D");
        javaCompare.add(2, "C");
        javaCompare.add(5, "G");
        javaCompare.add(5, "F");

        //both linked lists should contain:
        //"A", "B", "C", "D", "E", "F", "G"
        String[] adding = {"A", "B", "C", "D", "E", "F", "G"};


        assertEquals(7, list.size());
        assertEquals(adding.length, list.size());

        LinkedListNode<String> cur = list.getHead();


        for (String s : javaCompare) {
            //System.out.println(s + " =? " + cur.getData()); //debugging aid
            assertNotNull(cur);
            assertEquals(cur.getData(), s);
            cur = cur.getNext();
        }

        assertSame(cur, list.getHead());
        assertArrayEquals((Object[]) adding, list.toArray());


        //test 2 (from TA's)
        setUp();
        assertEquals(0, list.size());
        assertNull(list.getHead());

        list.addAtIndex(0, "0a"); //0a
        list.addAtIndex(1, "1a"); //0a 1a
        list.addAtIndex(2, "2a"); //0a 1a 2a
        list.addAtIndex(3, "3a"); //0a 1a 2a 3a

        assertEquals(4, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("0a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);

    }

    @Test(timeout = TIMEOUT)
    public void apeAddStringsFront() {
        assertEquals(0, list.size());
        assertNull(list.getHead());

        LinkedList<String> javaCompare = new LinkedList<>();

        list.addToFront("0a");

        assertEquals(list.getHead(), list.getHead().getNext());
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");
        list.addToFront("5a");
        list.addToFront("F");
        list.addToFront("E");
        list.addToFront("D");
        list.addToFront("C");
        list.addToFront("B");
        list.addToFront("A"); //A B C D E F 5a 4a 3a 2a 1a 0a

        javaCompare.addFirst("0a");
        javaCompare.addFirst("1a");
        javaCompare.addFirst("2a");
        javaCompare.addFirst("3a");
        javaCompare.addFirst("4a");
        javaCompare.addFirst("5a");
        javaCompare.addFirst("F");
        javaCompare.addFirst("E");
        javaCompare.addFirst("D");
        javaCompare.addFirst("C");
        javaCompare.addFirst("B");
        javaCompare.addFirst("A"); //A B C D E F 5a 4a 3a 2a 1a 0a


        String[] adding = new String[]{"A", "B", "C", "D", "E", "F"
                + "", "5a", "4a", "3a", "2a", "1a", "0a"};


        assertEquals(javaCompare.size(), list.size());
        assertEquals(adding.length, list.size());

        LinkedListNode<String> cur = list.getHead();


        for (String s : javaCompare) {
            //System.out.println(s + " =? " + cur.getData());
            assertNotNull(cur);
            assertEquals(cur.getData(), s);
            cur = cur.getNext();
        }

        assertSame(cur, list.getHead());
        assertArrayEquals((Object[]) adding, list.toArray());



        //TA test
        setUp();
        assertEquals(0, list.size());
        assertNull(list.getHead());


        list.addToFront("0a");

        assertEquals(list.getHead(), list.getHead().getNext());
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");
        list.addToFront("5a");

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("5a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("4a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("0a", current.getData());


    }

    @Test(timeout = TIMEOUT)
    public void apeAddStringsBack() {

        assertEquals(0, list.size());
        assertNull(list.getHead());

        LinkedList<String> javaCompare = new LinkedList<>();


        list.addToBack("Aping ");
        assertEquals(list.getHead(), list.getHead().getNext());
        list.addToBack("definition: ");
        list.addToBack("To Imitate ");
        list.addToBack("in an ");
        list.addToBack("Absurd ");
        list.addToBack("or ");
        list.addToBack("Unthinking ");
        list.addToBack("Way.");

        javaCompare.add("Aping ");
        javaCompare.add("definition: ");
        javaCompare.add("To Imitate ");
        javaCompare.add("in an ");
        javaCompare.add("Absurd ");
        javaCompare.add("or ");
        javaCompare.addLast("Unthinking ");
        javaCompare.addLast("Way.");
        //list should be
        String[] adding = {"Aping ", "definition: ", ""
                        + "To Imitate ", "in an ", "Absurd ", ""
                        + "or ", "Unthinking ", ""
                        + "Way."};

        assertEquals(javaCompare.size(), list.size());
        assertEquals(adding.length, list.size());

        LinkedListNode<String> cur = list.getHead();


        for (String s : javaCompare) {
            //debugging:
            //System.out.println(s + " =? " + cur.getData());


            //fun:
            //System.out.print(cur.getData());

            assertNotNull(cur);
            assertEquals(cur.getData(), s);
            cur = cur.getNext();
        }

        assertSame(cur, list.getHead());
        assertArrayEquals((Object[]) adding, list.toArray());


        //TA tests
        setUp();
        assertEquals(0, list.size());

        list.addToBack("0a");
        list.addToBack("1a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a"); //0a 1a 2a 3a 4a 5a

        assertEquals(6, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("0a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("4a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("5a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);
    }




    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeRemovingAtIndexLargerThanSizeException() {
        //testing when adding at index > size
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.removeAtIndex(list.size());
        //if this works. make sure you are throwing for
        //      "index >= size"
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeRemovingAtIndexNegativeException() {
        //testing when adding at index < 0
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT)
    public void apeRemoveAtIndex() {
        assertEquals(0, list.size());
        String[] aping = "Aping....yuh..".split("");
        LinkedList<String> jvCom = new LinkedList<>();
        for (String c : aping) {
            list.addToBack(c);
            jvCom.add(c);
        }

        //checking initial adding arrays
        assertArrayEquals(aping, list.toArray());
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());

        //remove from size - 1
        int sz = list.size() - 1;
        assertEquals(
                list.removeAtIndex(sz), jvCom.remove(sz));
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());


        //remove from 0
        assertEquals(
                list.removeAtIndex(0), jvCom.remove(0));
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());


        //remove remaining randomly
        while (list.size() > 0) {
            int rmv = (int) (Math.random() * list.size());
            String[] removed = {list.removeAtIndex(rmv), jvCom.remove(rmv)};
            //System.out.println(removed[0] + "=?" + removed[1]);
            assertEquals(removed[0], removed[1]);
            assertArrayEquals(jvCom.toArray(), list.toArray());
            assertEquals(jvCom.size(), list.size());
        }


        assertEquals(0, list.size());
        assertNull(list.getHead());

        //TA example
        setUp();
        assertEquals(0, list.size());

        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");
        list.addAtIndex(5, "5a"); //0a 1a 2a 3a 4a 5a

        assertEquals(6, list.size());

        assertEquals("2a", list.removeAtIndex(2)); //0a 1a 3a 4a 5a

        assertEquals(5, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("0a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("4a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("5a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);

    }

    @Test(timeout = TIMEOUT)
    public void apeRemoveFront() {

        assertEquals(0, list.size());
        String[] aping = "dont_get_aped".split("");

        LinkedList<String> javaCompare = new LinkedList<>();
        for (String c : aping) {
            list.addAtIndex(list.size(), c);
            javaCompare.add(c);
        }

        //checking initial adding arrays
        assertArrayEquals(aping, list.toArray());
        assertArrayEquals(javaCompare.toArray(), list.toArray());
        assertEquals(javaCompare.size(), list.size());


        while (list.size() > 0) {
            String[] removed = {list.removeFromFront(), javaCompare.poll()};
            //System.out.println(removed[0] + "=?" + removed[1]);
            assertEquals(removed[0], removed[1]);
            assertArrayEquals(javaCompare.toArray(), list.toArray());
            assertEquals(javaCompare.size(), list.size());
        }
        assertEquals(0, list.size());
        assertNull(list.getHead());

        //TA test
        setUp();
        assertEquals(0, list.size());

        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");
        list.addAtIndex(5, "5a"); //0a 1a 2a 3a 4a 5a

        assertEquals(6, list.size());

        assertEquals("0a", list.removeFromFront()); //1a 2a 3a 4a 5a

        assertEquals(5, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("4a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("5a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveStringsBack() {

        assertEquals(0, list.size());
        String[] aping = "dont_get_aped".split("");

        LinkedList<String> javaCompare = new LinkedList<>();
        for (String c : aping) {
            list.addAtIndex(list.size(), c);
            javaCompare.add(c);
        }

        //checking initial adding arrays
        assertArrayEquals(aping, list.toArray());
        assertArrayEquals(javaCompare.toArray(), list.toArray());
        assertEquals(javaCompare.size(), list.size());


        while (list.size() > 0) {
            String[] removed = {list.removeFromBack(), javaCompare.pollLast()};
            //System.out.println(removed[0] + "=?" + removed[1]);
            assertEquals(removed[0], removed[1]);
            assertArrayEquals(javaCompare.toArray(), list.toArray());
            assertEquals(javaCompare.size(), list.size());
        }
        assertEquals(0, list.size());
        assertNull(list.getHead());


        //Ta test
        setUp();
        assertEquals(0, list.size());

        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");
        list.addAtIndex(5, "5a"); //0a 1a 2a 3a 4a 5a

        assertEquals(6, list.size());

        assertEquals("5a", list.removeFromBack()); //0a 1a 2a 3a 4a

        assertEquals(5, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("0a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("4a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void apeRemoveLastOccurrenceNullItemException() {
        list.removeLastOccurrence(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {

        //requires working get to accurately test removing
        assertEquals(0, list.size());
        String[] aping = ("NMnn_arc_is_or_isnt_the_ape_ape_"
                + "ape_in_Question???").split("");
        LinkedList<String> jvCom = new LinkedList<>();
        for (String c : aping) {
            list.addToBack(c);
            jvCom.add(c);
        }
        assertNull(list.removeLastOccurrence("1"));


        //checking initial adding arrays
        assertArrayEquals(aping, list.toArray());
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());

        //remove from end: "?"
        assertEquals(
                list.removeLastOccurrence("?"), "?");
        assertTrue(jvCom.removeLastOccurrence("?"));
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());


        //remove from start: "N"
        assertEquals(
                list.removeLastOccurrence("N"), "N");
        assertTrue(jvCom.removeLastOccurrence("N"));
        assertArrayEquals(jvCom.toArray(), list.toArray());
        assertEquals(jvCom.size(), list.size());


        //remove remaining randomly
        while (list.size() > 0) {
            int rmv = (int) (Math.random() * list.size());
            String rmvd;
            if (Math.random() < 0.5) {
                rmvd = list.get(rmv);
            } else {
                rmvd = jvCom.get(rmv);
            }
            //System.out.println("removing " + rmvd + " from index " + rmv);
            assertEquals(
                    list.removeLastOccurrence(rmvd), rmvd);
            assertTrue(jvCom.removeLastOccurrence(rmvd));
            assertArrayEquals(jvCom.toArray(), list.toArray());
            assertEquals(jvCom.size(), list.size());
        }


        assertEquals(0, list.size());
        assertNull(list.getHead());


        //TA tests
        setUp();
        assertEquals(0, list.size());

        String temp = new String("4a");
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, temp);
        list.addAtIndex(5, "5a"); //0a 1a 2a 3a 4a 5a

        assertEquals(6, list.size());

        assertEquals(temp,
                list.removeLastOccurrence(new String("4a"))); //0a 1a 2a 3a 5a

        assertEquals(5, list.size());

        LinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertEquals("0a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("1a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("2a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("3a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertEquals("5a", current.getData());

        current = current.getNext();
        assertNotNull(current);
        assertSame(list.getHead(), current);

    }


    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeGetAtIndexLargerThanSizeException() {
        //testing when adding at index > size
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.get(list.size());
        //if this works. make sure you are throwing for
        //      "index >= size"
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class,
            timeout = TIMEOUT)
    public void apeGetAtIndexNegativeException() {
        //testing when adding at index < 0
        list.addAtIndex(0, "P");
        list.addAtIndex(1, "E");
        list.addAtIndex(0, "A");
        list.get(-1);
    }


    @Test(timeout = TIMEOUT)
    public void apeGetGeneral() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");
        list.addAtIndex(5, "5a"); //0a 1a 2a 3a 4a 5a

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
        assertEquals("5a", list.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void apeToArray() {
        String[] expectedItems = new String[10];

        // Adding items 0a, 1a, ..., 8a, 9a
        for (int x = 0; x < expectedItems.length; x++) {
            expectedItems[x] = "a" + x;
            list.addToBack(expectedItems[x]);
        }

        Object[] array = list.toArray();
        assertEquals(array.length, expectedItems.length);
        assertArrayEquals(expectedItems, array);
    }

    @Test(timeout = TIMEOUT)
    public void apeClearAndIsEmpty() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a"); //0a 1a 2a 3a 4a

        assertEquals(5, list.size());
        assertEquals(false, list.isEmpty());

        list.clear();
        assertEquals(0, list.size());
        assertEquals(null, list.getHead());
        assertEquals(true, list.isEmpty());
    }

    @AfterClass
    public static void finished() {

        if (failures == 0) {
            System.out.println(""
                    + ""
                    + " CONGRATULATIONS! You passed the APE unit tests! "
                    + "You have unlocked a full-grown ascii APE.\n\n"
                    + "           .\"`\".\n"
                    + "       .-./ _=_ \\.-.\n"
                    + "      {  (,(oYo),) }}\n"
                    + "      {{ |   \"   |} }\n"
                    + "      { { \\(---)/  }}\n"
                    + "      {{  }'-=-'{ } }\n"
                    + "      { { }._:_.{  }}\n"
                    + "      {{  } -:- { } }\n"
                    + "      {_{ }`===`{  _}\n"
                    + "     ((((\\)     (/))))\n"
                    + "\n"
                    + "\nAPE Art credit: snavjivan@gatech.edu"
                    + "\n"
                    + "Thank you very much for using APE Unit tests.\n"
                    + "\n"
                    + "Please contact andreas@gatech.edu for "
                    + "any APE related questions.\n"
                    + "Additionally, to discover the truth about APE "
                    + "please visit:\n"

                    + "http://university.apehangers.org/");
        } else {
            System.out.println("APE tests not passed. "
                    + "Try again to discover the APE.");
        }
    }
}