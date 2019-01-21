import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class ArrayListStudentTests {
  private ArrayList<String> list;

  public static final int TIMEOUT = 200;

  @Before
  public void setUp() {
      list = new ArrayList<>();
  }

  @Test(timeout = TIMEOUT)
    public void testAddExceptionBehavior() {
        assertThrowableThrown(() -> list.addAtIndex(-5, "a"),
                IndexOutOfBoundsException.class);
        assertThrowableThrown(() -> list.addAtIndex(list.size() + 1, "a"),
                IndexOutOfBoundsException.class);
        assertNoThrowable(() -> list.addAtIndex(list.size(), "a"));
        assertThrowableThrown(() -> list.addAtIndex(0, null),
                IllegalArgumentException.class);
        assertThrowableThrown(() -> list.addToBack(null),
                IllegalArgumentException.class);
        assertThrowableThrown(() -> list.addToFront(null),
                IllegalArgumentException.class);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveExceptionBehavior() {
        assertThrowableThrown(() -> list.removeAtIndex(-5),
                IndexOutOfBoundsException.class);
        assertNoThrowable(() -> list.removeFromBack());
        assertNoThrowable(() -> list.removeFromFront());
        list.addToBack("a");
        list.addToBack("b");
        list.addToBack("c");
        list.addToBack("d");
        assertThrowableThrown(() -> list.removeAtIndex(list.size()),
                IndexOutOfBoundsException.class);
    }

    @Test(timeout = TIMEOUT)
    public void testLastIndexOfExceptionBehavior() {
        assertThrowableThrown(() -> list.lastIndexOf(null),
                IllegalArgumentException.class);
    }

    @Test(timeout = TIMEOUT)
    public void testGrowthBehavior() {
        for (int i = 0; i < 300; ++i) {
            String str = Character.toString((char)('a' + (i % 26))) + i;
            int capacity = list.getBackingArray().length;
            int size = list.size();
            Object[] oldArray = list.getBackingArray();
            if (capacity == size) {
                list.addToBack(str);
                // doubling capacity
                assertEquals(capacity * 2, list.getBackingArray().length);
                // size incremented
                assertEquals(size + 1, list.size());
                // correctly copied
                for (int j = 0; j < oldArray.length; ++j) {
                    assertEquals(oldArray[j], list.get(j));
                }
            } else {
                list.addToBack(str);
                // capacity stays same
                assertEquals(capacity, list.getBackingArray().length);
                // size incremented
                assertEquals(size + 1, list.size());
                // array reference untouched
                assertEquals((Object)oldArray, list.getBackingArray());
            }
        }
    }

    @FunctionalInterface
    public interface Procedure { void run(); }

    public <T extends Throwable> void assertThrowableThrown(Procedure op, Class<T> c) {
        boolean caught = false;
        try {
            op.run();
        } catch (Throwable t) {
            if (t.getClass().equals(c)) caught = true;
        } finally {
            assertTrue(caught);
        }
    }

    public void assertNoThrowable(Procedure op) {
        try {
            op.run();
        } catch (Throwable t) {
            fail();
        }
    }
}
