import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Some fuzzy tests for each of the data structures.
 * If you'd like to just run one of the structures, run the appropriate test.
 * Best of luck with the assignment, hope this helps! :)
 * @author Simar Kareer
 */
public class KareerFuzzyTests {
    private ArrayStack<Integer> userArrayStack;
    private LinkedStack<Integer> userLinkedStack;
    private ArrayQueue<Integer> userArrayQueue;
    private LinkedQueue<Integer> userLinkedQueue;

    private Stack<Integer> javaStack;
    private ArrayDeque<Integer> javaQueue;

    private int iterations = 10000;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @org.junit.Before
    public void setUp() throws Exception {
        userArrayStack = new ArrayStack<>();
        userLinkedStack = new LinkedStack<>();
        userArrayQueue = new ArrayQueue<>();
        userLinkedQueue = new LinkedQueue<>();
        javaStack = new Stack<>();
        javaQueue = new ArrayDeque<>();
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @SuppressWarnings("Duplicates")
    @org.junit.Test
    public void TestArrayStack() {
        Random gen = new Random();
        for (int i = 0; i < iterations; i++) {
            int test = gen.nextInt(3);
            if (test == 0) { //push
                int toAdd = gen.nextInt();
                javaStack.push(toAdd);
                userArrayStack.push(toAdd);
                Object[] shortened = Arrays.copyOf(userArrayStack.getBackingArray(), javaStack.size());
                assertArrayEquals(javaStack.toArray(), shortened);
            } else if (test == 1) { //pop
                assertEquals(javaStack.size(), userArrayStack.size());
                if (javaStack.size() != 0) {
                    assertEquals(javaStack.pop(), userArrayStack.pop());
                }
            } else if (test == 2) { //peek
                if (javaStack.size() == 0) {
                    assertNull(userArrayStack.peek());
                } else {
                    assertEquals(javaStack.peek(), userArrayStack.peek());
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @org.junit.Test
    public void TestLinkedStack() {
        Random gen = new Random();
        for (int i = 0; i < iterations; i++) {
            int test = gen.nextInt(3);
            if (test == 0) { //push
                if (javaStack.size() != 0) {
                    assertEquals(javaStack.peek(), userLinkedStack.peek());
                }
                int toAdd = gen.nextInt();
                javaStack.push(toAdd);
                userLinkedStack.push(toAdd);
                assertEquals(javaStack.peek(), userLinkedStack.peek());
            } else if (test == 1) { //pop
                assertEquals(javaStack.size(), userLinkedStack.size());
                if (javaStack.size() != 0) {
                    assertEquals(javaStack.pop(), userLinkedStack.pop());
                }
            } else if (test == 2) { //peek
                if (javaStack.size() == 0) {
                    assertNull(userLinkedStack.peek());
                } else {
                    assertEquals(javaStack.peek(), userLinkedStack.peek());
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @org.junit.Test
    public void TestLinkedQueue() {
        Random gen = new Random();
        for (int i = 0; i < iterations; i++) {
            int test = gen.nextInt(3);
            if (test == 0) { //push
                if (javaQueue.size() != 0) {
                    assertEquals(javaQueue.peek(), userLinkedQueue.peek());
                }
                int toAdd = gen.nextInt();
                javaQueue.addLast(toAdd);
                userLinkedQueue.enqueue(toAdd);
                assertEquals(javaQueue.peek(), userLinkedQueue.peek());
            } else if (test == 1) { //pop
                assertEquals(javaQueue.size(), userLinkedQueue.size());
                if (javaQueue.size() != 0) {
                    assertEquals(javaQueue.pop(), userLinkedQueue.dequeue());
                }
            } else if (test == 2) { //peek
                if (javaQueue.size() == 0) {
                    assertNull(userLinkedQueue.peek());
                } else {
                    assertEquals(javaQueue.peek(), userLinkedQueue.peek());
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @org.junit.Test
    public void TestArrayQueue() {
        Random gen = new Random();
        for (int i = 0; i < iterations; i++) {
            int test = gen.nextInt(3);
            if (test == 0) { //push
                if (javaQueue.size() != 0) {
                    assertEquals(javaQueue.peek(), userArrayQueue.peek());
                }
                int toAdd = gen.nextInt();
                javaQueue.addLast(toAdd);
                userArrayQueue.enqueue(toAdd);
                assertEquals(javaQueue.peek(), userArrayQueue.peek());
            } else if (test == 1) { //pop
                assertEquals(javaQueue.size(), userArrayQueue.size());
                if (javaQueue.size() != 0) {
                    assertEquals(javaQueue.pop(), userArrayQueue.dequeue());
                }
            } else if (test == 2) { //peek
                if (javaQueue.size() == 0) {
                    assertNull(userArrayQueue.peek());
                } else {
                    assertEquals(javaQueue.peek(), userArrayQueue.peek());
                }
            }
        }
    }
}