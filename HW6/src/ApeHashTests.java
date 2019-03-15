import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


import java.awt.Desktop;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

/**
 * @Author APEEEEEEEEEEEEEEEE
 * assisted by Andreas ApeJug & Sarthak NApejivape
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApeHashTests {
    private static int  failures= 0;
    private static int succeded = 0;
    public static final int TIMEOUT = 500;
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }
        @Override
        protected void succeeded(Description description) {
            succeded++;
        }
    };

    public HashMap setup() {
        HashMap<String, Integer> map = new HashMap<>();
        //hash map of strings containing letters from A -> Z
        for (int i = 0; i < 26; i++) {
            map.put((char) (i + 65) + "", i);
        }
        return map;
    }
    public HashMap setup2() {
        HashMap<APEHash, Integer> map = new HashMap<>();
        //Hash map of costom "HashApe objects" containing letters from A -> Z with hashcodes 1 -> 26
        for (int i = 0; i < 26; i++) {
            String s =
                ((char) (i + 65)) + "";
            map.put(new APEHash(s, i), i);
            //printHash(map2);
            //System.out.println((char) (i + 65) + "");
        }
        map.resizeBackingTable(map.size());
        return map;
    }
    public HashMap setup3() {
        //Hash map of costom "HashApe objects" containing letters from A -> Z with hashcodes i * 26 (all entered into 0 index)
        HashMap<APEHash, Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            String s = (char) (i + 65) + "";
            map.put(new APEHash(s, i * 26), i);
        }
        map.resizeBackingTable(map.size());
        return map;
    }


    @Test (timeout = TIMEOUT)
    public void bApePut() {
        //test resize rate, and adding with different keys which should enter at (mostly) different indexes.
        HashMap<String, Integer> map = new HashMap<String, Integer>(); //map
        MapEntry[] recreated = new MapEntry[map.INITIAL_CAPACITY]; //recreated arr of hash
        Set<String> everyKey = new HashSet(map.INITIAL_CAPACITY * 4 + 3); //set with every key string
        int i = 0;
        for (; (((map.size() + 1.0)) / (map.getTable().length * 1.0)  < map.MAX_LOAD_FACTOR)
            && i < 26; i++) {
            String s = (char) (i + 65) + "";
            map.put(s, i);
            everyKey.add(s);
            recreated[s.hashCode() % map.INITIAL_CAPACITY] = new MapEntry(s,i);
        }
        //printHash(map);
        assertTrue(map.getTable().length == map.INITIAL_CAPACITY);
        assertTrue("recreated should equal original: " + stringHash(map), Arrays.equals(recreated, map.getTable()));
        i++;
        map.put((char) (i + 65) +  "", i);
        everyKey.add((char) (i + 65) +  "");
        //printHash(map)
        assertTrue(map.getTable().length == (map.INITIAL_CAPACITY * 2 + 1));
        i++;
        for (; i < 26; i++) {
            map.put((char) (i + 65) + "", i);
            everyKey.add((char) (i + 65) +  "");
        }

        //printHash(map);
        assertTrue("assert the size is ",map.getTable().length == (map.INITIAL_CAPACITY * 4 + 3));
        recreated = map.getTable(); //get the table

        for (MapEntry m : recreated) {  //check if every element in hash map is actually present.
            if (m != null) {
                assertTrue(""
                        + " Key value incorrect: \"" + m.getKey()
                        + "\" should not have been added: ",
                    everyKey.contains(m.getKey()));
                everyKey.remove(m.getKey());
                MapEntry cur = m.getNext();
                while (cur != null) {
                    assertTrue(""
                            + " Key value incorrect: \"" + m.getKey()
                            + "\" should not have been added: ",
                        everyKey.contains(m.getKey()));
                    everyKey.remove(m.getKey());
                    cur = cur.getNext();
                }
            }
        }
        //adding many times with the same key - using apeHash inner method with constant hashcode
        HashMap<APEHash, String> map2 = new HashMap<>(1);
        assertTrue("initial capacity constructor is broken: (length not 1)" + map2.getTable().length, map2.getTable().length == 1);
        Integer constKey;

        for (i = 0; i < 10; i++) {
            String s = (char) (i + 65) + "";
            if (i > 0) {
                assertTrue("Returned incorrect value while overwriting mapEntry with key " + s
                        + ". Value should be " + (char) (i + 64)
                    , ((char) ((i + 64)) + "").equals(map2.put(new APEHash(s), s)));

            } else {
                assertNull(map2.put(new APEHash(s), s));
            }
            //printHash(map2);
            //System.out.println((char) (i + 65) + "");
        }
        //System.out.println(map2.size());
        assertTrue("Did not overwrite MapEntry with equivalent hashcode.",  map2.getTable()[0].getNext() == null);
        assertTrue("Size should equal 1.", map2.size() == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void bApePutException0() {
        HashMap map = setup();
        map.put(null , 1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void bApePutException1() {
        HashMap map = setup();
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT)
    public void aApeInitialization() {
        assertEquals(0.67, HashMap.MAX_LOAD_FACTOR, 0.001);
        assertEquals(11, HashMap.INITIAL_CAPACITY);

        HashMap map = new HashMap<>();
        assertEquals(0, map.size());
        assertEquals(HashMap.INITIAL_CAPACITY, map.getTable().length);

        map = new HashMap<>(23);
        assertEquals(0, map.size());
        assertEquals(23, map.getTable().length);

    }


    @Test(expected = IllegalArgumentException.class)
    public void dApeResizeException0() {
        HashMap map = setup();
        map.resizeBackingTable(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void dApeResizeException1() {
        HashMap map = setup();
        map.resizeBackingTable(1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void dApeResizeException2() {
        HashMap map = setup();
        map.resizeBackingTable(map.size() - 1);
    }

    @Test(timeout = TIMEOUT)
    public void dApeResize() {
        HashMap map = setup();
        map.resizeBackingTable(map.size());
        assertTrue(map.getTable().length == map.size());
        map.clear();
        map = setup2();
        //printHash(map);
        for (MapEntry m : map.getTable()) {
            assertTrue(m != null);
            assertTrue(m.getNext() == null);
        }

        //add all elements at index 0
        map.clear();
        map = setup3();
        // printHash(map);
        MapEntry cur = map.getTable()[0];
        for (int i = 0; i < 26; i++) {
            if (i != 0 && cur != null) {
                assertTrue("Values should all be in index 0.", cur.getNext() != null);

                cur = cur.getNext();
                assertTrue("Values should all be in index 0.", map.getTable()[i] == null);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    //relies on resize & get method
    public void eApeRemove() {
        HashMap map = setup();
        for (int i = 0; i < 26; i++) {
            assertTrue("Val (" + i + ") is not correctly matched to key "
                + (char) (i + 65) + "",  ((Integer) i).equals(map.remove((char) (i + 65) + "")));
        }
        assertTrue(map.size() == 0);

        map = setup2();
        //printHash(map);
        for (MapEntry m : map.getTable()) {
            assertTrue("resize not working properly.", m != null);
            assertTrue(map.remove(m.getKey()).equals(m.getValue()));
        }

        //add all elements at index 0
        map = setup3();
        MapEntry cur = map.getTable()[0];
        //removes from front to back of linked list
        for (int i = 0; i < 27; i++) {
            if (cur != null) {
                //assertTrue("Values should all be in index 0.", cur.getNext() != null);
                Object temp = map.remove(cur.getKey());
                assertTrue(cur.getValue().equals(temp));
                cur = cur.getNext();
            }
        }
        assertTrue("Size should be 0", map.size() == 0);
        for (MapEntry m : map.getTable()) {
            assertTrue("backing array not empty after removing all elements: \n " + stringHash(map),m == null);
        }


        //add all elements at index 0
        map = setup3();
        cur = map.getTable()[0];
        //removes in a random order of the values in list. tests all edge cases
        for (int i = 0; i < 26; i++) {
            String s = (char) (i + 65) + "";
            APEHash ape = new APEHash(s, i * 26);
            Object v = map.get(ape);  //requires get to work
            assertTrue("(Using Get) requires key's remove value to equal get value. Key is" + ape , v.equals(map.remove(ape)));
        }
        assertTrue("Size should be 0", map.size() == 0);
        for (MapEntry m : map.getTable()) {
            assertTrue("backing array not empty after removing all elements: \n " + stringHash(map),m == null);
        }
    }
    @Test(expected = NoSuchElementException.class)
    public void eApeRemoveException0() {
        HashMap map = new HashMap();
        map.remove("APE"); //not present key
    }
    @Test(expected = IllegalArgumentException.class)
    public void eApeRemoveException1() {
        HashMap map = new HashMap();
        map.remove(null);
    }

    @Test(timeout = TIMEOUT)
    public void cApeClear() {
        HashMap map = setup();
        map.clear();
        assertEquals(0, map.size());
        assertEquals(HashMap.INITIAL_CAPACITY, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void fApeValues() {
        HashMap map = new HashMap();
        List l = map.values();
        assertTrue("List should be empty.", l.size() == 0);
        assertTrue("List should be of type LinkedList or ArrayList.", l instanceof LinkedList || l instanceof ArrayList);
        map = setup();
        HashMap map2 = setup2();
        HashMap map3 = setup3();
        l = map.values();
        List l2 = map2.values();
        List l3 = map3.values();
        //System.out.println(l2);
        for (int i = 0; i < 26; i++) {
            assertTrue("List 1 does not contains all correct values. " + i + " not present.", l.contains((Integer) i));
            assertTrue("List 2 does not contains all correct values. " + i + " not present.", l2.contains((Integer) i));
            assertTrue("List 3 does not contains all correct values. " + i + " not present.", l3.contains((Integer) i));
        }
        assertTrue("List 1 size incorrect: Shouldn't be " + l.size() , l.size() == 26);
        assertTrue("List 2 size incorrect: Shouldn't be " + l2.size() , l2.size() == 26);
        assertTrue("List 3 size incorrect: Shouldn't be " + l3.size() , l3.size() == 26);
    }
    @Test(timeout = TIMEOUT)
    public void gApeKeySet() {
        HashMap map = new HashMap();
        Set s = map.keySet();
        assertTrue("Set should be empty.", s.size() == 0);
        assertTrue("Set should be of type HashSet.", s instanceof HashSet);
        map = setup();
        HashMap map2 = setup2();
        HashMap map3 = setup3();
        s = map.keySet();
        Set s2 = map2.keySet();
        Set s3 = map3.keySet();

        for (int i = 0; i < 26; i++) {
            String ss = ((char) (i + 65)) + "";
            APEHash a2 = new APEHash(ss, i);
            APEHash a3 = new APEHash(ss, i * 26);
            assertTrue("Set 1 does not contains all correct values. " + ss  + " not present.", s.contains(ss));
            assertTrue("Set 2 does not contains all correct values. " + a2 + " not present.", s2.contains(a2));
            assertTrue("Set 3 does not contains all correct values. " + a3 + " not present.", s3.contains(a3));
        }
        assertTrue("Set 1 size incorrect: Shouldn't be " + s.size() , s.size() == 26);
        assertTrue("Set 2 size incorrect: Shouldn't be " + s2.size() , s2.size() == 26);
        assertTrue("Set 3 size incorrect: Shouldn't be " + s3.size() , s3.size() == 26);
    }
    @Test(timeout = TIMEOUT)
    public void hApeContainsKey() {
        HashMap map = new HashMap();
        assertTrue("Shouldn't contain any keys." , !map.containsKey("ape"));
        map = setup();
        HashMap map2 = setup2();
        HashMap map3 = setup3();
        for (int i = 0; i < 26; i++) {
            String ss = ((char) (i + 65)) + "";
            APEHash a2 = new APEHash(ss, i);
            APEHash a3 = new APEHash(ss, i * 26);
            assertTrue("Map1 contains key should return true for: " + ss, map.containsKey(ss));
            assertTrue("Map2 contains key should return true for: " + a2, map2.containsKey(a2));
            assertTrue("Map3 contains key should return true for: " + a3, map3.containsKey(a3));
        }
        assertTrue("Map1 contains key should return false for: APE", !map.containsKey("APE"));
        assertTrue("Map2 contains key should return false for: aped with haschode 420", !map2.containsKey(new APEHash("aped", 420)));
        assertTrue("Map3 contains key should return false for: aping with hashcode 69", !map3.containsKey(new APEHash("aping", 69 )));
    }

    @Test(expected = IllegalArgumentException.class)
    public void hApeContainsKeyException() {
        HashMap m = new HashMap();
        m.containsKey(null);
    }


    @Test(timeout = TIMEOUT)
    public void iApeGet() {
        HashMap map = new HashMap();
        map = setup();
        HashMap map2 = setup2();
        HashMap map3 = setup3();
        for (int i = 0; i < 26; i++) {
            String ss = ((char) (i + 65)) + "";
            APEHash a2 = new APEHash(ss, i);
            APEHash a3 = new APEHash(ss, i * 26);
            assertTrue("Map1 contains key should return true for: " + ss, ((Integer) i).equals(map.get(ss)));
            assertTrue("Map2 contains key should return true for: " + a2, ((Integer) i).equals(map2.get(a2)));
            assertTrue("Map3 contains key should return true for: " + a3, ((Integer) i).equals(map3.get(a3)));
        }
    }
    @Test(expected = NoSuchElementException.class)
    public void iApeGetException1() {
        HashMap m = new HashMap();
        m.get("aPE");
    }
    @Test(expected = IllegalArgumentException.class)
    public void iApeGetException2() {
        HashMap m = new HashMap();
        m.get(null);
    }

    public class APEHash {
        public String s;
        public int hashCode;

        /**
         * constructor for APEs
         * @param str val of string
         */
        public APEHash(String str) {
            s = str;
            hashCode = 0;
        }

        /**
         * overloaded constructor allows setting of hashcode
         * @param str str val
         * @param i hashcode val
         */
        public APEHash(String str, int i) {
            s = str;
            hashCode = i;
        }
        @Override
        public int hashCode() {
            return hashCode;
        }
        @Override
        public boolean equals(Object other) {
            return ((Integer) hashCode()).equals((Integer) ((APEHash) other).hashCode());
        }
        @Override
        public String toString() {
            return s;
        }
    }

    /** method to print hash.
     * @param map map to print
     * @param <K> param key
     * @param <V> param val
     */
    public  <K, V> void printHash(HashMap<K, V> map) {
        for (MapEntry m : map.getTable()) {
            if (m != null) {
                System.out.print("[ (" + m.getKey().toString()
                    + ", " + m.getValue().toString() + ") ");
                MapEntry cur = m.getNext();
                while (cur != null) {
                    System.out.print("(" + cur.getKey().toString()
                        + ", " + cur.getValue().toString() + ") ");
                    cur = cur.getNext();
                }
                System.out.print("], ");
            } else {
                System.out.print("[], ");
            }
        }
    }

    /**
     * returns string of hashmap
     * @param map map to strignify
     * @param <K> param k
     * @param <V> param v
     * @return string of hashmap
     */
    public  <K, V> String stringHash(HashMap<K, V> map) {
        String s = "";
        for (MapEntry m : map.getTable()) {
            if (m != null) {
                s += "[ (" + m.getKey().toString()
                    + ", " + m.getValue().toString() + ") ";
                MapEntry cur = m.getNext();
                while (cur != null) {
                    s += "(" + cur.getKey().toString()
                        + ", " + cur.getValue().toString() + ") ";
                    cur = cur.getNext();
                }
                s += "], ";
            } else {
                s += "[], ";
            }
        }
        return s;
    }



    @AfterClass
    public static void testCompleted() throws Exception {
        //fix success #
        if (failures <= 0  && succeded >= 19) {
            try {
                Desktop.getDesktop().browse(
                    new URL("https://ape-unit.github.io/"
                        + "ape-kong/").toURI());
            } catch (Exception e) { }
        } else {
            System.out.println("Ape game not unlocked."
                + " \nPlease continue to work on your tests.");
        }
    }
}
