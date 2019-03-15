import org.junit.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import javax.swing.*;
import java.util.Scanner;


import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
/**
 * Basic tests for the stack and queue classes.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class apeStacksQueuesAPETests {

    public class APE {
        public String name;

        /**constructor for APE class
         * @param nm of the ape Object
         */
        public APE(String nm) {
            name = nm;
        }
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }
            if (!(o instanceof APE)) {
                return false;
            }
            return this.name.equals(((APE) o).name);
        }

        @Override
        public String toString() {
            return name.toString();
        }
    }

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }
    };
    private static int failures = 0;


    public static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void apeStackComparisonTest() {
        APE[] test4Stack = {new APE("Marc"), new APE("Mark"),
                new APE("Luke"), new APE("APE"),
                new APE("Shark"), new APE("Matt")
                , new APE("Dre"), new APE("Mike"), new APE("AP-APE")}; //length 9

        Stack<APE> javaStack = new Stack<APE>();
        ArrayStack<APE> arrayStack = new ArrayStack<APE>();
        LinkedStack<APE> linkStack = new LinkedStack<APE>();
        for (APE ape : test4Stack) {
            javaStack.push(ape);
            arrayStack.push(ape);
            linkStack.push(ape);
            assertTrue(javaStack.peek().equals(arrayStack.peek()));
            assertTrue(javaStack.peek().equals(linkStack.peek()));
        }
        Object[] backingArray = arrayStack.getBackingArray();
        assertArrayEquals((Object[]) test4Stack, backingArray);

        while (!javaStack.empty()) {
            APE javaApe = javaStack.pop();
            assertTrue(javaApe.equals(arrayStack.pop()));
            assertTrue(javaApe.equals(linkStack.pop()));
        }
    }


    @Test(timeout = TIMEOUT)
    public void apeQueueComparisonTest() {
        APE[] test4Queue = {new APE("Marc1"), new APE("Mark2"),
                new APE("APE3"), new APE("APEE4"),
                new APE("APE5"), new APE("APEEE6")
                , new APE("APEEEEE7"), new APE("APE8"), new APE("AP-APE9")}; //length 9

        LinkedBlockingQueue<APE> javaLQueue = new LinkedBlockingQueue<APE>(20);
        ArrayBlockingQueue<APE> javaAQueue = new ArrayBlockingQueue<APE>(20);
        ArrayQueue<APE> arrayQueue = new ArrayQueue<>();
        LinkedQueue<APE> linkQueue = new LinkedQueue<>();
        for (APE ape : test4Queue) {
            javaAQueue.add(ape);
            javaLQueue.add(ape);
            arrayQueue.enqueue(ape);
            linkQueue.enqueue(ape);
        }

        assertTrue(javaAQueue.peek().equals(arrayQueue.peek()));
        assertTrue(javaLQueue.peek().equals(linkQueue.peek()));

        Object[] backingArray = arrayQueue.getBackingArray();
        assertArrayEquals((Object[]) javaAQueue.toArray(), backingArray);

        while (javaAQueue.size() != 0) {
            assertTrue(javaAQueue.poll().equals(arrayQueue.dequeue()));
            assertTrue(javaLQueue.poll().equals(linkQueue.dequeue()));
        }
    }


    @Test(timeout = TIMEOUT)
    public void APEtestArrayStackPush() {
        //APEtests
        ArrayStack<APE> ape = new ArrayStack<>();
        int i;
        for (i = 0; i < 9; i++) {
            ape.push(new APE(getRandomString()));
        }
        assertEquals(9, ape.size());
        assertEquals(9, ape.getBackingArray().length);
        ape.push(new APE(getRandomString()));
        i++;
        int j = i;
        assertEquals(ape.INITIAL_CAPACITY * 2, ape.getBackingArray().length);
        for (i = 0 + i; i < 9 + j; i++) {
            ape.push(new APE(getRandomString()));
        }
        assertEquals(ape.INITIAL_CAPACITY * 2 * 2,  ape.getBackingArray().length);

        //TA tests
        arrayStack = new ArrayStack<>();
        assertEquals(0, arrayStack.size());

        // [34, 29, 48, 59, _, _, _, _, _]
        arrayStack.push(34);
        arrayStack.push(29);
        arrayStack.push(48);
        arrayStack.push(59);

        assertEquals(4, arrayStack.size());

        Object[] backingArray = arrayStack.getBackingArray();

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void APEArrayStackPop() {
        ArrayStack<APE> ape = new ArrayStack<>();
        assertEquals(0, ape.size());

        ape.push(new APE("                              .=\"=.          "));
        ape.push(new APE("                            _/.-.-.\\_     _  "));
        ape.push(new APE("                           ( ( o o ) )    )) "));
        ape.push(new APE("                            |/  \"  \\|    //  "));
        ape.push(new APE("    .-------.                \'---'/    //   "));
        ape.push(new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        ape.push(new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        ape.push(new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        ape.push(new APE("   |o=======.|              /   /    \\  /    "));
        ape.push(new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        ape.push(new APE("                         '-- \"--'  \'--'      "));




        assertEquals(ape.getBackingArray().length,  ape.INITIAL_CAPACITY * 2);

        // uncomment to see the chimp
        /*
        System.out.println("ITS A CHIMP! Almost Passed all the tests!");
        for (Object a :ape.getBackingArray()) {
            if (a != null) {
                System.out.println(a);
            }
        }
        */

        assertEquals(ape.peek(),
                new APE("                         '-- \"--'  \'--'      "));
        assertEquals(ape.pop(),
                new APE("                         '-- \"--'  \'--'      "));

        assertEquals(ape.peek(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));

        assertEquals(ape.pop(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        assertEquals(ape.pop(), new APE("   |o=======.|              /   /    \\  /    "));
        assertEquals(ape.pop(), new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        assertEquals(ape.pop(), new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        assertEquals(ape.pop(), new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        assertEquals(ape.pop(), new APE("    .-------.                \'---'/    //   "));
        assertEquals(ape.pop(), new APE("                            |/  \"  \\|    //  "));
        assertEquals(ape.pop(), new APE("                           ( ( o o ) )    )) "));
        assertEquals(ape.pop(), new APE("                            _/.-.-.\\_     _  "));
        assertEquals(ape.peek(), new APE("                              .=\"=.          "));
        assertEquals(ape.pop(), new APE("                              .=\"=.          "));



        // TA tests
        arrayStack = new ArrayStack<>();
        assertEquals(0, arrayStack.size());

        // [34, 29, 48, 59, _, _, _, _, _]
        arrayStack.push(34);
        arrayStack.push(29);
        arrayStack.push(48);
        arrayStack.push(59);

        // [34, 29, 48, _, _, _, _, _, _]
        assertEquals((Integer) 59, arrayStack.pop());

        assertEquals(3, arrayStack.size());

        Object[] backingArray = arrayStack.getBackingArray();

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void aPEArrayStackPeek() {
        //ta test
        arrayStack = new ArrayStack<>();
        assertEquals(0, arrayStack.size());

        // [34, 29, 48, 59, _, _, _, _, _]
        arrayStack.push(34);
        arrayStack.push(29);
        arrayStack.push(48);
        arrayStack.push(59);

        assertEquals((Integer) 59, arrayStack.peek());
        assertEquals(4, arrayStack.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPush() {

        //APEtests
        ArrayStack<APE> ape = new ArrayStack<>();
        assertEquals(0, ape.size());
        int i;
        for (i = 0; i < 9; i++) {
            ape.push(new APE(getRandomString()));
        }
        assertEquals(9, ape.size());
        ape.push(new APE(getRandomString()));

        assertEquals(10, ape.size());
        //ta test
        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());

        // 59 -> 48 -> 29 -> 34
        linkedStack.push(34);
        linkedStack.push(29);
        linkedStack.push(48);
        linkedStack.push(59);

        assertEquals(4, linkedStack.size());

        LinkedNode<Integer> curr = linkedStack.getHead();
        assertNotEquals(null, curr);
        assertEquals((Integer) 59, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 48, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 29, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 34, curr.getData());

        curr = curr.getNext();
        assertEquals(null, curr);
    }

    @Test(timeout = TIMEOUT)
    public void apeLinkedStackPop() {

        LinkedStack<APE> ape = new LinkedStack<>();

        ape.push(new APE("                              .=\"=.          "));
        ape.push(new APE("                            _/.-.-.\\_     _  "));
        ape.push(new APE("                           ( ( o o ) )    )) "));
        ape.push(new APE("                            |/  \"  \\|    //  "));
        ape.push(new APE("    .-------.                \'---'/    //   "));
        ape.push(new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        ape.push(new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        ape.push(new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        ape.push(new APE("   |o=======.|              /   /    \\  /    "));
        ape.push(new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        ape.push(new APE("                         '-- \"--'  \'--'      "));



        assertEquals(ape.peek(),
                new APE("                         '-- \"--'  \'--'      "));
        assertEquals(ape.pop(),
                new APE("                         '-- \"--'  \'--'      "));

        assertEquals(ape.peek(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));

        assertEquals(ape.pop(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        assertEquals(ape.pop(), new APE("   |o=======.|              /   /    \\  /    "));
        assertEquals(ape.pop(), new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        assertEquals(ape.pop(), new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        assertEquals(ape.pop(), new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        assertEquals(ape.pop(), new APE("    .-------.                \'---'/    //   "));
        assertEquals(ape.pop(), new APE("                            |/  \"  \\|    //  "));
        assertEquals(ape.pop(), new APE("                           ( ( o o ) )    )) "));
        assertEquals(ape.pop(), new APE("                            _/.-.-.\\_     _  "));
        assertEquals(ape.peek(), new APE("                              .=\"=.          "));
        assertEquals(ape.pop(), new APE("                              .=\"=.          "));


        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());

        // 59 -> 48 -> 29 -> 34
        linkedStack.push(34);
        linkedStack.push(29);
        linkedStack.push(48);
        linkedStack.push(59);

        // 48 -> 29 -> 34
        assertEquals((Integer) 59, linkedStack.pop());

        assertEquals(3, linkedStack.size());

        LinkedNode<Integer> curr = linkedStack.getHead();
        assertNotEquals(null, curr);
        assertEquals((Integer) 48, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 29, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 34, curr.getData());

        curr = curr.getNext();
        assertEquals(null, curr);
    }

    @Test(timeout = TIMEOUT)
    public void APELinkedStackPeek() {

        //tatest
        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());

        // 59 -> 48 -> 29 -> 34
        linkedStack.push(34);
        linkedStack.push(29);
        linkedStack.push(48);
        linkedStack.push(59);

        assertEquals((Integer) 59, linkedStack.peek());
        assertEquals(4, linkedStack.size());
    }

    @Test(timeout = TIMEOUT)
    public void APEArrayQueueEnqueue() {
        //apeTESTS
        Integer[] apeInts = {0,1,2,3,4,5,6,7,8};
        ArrayQueue<Integer> apes = new ArrayQueue<>();
        for (Integer i : apeInts) {
             apes.enqueue(i);
        }
        assertArrayEquals(apes.getBackingArray(), (Object[]) apeInts);

        for (int i = 0; i < 5; i++) {
            assertEquals(apes.dequeue(), apeInts[i]);
            assertNull(apes.getBackingArray()[i]);
            apeInts[i] = null;
        }
        assertArrayEquals(apes.getBackingArray(), (Object[]) apeInts);
        for (int i = 0; i < 5; i++) {
            apes.enqueue(i);
            apeInts[i] = i;
        }
        assertArrayEquals(apes.getBackingArray(), (Object[]) apeInts);



        int sizeCount = apes.size();
        assertEquals(sizeCount, apes.getBackingArray().length);
        assertEquals(sizeCount, apes.size());
        //System.out.println(java.util.Arrays.toString(((Object[]) apes.getBackingArray())));

        apes.enqueue(69);
        Integer[] apeInt = {5, 6, 7, 8, 0, 1, 2, 3, 4, 69, null, null, null, null, null, null, null, null};
        assertEquals("size isnt maintained after enqueue causes resizing", ++sizeCount, apes.size());
        //System.out.println(java.util.Arrays.toString(((Object[]) apes.getBackingArray())));
        Integer[] apeFINALFORM = new Integer[576];
        int tot;
        for (tot = 0; apeInt[tot] != null; tot++) {
            apeFINALFORM[tot] = apeInt[tot];
        }

        assertArrayEquals(apes.getBackingArray(), (Object[]) apeInt);
        for (int i = 0; i < 420; i++) {
            apeFINALFORM[tot + i] = i;
            apes.enqueue(i);
            assertEquals("size isnt maintained after enqueue causes resizing", ++sizeCount, apes.size());
            //apeInts[i] = i;
        }

        //System.out.println(apes.getBackingArray().length);
        //System.out.println(java.util.Arrays.toString(((Object[]) apes.getBackingArray())));

        assertArrayEquals(apes.getBackingArray(), (Object[]) apeFINALFORM);


        //System.out.println(java.util.Arrays.toString(((Object[]) apes.getBackingArray())));

        //tatests
        arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());

        // [34, 29, 38, 59, _, _, _, _, _]
        arrayQueue.enqueue(34);
        arrayQueue.enqueue(29);
        arrayQueue.enqueue(48);
        arrayQueue.enqueue(59);

        assertEquals(4, arrayQueue.size());

        Object[] backingArray = arrayQueue.getBackingArray();

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueueDequeue() {
        ArrayQueue<APE> ape = new ArrayQueue<>();

        ape.enqueue(new APE("                              .=\"=.          "));
        ape.enqueue(new APE("                            _/.-.-.\\_     _  "));
        ape.enqueue(new APE("                           ( ( o o ) )    )) "));
        ape.enqueue(new APE("                            |/  \"  \\|    //  "));
        ape.enqueue(new APE("    .-------.                \'---'/    //   "));
        ape.enqueue(new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        ape.enqueue(new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        ape.enqueue(new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        ape.enqueue(new APE("   |o=======.|              /   /    \\  /    "));
        ape.enqueue(new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        ape.enqueue(new APE("                         '-- \"--'  \'--'      "));


        assertEquals(ape.peek(), new APE("                              .=\"=.          "));
        assertEquals(ape.dequeue(), new APE("                              .=\"=.          "));
        assertEquals(ape.dequeue(), new APE("                            _/.-.-.\\_     _  "));
        assertEquals(ape.dequeue(), new APE("                           ( ( o o ) )    )) "));
        assertEquals(ape.dequeue(), new APE("                            |/  \"  \\|    //  "));
        assertEquals(ape.dequeue(), new APE("    .-------.                \'---'/    //   "));
        assertEquals(ape.dequeue(), new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        assertEquals(ape.dequeue(), new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        assertEquals(ape.dequeue(), new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        assertEquals(ape.dequeue(), new APE("   |o=======.|              /   /    \\  /    "));
        assertEquals(ape.dequeue(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        assertEquals(ape.peek(),
                new APE("                         '-- \"--'  \'--'      "));

        assertEquals(ape.dequeue(),
                new APE("                         '-- \"--'  \'--'      "));





        arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());

        // [34, 29, 38, 59, _, _, _, _, _]
        arrayQueue.enqueue(34);
        arrayQueue.enqueue(29);
        arrayQueue.enqueue(48);
        arrayQueue.enqueue(59);

        // [_, 29, 38, 59, _, _, _, _, _]
        assertEquals((Integer) 34, arrayQueue.dequeue());
        Object[] backingArray = arrayQueue.getBackingArray();
        assertEquals(3, arrayQueue.size());

       backingArray = arrayQueue.getBackingArray();

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueuePeek() {
        arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());

        // [34, 29, 38, 59, _, _, _, _, _]
        arrayQueue.enqueue(34);
        arrayQueue.enqueue(29);
        arrayQueue.enqueue(48);
        arrayQueue.enqueue(59);

        assertEquals((Integer) 34, arrayQueue.peek());
        assertEquals(4, arrayQueue.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedQueueEnqueue() {
        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // 34 -> 29 -> 48 -> 59
        linkedQueue.enqueue(34);
        linkedQueue.enqueue(29);
        linkedQueue.enqueue(48);
        linkedQueue.enqueue(59);

        assertEquals(4, linkedQueue.size());

        LinkedNode<Integer> curr = linkedQueue.getHead();
        assertNotEquals(null, curr);
        assertEquals((Integer) 34, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 29, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 48, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 59, curr.getData());
        assertSame(linkedQueue.getTail(), curr);

        curr = curr.getNext();
        assertEquals(null, curr);
    }

    @Test(timeout = TIMEOUT)
    public void APELinkedQueueDequeue() {
        ArrayQueue<APE> ape = new ArrayQueue<>();

        ape.enqueue(new APE("                              .=\"=.          "));
        ape.enqueue(new APE("                            _/.-.-.\\_     _  "));
        ape.enqueue(new APE("                           ( ( o o ) )    )) "));
        ape.enqueue(new APE("                            |/  \"  \\|    //  "));
        ape.enqueue(new APE("    .-------.                \'---'/    //   "));
        ape.enqueue(new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        ape.enqueue(new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        ape.enqueue(new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        ape.enqueue(new APE("   |o=======.|              /   /    \\  /    "));
        ape.enqueue(new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        ape.enqueue(new APE("                         '-- \"--'  \'--'      "));


        assertEquals(ape.peek(), new APE("                              .=\"=.          "));
        assertEquals(ape.dequeue(), new APE("                              .=\"=.          "));
        assertEquals(ape.dequeue(), new APE("                            _/.-.-.\\_     _  "));
        assertEquals(ape.dequeue(), new APE("                           ( ( o o ) )    )) "));
        assertEquals(ape.dequeue(), new APE("                            |/  \"  \\|    //  "));
        assertEquals(ape.dequeue(), new APE("    .-------.                \'---'/    //   "));
        assertEquals(ape.dequeue(), new APE(" =(_|_______|_)=            / /_,_\\ \\  \\   "));
        assertEquals(ape.dequeue(), new APE("   |:::::::::|              \\_\\_'__/ \\  ))  "));
        assertEquals(ape.dequeue(), new APE("   |:::::::[]|               /`  /`~\\  |//   "));
        assertEquals(ape.dequeue(), new APE("   |o=======.|              /   /    \\  /    "));
        assertEquals(ape.dequeue(), new APE(""
                + "   `\"\"\"\"\"\"\"\"\"`          ,--`,--'\\/\\    /     "));
        assertEquals(ape.peek(),
                new APE("                         '-- \"--'  \'--'      "));

        assertEquals(ape.dequeue(),
                new APE("                         '-- \"--'  \'--'      "));

        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // 34 -> 29 -> 48 -> 59
        linkedQueue.enqueue(34);
        linkedQueue.enqueue(29);
        linkedQueue.enqueue(48);
        linkedQueue.enqueue(59);

        // 29 -> 48 -> 59
        assertEquals((Integer) 34, linkedQueue.dequeue());

        assertEquals(3, linkedQueue.size());

        LinkedNode<Integer> curr = linkedQueue.getHead();
        assertNotEquals(null, curr);
        assertEquals((Integer) 29, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 48, curr.getData());

        curr = curr.getNext();
        assertNotEquals(null, curr);
        assertEquals((Integer) 59, curr.getData());
        assertSame(linkedQueue.getTail(), curr);

        curr = curr.getNext();
        assertEquals(null, curr);
    }

    @Test(timeout = TIMEOUT)
    public void APELinkedQueuePeek() {

        //ta tests
        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // 34 -> 29 -> 48 -> 59
        linkedQueue.enqueue(34);
        linkedQueue.enqueue(29);
        linkedQueue.enqueue(48);
        linkedQueue.enqueue(59);

        assertEquals((Integer) 34, linkedQueue.peek());
        assertEquals(4, linkedQueue.size());
    }







    public String getRandomString() {
        return "" + (char) ('A' + Math.random() * 50);
    }


    private ArrayStack arrayStack;
    private ArrayQueue arrayQueue;
    private LinkedStack linkedStack;
    private LinkedQueue linkedQueue;
    private LinkedStack linkStack;
    private Stack javaStack;


    @Test(timeout = TIMEOUT)
    public void InitialTest() {
        arrayStack = new ArrayStack<>();
        linkStack = new LinkedStack<>();
        javaStack = new Stack<>();

        arrayQueue = new ArrayQueue<>();
        linkedQueue = new LinkedQueue<>();

        // test initial size is 0
        assertEquals("initial size should be 0", 0, arrayStack.size());
        assertEquals("initial size should be 0", 0, linkStack.size());
        assertEquals("initial size should be 0", 0, arrayQueue.size());
        assertEquals("initial size should be 0", 0, linkedQueue.size());

        // test front as null
        assertEquals("first index should be null", null, arrayStack.getBackingArray()[0]);
        assertEquals("head should initially be null", null, linkStack.getHead());
        assertEquals("first index should be null", null, arrayQueue.getBackingArray()[0]);
        assertEquals("head should initially be null", null, linkedQueue.getHead());
        assertEquals("tail should initially be null", null, linkedQueue.getTail());
    }

    @Test
    public void KeohaneAPETester() {
        int numTests = 13;
        for (int test = 0; test < numTests; test++) {
            arrayStack = new ArrayStack();
            linkStack = new LinkedStack();
            javaStack = new Stack();

            arrayQueue = new ArrayQueue();
            linkedQueue = new LinkedQueue();
            // test ArrayStackPush
            if (test == 0) {
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayStack.push(adding);
                    javaStack.push(adding);
                }
                // make sure sizes are equal
                assertEquals("Size not correctly updated", javaStack.size(), arrayStack.size());
                // make sure elements are the same
                assertArrayEquals("Elements not correctly added", javaStack.toArray(), arrayStack.getBackingArray());

                // test if array correctly resized
                arrayStack.push("last straw");
                javaStack.push("last straw");
                assertEquals("Backing array not resized properly", 18, arrayStack.getBackingArray().length);

                // one last test
                for(int x = 0; x < 8; x++) {
                    String adding = getRandomString();
                    arrayStack.push(adding);
                    javaStack.push(adding);
                }
                assertArrayEquals("Elements not correctly added", javaStack.toArray(), arrayStack.getBackingArray());
            } else if (test == 1) { // test ArrayStackPop
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayStack.push(adding);
                    javaStack.push(adding);
                }

                assertEquals("Incorrect element removed", javaStack.pop(), arrayStack.pop());
                assertEquals("Size not correctly updated", 8, arrayStack.size());

                for (int x = 7; x >= 0; x--) {
                    assertEquals("Incorrect element removed", javaStack.pop(), arrayStack.pop());
                    assertEquals("Removed element not set to null", null, arrayStack.getBackingArray()[x]);
                }
                assertEquals("size not correctly updated", 0 , arrayStack.size());

            } else if (test == 2) { // test ArrayStackPeek
                assertEquals("Peek on empty array should return null", null, arrayStack.peek());
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayStack.push(adding);
                    javaStack.push(adding);

                    assertEquals("Incorrect element returned", javaStack.peek(), arrayStack.peek());
                }

                assertEquals("peek should not adjust size", 9, arrayStack.size());
            } else if (test == 3) {// test LinkedStackPush
                int repetitions = (int) (Math.random() * 50 + 10);
                for(int x = 0; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkStack.push(adding);
                    javaStack.push(adding);

                    assertEquals("Elements not correctly added", javaStack.peek(), linkStack.getHead().getData());
                }
                // make sure sizes are equal
                assertEquals("Size not correctly updated", javaStack.size(), linkStack.size());
            } else if (test == 4) {// test LinkedStackPop
                int repetitions = (int) (Math.random() * 50 + 10);
                for(int x = 0; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkStack.push(adding);
                    javaStack.push(adding);
                }

                for(int x = 0; x < repetitions; x++) {
                    assertEquals("Incorrect element removed", javaStack.pop(), linkStack.pop());
                    assertEquals("Size incorrectly adjusted", javaStack.size(), linkStack.size());
                }

                assertEquals("Popped node not removed", null, linkStack.getHead());

            } else if (test == 5) { // test LinkedStackPeek
                assertEquals("Peek should return null with empty stack", null, linkStack.peek());
                int repetitions = (int) (Math.random() * 50 + 10);
                for(int x = 0; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkStack.push(adding);
                    javaStack.push(adding);

                    assertEquals("Incorrect element returned", javaStack.peek(), linkStack.peek());
                    assertEquals("Peek should not update size", javaStack.size(), linkStack.size());
                }
            } else if (test == 6) { // test ArrayQueueEnqueue
                Object[] javaArr = new Object[9];
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayQueue.enqueue(adding);
                    javaArr[x] = adding;
                }

                assertArrayEquals("Elements not correctly added", javaArr, arrayQueue.getBackingArray());

                // see if resizing occurs
                arrayQueue.enqueue("Resize");
                assertEquals("Backing array not properly resized", 18, arrayQueue.getBackingArray().length);
            } else if (test == 7) { // test ArrayQueueDequeue
                Object[] javaArr = new Object[9];
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayQueue.enqueue(adding);
                    javaArr[x] = adding;
                }

                for(int x = 0; x < 9; x++) {
                    // make sure right element is removed
                    assertEquals("Incorrect element removed", javaArr[x], arrayQueue.dequeue());

                    // make sure removed spots are set to null
                    assertEquals("Removed data not set to null", null, arrayQueue.getBackingArray()[x]);
                }

                // make sure size changed
                assertEquals("Size not correctly changed", 0, arrayQueue.size());
            } else if (test == 8) { // test circularity of enqueue
                Object[] javaArr = new Object[9];
                for (int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayQueue.enqueue(adding);
                    javaArr[x] = adding;
                }

                // make sure resizing doesn't happen
                arrayQueue.dequeue();
                arrayQueue.enqueue("Index 0");
                assertEquals("Array resized when it wasn't full", 9, arrayQueue.getBackingArray().length);

                // make sure element was enqueued to index 0
                javaArr[0] = "Index 0";
                assertArrayEquals("Check that your array is 'circular'", javaArr, arrayQueue.getBackingArray());

                // make sure front properly wraps around too
                for(int x = 0; x < arrayQueue.getBackingArray().length - 1; x++) {
                    arrayQueue.dequeue();
                }
                assertEquals("Front does not wrap around circular array", javaArr[0], arrayQueue.dequeue());
            } else if (test == 9) { // test ArrayQueuePeek
                assertEquals("Peek on empty array should return null", null, arrayQueue.peek());
                Object[] javaArr = new Object[9];
                for(int x = 0; x < 9; x++) {
                    String adding = getRandomString();
                    arrayQueue.enqueue(adding);
                    javaArr[x] = adding;

                    assertEquals("Incorrect element returned", javaArr[0], arrayQueue.peek());
                }

                assertEquals("peek should not adjust size", 9, arrayQueue.size());
            } else if (test == 10) { // test LinkedQueueEnqueue
                int repetitions = (int) (Math.random() * 50 + 10);
                Object[] javaArr = new Object[repetitions];
                linkedQueue.enqueue("First");
                javaArr[0]  = "First";
                assertEquals("Head and tail must point to same node when size is 1",
                        linkedQueue.getTail(), linkedQueue.getHead());

                for(int x = 1; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkedQueue.enqueue(adding);
                    javaArr[x] = adding;

                    assertEquals("Elements not correctly added", javaArr[x], linkedQueue.getTail().getData());
                }

                assertEquals("Size not correctly adjusted", javaArr.length, linkedQueue.size());
                assertEquals("Head does not point to correct node", javaArr[0], linkedQueue.getHead().getData());
            } else if (test == 11) { // test LinkedQueueDeque
                int repetitions = (int) (Math.random() * 50 + 10);
                Object[] javaArr = new Object[repetitions];
                for(int x = 0; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkedQueue.enqueue(adding);
                    javaArr[x] = adding;
                }

                for(int x = 0; x < repetitions; x++) {
                    assertEquals("Incorrect element removed", javaArr[x], linkedQueue.dequeue());
                }

                linkedQueue.enqueue("A");
                linkedQueue.enqueue("B");
                linkedQueue.dequeue();
                assertEquals("Head node not properly adjusted", "B", linkedQueue.getHead().getData());

                assertEquals("Size not correctly updated", 1, linkedQueue.size());
            } else if (test == 12) { // test LinkedQueuePeek

                assertEquals("Peek should return null when queue is empty",
                        null, linkedQueue.peek());

                int repetitions = (int) (Math.random() * 50 + 10);
                Object[] javaArr = new Object[repetitions];
                for(int x = 0; x < repetitions; x++) {
                    String adding = getRandomString();
                    linkedQueue.enqueue(adding);
                    javaArr[x] = adding;

                    // peek shouldn't get changed by enqueing more elements
                    assertEquals("Incorrect element returned", javaArr[0], linkedQueue.peek());
                }

                for(int x = 0; x < repetitions; x++) {
                    assertEquals("Incorrect element returned", javaArr[x], linkedQueue.peek());
                    linkedQueue.dequeue();
                }
            }
        }
    }




    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEexceptionTestArrayStackPush() {
        arrayStack = new ArrayStack<>();
        arrayStack.push(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void APEexceptionTestArrayStackPop() {
        arrayStack = new ArrayStack<>();
        arrayStack.pop();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEexceptionTestArrayQueueEnqueue() {
        arrayQueue = new ArrayQueue<>();
        arrayQueue.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void APEexceptionTestArrayQueueDeque() {
        arrayQueue = new ArrayQueue<>();
        arrayQueue.dequeue();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEexceptionTestLinkedStackPush() {
        linkStack = new LinkedStack<>();
        linkStack.push(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void APEexceptionTestLinkedStackPop() {
        linkStack = new LinkedStack<>();
        linkStack.pop();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEexceptionTestLinkedQueueEnqueue() {
        linkedQueue = new LinkedQueue<>();
        linkedQueue.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void APEexceptionTestLinkedQueueDeque() {
        linkedQueue = new LinkedQueue<>();
        linkedQueue.dequeue();
    }


    @AfterClass
    public static void finished() throws IOException{
        Runtime.getRuntime().exec("/usr/bin/open -a Terminal /path/to/the/executable");

        if (failures == 0) {
            HangApe.main(new String[1]);
            if (HangApe.count >= 7) {
                System.out.println("GAME OVER." + "\n"
                        + "   ____________" + "\n"
                        + "   |          .-`-." + "\n"
                        + "   |      .-./ _=_ \\.-." + "\n"
                        + "   |     {  (,(oYo),) }}" + "\n"
                        + "   |     {{ |   \"   |} }" + "\n"
                        + "   |     { { \\(---)/  }}" + "\n"
                        + "   |     {{  }'-=-'{ } }" + "\n"
                        + "   |     {{  }'-=-'{ } }" + "\n"
                        + "___|___ ((((\\)     (/))))" + "\n"
                        + "-------------------------------------------------------------------" + "\n"
                        + "   GAME OVER. YOU HAVE FAILED HANGAPE. THE WORD WAS " + "" + "APE   " + "\n"
                        + "-------------------------------------------------------------------" + "\n");
            } else {
                System.out.println(""
                        + "---------------------------------------------------------------\n"
                        + "   CONGRATS. YOU HAVE WON HANGAPE. THE WORD WAS " + "APE" + "  \n"
                        + "---------------------------------------------------------------");
            }
        } else {
            System.out.println("Finish debugging to unlock APE game");
        }
    }


    public static class HangApe {

        private static String word = "APE";
        private static String asterisk = new String(new char[word.length()]).replace("\0", "*");
        public static int count = 0;

        public static void main(String[] args) {
            boolean uno = true;
            String hello = "";
            hello += "------------------------\n";
            hello += "   WELCOME TO HANGAPE   \n";
            hello += "------------------------\n";
            Scanner sc = new Scanner(System.in);
            while (count < 7 && asterisk.contains("*")) {
                hello += "Guess any letter in the word:\n";
                hello += asterisk + "\n";
                String guess = "";
                if (uno) {
                    guess = JOptionPane.showInputDialog(hello);
                    uno = false;
                    hello = "";
                } else {
                    guess = JOptionPane.showInputDialog(hello);
                }
                // guess = sc.next().toUpperCase();
                hello = hang(guess);
            }
            sc.close();
        }

        public static String hang(String guess) {
            boolean bruh = false;
            String messge = "";
            String newasterisk = "";
            for (int i = 0; i < word.length(); i++) {
                if (guess.length() != 0) {
                    if (word.charAt(i) == Character.toUpperCase(guess.charAt(0))) {
                        newasterisk += Character.toUpperCase(guess.charAt(0));
                    } else if (asterisk.charAt(i) != '*') {
                        newasterisk += word.charAt(i);
                    } else {
                        newasterisk += "*";
                    }
                    bruh = true;
                }
            }

            if (asterisk.equals(newasterisk)) {
                if (bruh)
                    count++;
                messge = hangmanImage();
            } else {
                asterisk = newasterisk;
            }
            if (asterisk.equals(word)) {
                return ""
                + "---------------------------------------------------------------\n"
                + "   CONGRATS. YOU HAVE WON HANGAPE. THE WORD WAS " + word + "  \n"
                + "---------------------------------------------------------------";
            }
            return messge;
        }

        public static String hangmanImage() {
            if (count == 1) {
                return ""
                +"BAD GUESS, TRY AGAIN"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "___|___" + "\n"
                +  "\n";
            }
            if (count == 2) {
                return "BAD GUESS, TRY AGAIN" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "___|___" + "\n";
            }
            if (count == 3) {
                return "BAD GUESS, TRY AGAIN" + "\n"
                + "   ____________" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "   | " + "\n"
                + "___|___" + "\n";
            }
            if (count == 4) {
                return "BAD GUESS, TRY AGAIN" + "\n"
                + "   ____________" + "\n"
                + "   |          .-`-." + "\n"
                + "   |         / _=_ \\" + "\n"
                + "   |        (,(oYo),)" + "\n"
                + "   |        |   \"   |" + "\n"
                + "   |         \\(---)/ " + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "___|___" + "\n";
            }
            if (count == 5) {
                return "BAD GUESS, TRY AGAIN" + "\n"
                + "   ____________" + "\n"
                + "   |          .-`-." + "\n"
                + "   |      .-./ _=_ \\.-." + "\n"
                + "   |     {  (,(oYo),) }}" + "\n"
                + "   |     {{ |   \"   |} }" + "\n"
                + "   |     { { \\(---)/  }}" + "\n"
                + "   |" + "\n"
                + "   |" + "\n"
                + "___|___" + "\n";
            }
            if (count == 6) {
                return "BAD GUESS, TRY AGAIN" + "\n"
                + "   ____________" + "\n"
                + "   |          .-`-." + "\n"
                + "   |      .-./ _=_ \\.-." + "\n"
                + "   |     {  (,(oYo),) }}" + "\n"
                + "   |     {{ |   \"   |} }" + "\n"
                + "   |     { { \\(---)/  }}" + "\n"
                + "   |     {{  }'-=-'{ } }" + "\n"
                + "   |" + "\n"
                + "___|___" + "\n";
            }
                return "GAME OVER." + "\n"
                + "   ____________" + "\n"
                + "   |          .-`-." + "\n"
                + "   |      .-./ _=_ \\.-." + "\n"
                + "   |     {  (,(oYo),) }}" + "\n"
                + "   |     {{ |   \"   |} }" + "\n"
                + "   |     { { \\(---)/  }}" + "\n"
                + "   |     {{  }'-=-'{ } }" + "\n"
                + "   |     {{  }'-=-'{ } }" + "\n"
                + "___|___ ((((\\)     (/))))" + "\n"
                + "-------------------------------------------------------------------" + "\n"
                + "   GAME OVER. YOU HAVE FAILED HANGAPE. THE WORD WAS " + word + "   " + "\n"
                + "-------------------------------------------------------------------" + "\n";


        }
    }


}