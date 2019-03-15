import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.*;

/**
 * A set of basic tests to test your HashMap.
 *
 * These tests are exhaustive for put, resize, remove, and clear ONLY 
 *
 * @author Sam
 * @version 1.0
 */
public class SamTests {

    private HashMap<Integer, String> map;
    private HashMap<Integer, String> map2;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _]
        map = new HashMap<>();
        map.put(new Integer(1), "B");
        map.put(new Integer(12), "B1");
        map.put(new Integer(23), "B2");
        map.put(new Integer(34), "B3");
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(11, HashMap.INITIAL_CAPACITY);
        assertEquals(0.67, HashMap.MAX_LOAD_FACTOR, 0.001);

        map = new HashMap<>();
        assertEquals(0, map.size());
        assertEquals(HashMap.INITIAL_CAPACITY, map.getTable().length);

        map = new HashMap<>(23);
        assertEquals(0, map.size());
        assertEquals(23, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void testPutAndResize() {
        //printHash(map);
        // test for replacing data when duplicate key used
        assertEquals("B", map.put(new Integer(1), "B0"));
        // test when adding data in general returns null if not duplicate
        assertEquals(null, map.put(new Integer(6), "F"));
        // resize test
        map.put(new Integer(2), "C");
        map.put(new Integer(3), "D");
        map.put(new Integer(4), "E");
        assertEquals((2 * map.INITIAL_CAPACITY + 1), map.getTable().length);
        map2 = new HashMap<>(23);
        map2.put(new Integer(23), "B2");
        map2.put(new Integer(1), "B0");
        map2.put(new Integer(2), "C");
        map2.put(new Integer(3), "D");
        map2.put(new Integer(4), "E");
        map2.put(new Integer(6), "F");
        map2.put(new Integer(34), "B3");
        map2.put(new Integer(12), "B1");
        String yourMap = printHash(map);
        String myMap = printHash(map2);
        assertEquals(myMap, yourMap);
        // resize test
        map.resizeBackingTable(15);
        map2.resizeBackingTable(15);
        yourMap = printHash(map);
        myMap = printHash(map2);
        assertEquals(myMap, yourMap);

    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        // Scenario where there are multiple values at same index and the front
        // is removed.
        assertEquals("B3", map.remove(new Integer(34)));
        map2 = new HashMap<>(11);
        map2.put(new Integer(1), "B");
        map2.put(new Integer(12), "B1");
        map2.put(new Integer(23), "B2");
        String yourMap = printHash(map);
        String myMap = printHash(map2);
        assertEquals(myMap, yourMap);
        // Scenario where there are multiple values at the same index and the
        // back is removed.
        map.put(new Integer(34), "B3");
        assertEquals("B", map.remove(new Integer(1)));
        map2.clear();
        map2.put(new Integer(12), "B1");
        map2.put(new Integer(23), "B2");
        map2.put(new Integer(34), "B3");
        yourMap = printHash(map);
        myMap = printHash(map2);
        assertEquals(myMap, yourMap);

        // Scenario where there are multiple values at the same index and the
        // found value is in the middle somewhere
        map.clear();
        map.put(new Integer(1), "B");
        map.put(new Integer(12), "B1");
        map.put(new Integer(23), "B2");
        map.put(new Integer(34), "B3");
        assertEquals("B2", map.remove(new Integer(23)));
        map2.clear();
        map2.put(new Integer(1), "B");
        map2.put(new Integer(12), "B1");
        map2.put(new Integer(34), "B3");
        yourMap = printHash(map);
        myMap = printHash(map2);
        assertEquals(myMap, yourMap);

    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveException0() {
        HashMap map = new HashMap();
        map.remove("117"); //not present key
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveException1() {
        HashMap map = new HashMap();
        map.remove(null);
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _] ->
        // [_, _, _, _, _, _, _, _, _, _, _]
        map.clear();
        for (int i = 0; i < HashMap.INITIAL_CAPACITY; i++) {
            assertEquals(null, map.getTable()[i]);
        }
        assertEquals(0, map.size());
    }

    /** method to print hash.
     * @param map map to print
     * @param <K> param key
     * @param <V> param val
     */
    public  <K, V> String printHash(HashMap<K, V> map) {
        String output = "";
        for (MapEntry m : map.getTable()) {
            if (m != null) {
                System.out.print("[ (" + m.getKey().toString()
                        + ", " + m.getValue().toString() + ") ");
                output = ("[ (" + m.getKey().toString()
                        + ", " + m.getValue().toString() + ") ");
                MapEntry cur = m.getNext();
                while (cur != null) {
                    output = output + "(" + cur.getKey().toString()
                            + ", " + cur.getValue().toString() + ") ";
                    System.out.print("(" + cur.getKey().toString()
                            + ", " + cur.getValue().toString() + ") ");
                    cur = cur.getNext();
                }
                System.out.print("], ");
                output = output + "], ";
            } else {
                System.out.print("[], ");
                output = output + "[], ";
            }
        }
        System.out.println();
        return output;
    }
}