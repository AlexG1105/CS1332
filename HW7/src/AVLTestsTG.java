import org.junit.*;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * UI Enabled AVL Tests
 *
 * @author Tillson Galloway
 * @version 1.4
 */
public class AVLTestsTG {

    private static final String VERSION = "1.4";
    private static final String URL = "https://avl-homework-tests.herokuapp.com";

    private AVL<Integer> avlTree;

    @Rule
    public TestRule timeout = new DisableOnDebug(new Timeout(400));

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            if (e.getMessage().substring(0, 5).equals("Test ")) {
                String testInfo = e.getMessage().split(" - ")[0].split(" ")[1];
                printTestURL(testInfo);
            }
        }
    };


    // MARK: Tests

    // Test 1 - Add to AVL without rotation
    @Test
    public void testAddOperations() {

        avlTree.add(5);
        assertNotNull("Test 1.1 - Root node exists", avlTree.getRoot());
        assertNotNull("Test 1.2 - Root data", avlTree.getRoot().getData());
        assertEquals("Test 1.3 - Size is updated after an add", 1, avlTree.size());
        assertEquals("Test 1.4 - Root data is correct", (Integer)5, avlTree.getRoot().getData());
        assertEquals("Test 1.5 - Root height is correct", 0, avlTree.getRoot().getHeight());
        assertEquals("Test 1.6 - Root balance factor is correct", 0, avlTree.getRoot().getBalanceFactor());

        avlTree.add(4);
        assertEquals("Test 1.7 - Root height is correct", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 1.8 - Root balance factor is correct", 1, avlTree.getRoot().getBalanceFactor());

        avlTree = new AVL<>();
        avlTree.add(5);
        avlTree.add(6);
        assertEquals("Test 1.9 - Root height is correct", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 1.10 - Root balance factor is correct", -1, avlTree.getRoot().getBalanceFactor());

        avlTree.add(3);
        assertEquals("Test 1.11 - Size is updated after an add.", 3, avlTree.size());

        avlTree.add(3);
        assertEquals("Test 1.12 - Duplicates should not be added to the AVL.", 3, avlTree.size());

        assertEquals("Test 1.13 - Root height is correct", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 1.14 - Root balance factor is correct", 0, avlTree.getRoot().getBalanceFactor());


        assertTrue("Test 1.15 - Left and right children exist", avlTree.getRoot().getLeft() != null && avlTree.getRoot().getRight() != null);
        assertEquals("Test 1.16 - Left child has correct data", (Integer)3,avlTree.getRoot().getLeft().getData());
        assertEquals("Test 1.17 - Right child has correct data", (Integer)6,avlTree.getRoot().getRight().getData());
        assertEquals("Test 1.18 - Root height is correct", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 1.19 - Root balance factor is correct", 0, avlTree.getRoot().getBalanceFactor());

        assertEquals("Test 1.20 - height() is correct", 1, avlTree.getRoot().getHeight());




    }

    // Test 2 - Add to AVL with right rotation
    @Test
    public void testRightRotation() {
        /*
                a              b
               /              / \
              b        ->    c   a
             /
            c
         */

        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);

        assertEquals("Test 2.1 - New root node has incorrect data after right rotation", (Integer)4,avlTree.getRoot().getData());
        assertEquals("Test 2.2 - Left child has correct data after right rotation", (Integer)3,avlTree.getRoot().getLeft().getData());
        assertEquals("Test 2.3 - Right child has correct data after right rotation", (Integer)5,avlTree.getRoot().getRight().getData());

        assertEquals("Test 2.4 - Root has correct height", 1,avlTree.getRoot().getHeight());
        assertEquals("Test 2.5 - Root has correct balance factor", 0,avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 2.6 - Left child has correct height", 0,avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 2.7 - Right child has correct height", 0,avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 2.8 - Left child has correct balance factor", 0,avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 2.9 - Right child has correct balance factor", 0,avlTree.getRoot().getRight().getBalanceFactor());

        assertEquals("Test 2.10 - AVL updates size after add operation", 3, avlTree.size());

    }

    // Test 3 - Add to AVL with left-right rotation
    @Test
    public void testLeftRotation() {
        /*
               a               b
                \             / \
                 b     ->    a   c
                  \
                   c
         */

        avlTree.add(5);
        avlTree.add(6);
        avlTree.add(7);

        assertEquals("Test 3.1 - New root node has incorrect data after right rotation", (Integer)6,avlTree.getRoot().getData());
        assertEquals("Test 3.2 - Left child has correct data after right rotation", (Integer)5,avlTree.getRoot().getLeft().getData());
        assertEquals("Test 3.3 - Right child has correct data after right rotation", (Integer)7,avlTree.getRoot().getRight().getData());

        assertEquals("Test 3.4 - Root has correct height", 1,avlTree.getRoot().getHeight());
        assertEquals("Test 3.5 - Root has correct balance factor", 0,avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 3.6 - Left child has correct height", 0,avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 3.7 - Right child has correct height", 0,avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 3.8 - Left child has correct balance factor", 0,avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 3.9 - Right child has correct balance factor", 0,avlTree.getRoot().getRight().getBalanceFactor());
    }

    // Test 4 - Add to AVL with right-left rotation
    @Test
    public void testRightLeftRotation() {

        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(7);
        assertEquals("Test 4.1 - New root node has incorrect data after right-left rotation", (Integer)7, avlTree.getRoot().getData());
        assertEquals("Test 4.2 - Left child has correct data after right-left rotation", (Integer)5,avlTree.getRoot().getLeft().getData());
        assertEquals("Test 4.3 - Right child has correct data after right-left rotation", (Integer)10,avlTree.getRoot().getRight().getData());
        assertEquals("Test 4.4 - Root has correct height", 1,avlTree.getRoot().getHeight());
        assertEquals("Test 4.5 - Root has correct balance factor", 0,avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 4.6 - Left child has correct height", 0,avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 4.7 - Right child has correct height", 0,avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 4.8 - Left child has correct balance factor", 0,avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 4.9 - Right child has correct balance factor", 0,avlTree.getRoot().getRight().getBalanceFactor());
    }

    // Test 5 - Add to AVL with left-right rotation
    @Test
    public void testLeftRightRotation() {

        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(7);
        assertEquals("Test 5.1 - New root node has incorrect data after left-right rotation", (Integer)7, avlTree.getRoot().getData());
        assertEquals("Test 5.2 - Left child has correct data after left-right rotation", (Integer)5, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 5.3 - Right child has correct data after left-right rotation", (Integer)10, avlTree.getRoot().getRight().getData());
        assertEquals("Test 5.4 - Root has correct height", 1,avlTree.getRoot().getHeight());
        assertEquals("Test 5.5 - Root has correct balance factor", 0,avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 5.6 - Left child has correct height", 0,avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 5.7 - Right child has correct height", 0,avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 5.8 - Left child has correct balance factor", 0,avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 5.9 - Right child has correct balance factor", 0,avlTree.getRoot().getRight().getBalanceFactor());
    }


    // Test 6 - Rotations in subtrees
    @Test
    public void testRotations() {

        avlTree.add(35);
        avlTree.add(40);
        avlTree.add(45);
        avlTree.add(60);
        avlTree.add(55);
        assertEquals("Test 6.1 - New root node has incorrect data after rotation", (Integer)40,avlTree.getRoot().getData());
        assertEquals("Test 6.2 - Left child has correct data after rotation", (Integer)35,avlTree.getRoot().getLeft().getData());
        assertEquals("Test 6.3 - Right child has correct data after rotation", (Integer)55,avlTree.getRoot().getRight().getData());
        assertEquals("Test 6.4 - Right left child has correct data after rotation", (Integer)45,avlTree.getRoot().getRight().getLeft().getData());
        assertEquals("Test 6.5 - Right right child has correct data after rotation", (Integer)60,avlTree.getRoot().getRight().getRight().getData());

        avlTree.add(42);
        AVLNode<Integer> root = avlTree.getRoot();
        assertNotNull("Test 6.6.1 - A node was set to null during the add operation (root)", root);
        assertNotNull("Test 6.6.2 - A node was set to null during the add operation (left)", root.getLeft());
        assertNotNull("Test 6.6.3 - A node was set to null during the add operation (right)", root.getRight());
        assertNotNull("Test 6.6.4 - A node was set to null during the add operation (left-left)", root.getLeft().getLeft());
        assertNotNull("Test 6.6.5 - A node was set to null during the add operation (left-right)", root.getLeft().getRight());
        assertNotNull("Test 6.6.6 - A node was set to null during the add operation (right-right)", root.getRight().getRight());
        assertEquals("Test 6.7 - New root node has incorrect data after rotation", (Integer)45, root.getData());
        assertEquals("Test 6.8 - Left child has correct data after rotation", (Integer)40, root.getLeft().getData());
        assertEquals("Test 6.9 - Right child has correct data after rotation", (Integer)55,root.getRight().getData());
        assertEquals("Test 6.10 - Left left child has correct data after rotation", (Integer)35,root.getLeft().getLeft().getData());
        assertEquals("Test 6.11 - Left right child has correct data after rotation", (Integer)42,root.getLeft().getRight().getData());
        assertEquals("Test 6.12 - Right right child has correct data after rotation", (Integer)60,root.getRight().getRight().getData());

        avlTree.add(65);
        root = avlTree.getRoot();
        assertNotNull("Test 6.13.1 - A node was set to null during the add operation (root)", root);
        assertNotNull("Test 6.13.2 - A node was set to null during the add operation (left)", root.getLeft());
        assertNotNull("Test 6.13.3 - A node was set to null during the add operation (right)", root.getRight());
        assertNotNull("Test 6.13.4 - A node was set to null during the add operation (left-left)", root.getLeft().getLeft());
        assertNotNull("Test 6.13.5 - A node was set to null during the add operation (left-right)", root.getLeft().getRight());
        assertNotNull("Test 6.13.6 - A node was set to null during the add operation (right-left)", root.getRight().getRight());
        assertNotNull("Test 6.13.6 - A node was set to null during the add operation (right-right)", root.getRight().getRight());
        assertEquals("Test 6.14 - New root node has incorrect data after rotation", (Integer)45, root.getData());
        assertEquals("Test 6.15 - Left child has correct data after rotation", (Integer)40, root.getLeft().getData());
        assertEquals("Test 6.16 - Right child has correct data after rotation", (Integer)60,root.getRight().getData());
        assertEquals("Test 6.17 - Left left child has correct data after rotation", (Integer)35,root.getLeft().getLeft().getData());
        assertEquals("Test 6.18 - Left right child has correct data after rotation", (Integer)42,root.getLeft().getRight().getData());
        assertEquals("Test 6.19 - Right right child has correct data after rotation", (Integer)55,root.getRight().getLeft().getData());
        assertEquals("Test 6.20 - Right right child has correct data after rotation", (Integer)65,root.getRight().getRight().getData());

        avlTree.add(62);
        avlTree.add(63);
        avlTree.add(67);
        avlTree.add(30);
        avlTree.add(25);
        root = avlTree.getRoot();
        assertNotNull("Test 6.21.1 - A node was set to null during the add operation (root)", root);
        assertNotNull("Test 6.21.2 - A node was set to null during the add operation (left)", root.getLeft());
        assertNotNull("Test 6.21.3 - A node was set to null during the add operation (right)", root.getRight());
        assertNotNull("Test 6.21.4 - A node was set to null during the add operation (left-left)", root.getLeft().getLeft());
        assertNotNull("Test 6.21.5 - A node was set to null during the add operation (left-right)", root.getLeft().getRight());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (right-left)", root.getRight().getLeft());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (right-right)", root.getRight().getRight());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (left-left-left)", root.getLeft().getLeft().getLeft());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (left-left-right)", root.getLeft().getLeft().getRight());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (right-left-left)", root.getRight().getLeft().getLeft());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (right-left-right)", root.getRight().getLeft().getRight());
        assertNotNull("Test 6.21.6 - A node was set to null during the add operation (right-right-right)", root.getRight().getRight().getRight());
        assertEquals("Test 6.22 - New root node has incorrect data after rotation", (Integer)45, root.getData());
        assertEquals("Test 6.23 - Left child has correct data after rotation", (Integer)40, root.getLeft().getData());
        assertEquals("Test 6.24 - Right child has correct data after rotation", (Integer)63,root.getRight().getData());
        assertEquals("Test 6.25 - Left left child has correct data after rotation", (Integer)30,root.getLeft().getLeft().getData());
        assertEquals("Test 6.26 - Left right child has correct data after rotation", (Integer)42,root.getLeft().getRight().getData());
        assertEquals("Test 6.27 - Right right child has correct data after rotation", (Integer)60,root.getRight().getLeft().getData());
        assertEquals("Test 6.28 - Right right child has correct data after rotation", (Integer)65,root.getRight().getRight().getData());
        assertEquals("Test 6.29 - Left-left-left has correct data after rotation", (Integer)25,root.getLeft().getLeft().getLeft().getData());
        assertEquals("Test 6.30 - Left-left-right child has correct data after rotation", (Integer)35,root.getLeft().getLeft().getRight().getData());
        assertEquals("Test 6.31 - Right-left-left child has correct data after rotation", (Integer)55,root.getRight().getLeft().getLeft().getData());
        assertEquals("Test 6.32 - Right-left-right  child has correct data after rotation", (Integer)62,root.getRight().getLeft().getRight().getData());
        assertEquals("Test 6.33 - Right-right-right child has correct data after rotation", (Integer)67,root.getRight().getRight().getRight().getData());

        assertEquals("Test 6.34 - AVL updates size after add operation", 12, avlTree.size());

    }

    // Test 7 - Simple remove from AVL
    @Test
    public void testRemove() {
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);


        avlTree.remove(3);

        assertEquals("Test 7.1 - Size of AVL changes on remove", 2, avlTree.size());
        assertEquals("Test 7.2 - Parent node's reference to removed node is updated to null", null, avlTree.getRoot().getLeft());
        assertNotNull("Test 7.3 - Root node was set to null on remove", avlTree.getRoot());
        assertNotNull("Test 7.4 - Right child node was set to null on remove", avlTree.getRoot().getRight());
        assertEquals("Test 7.5 - Root node has correct data after remove", (Integer)4, avlTree.getRoot().getData());
        assertEquals("Test 7.6 - Right child has correct data after remove", (Integer)5, avlTree.getRoot().getRight().getData());
        assertEquals("Test 7.7 - Root retains correct height after removal of one child", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 7.8 - Balance factor of root is updated after a removal", -1, avlTree.getRoot().getBalanceFactor());

        avlTree.remove(4);

        assertEquals("Test 7.9 - Size of AVL changes on remove", 1, avlTree.size());
        assertEquals("Test 7.10 - Parent node's reference to removed node is updated to null (left)", null, avlTree.getRoot().getLeft());
        assertEquals("Test 7.11 - Parent node's reference to removed node is updated to null (right)", null, avlTree.getRoot().getRight());
        assertNotNull("Test 7.12 - Root node was set to null on remove", avlTree.getRoot());
        assertEquals("Test 7.13 - Root node has correct data after remove", (Integer)5, avlTree.getRoot().getData());
        assertEquals("Test 7.14 - Root's height is updated after a that changes it", 0, avlTree.getRoot().getHeight());
        assertEquals("Test 7.15 - Balance factor of root is updated after a removal", 0, avlTree.getRoot().getBalanceFactor());

        avlTree.remove(5);

        assertEquals("Test 7.16 - Size of AVL changes on remove", 0, avlTree.size());
        assertEquals("Test 7.17 - Root node is AVL is set to null when AVL becomes empty", null, avlTree.getRoot());

        avlTree = new AVL<>();
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);
        avlTree.remove(4);

        assertNotNull("Test 7.18 - Root node does not become null after its removal if it has children", avlTree.getRoot());
        assertTrue("Test 7.19 - Removed nodes with two children should be replaced with its successor", 3 != avlTree.getRoot().getData());
        assertEquals("Test 7.20 - Root node removed with children has incorrect data", (Integer)5, avlTree.getRoot().getData());
        assertEquals("Test 7.21 - Right child node should be null after replacing its parent", null, avlTree.getRoot().getRight());
        assertNotNull("Test 7.22 - Left child node should not be null after removal", avlTree.getRoot().getLeft());
        assertEquals("Test 7.23 - Left child node has correct data after removal", (Integer)3, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 7.24 - Root node has updated height after removal", 1, avlTree.getRoot().getHeight());
        assertEquals("Test 7.25 - Root node has updated balance factor after removal", 1, avlTree.getRoot().getBalanceFactor());

    }

    // Test 8 - Remove from AVL with a right rotation
    @Test
    public void testRemoveLeftRotation() {
        Integer three = new Integer(3);
        Integer four = new Integer(4);
        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(four);
        avlTree.add(three);
        avlTree.add(15);
        avlTree.add(20);

        assertSame("Test 8.0.1 - The correct integer reference is returned on remove.", avlTree.remove(three), three);
        assertSame("Test 8.0.1 - The correct integer reference is returned on remove.", avlTree.remove(four), four);

        assertEquals("Test 8.1 - Size of AVL should change on remove", 4, avlTree.size());
        assertNotNull("Test 8.2 - Root node was set to null on remove", avlTree.getRoot());
        assertNotNull("Test 8.3 - Right child node was set to null on remove", avlTree.getRoot().getRight());
        assertNotNull("Test 8.4 - Left child node was set to null on remove", avlTree.getRoot().getLeft());
        assertNotNull("Test 8.5 - Left-right child node should be null after remove", avlTree.getRoot().getLeft().getRight());
        assertEquals("Test 8.6 - Root node data is incorrect after a right rotation removal", (Integer)15, avlTree.getRoot().getData());
        assertEquals("Test 8.7 - Left child node data is incorrect after a left rotation removal", (Integer)5, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 8.8 - Right child node data is incorrect after a left rotation removal", (Integer)20, avlTree.getRoot().getRight().getData());
        assertEquals("Test 8.9 - Left-right child node data is incorrect after a left rotation removal", (Integer)10, avlTree.getRoot().getLeft().getRight().getData());
        assertEquals("Test 8.10 - Root node has correct height after a left rotation removal", 2, avlTree.getRoot().getHeight());
        assertEquals("Test 8.11 - Root node has correct balance factor after a left rotation removal", 1, avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 8.12 - Left child node has correct height after a left rotation removal", 1, avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 8.13 - Left child node has correct balance factor after a left rotation removal", -1, avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 8.14 - Right child node has correct height after a left rotation removal", 0, avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 8.15 - Right child node has correct balance factor after a left rotation removal", 0, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals("Test 8.16 - Left-right child node has correct height after a left rotation removal", 0, avlTree.getRoot().getLeft().getRight().getHeight());
        assertEquals("Test 8.17 - Left-right child node has correct balance factor after a left rotation removal", 0, avlTree.getRoot().getLeft().getRight().getBalanceFactor());


    }

    // Test 9 - Remove from AVL with a right rotation
    @Test
    public void testRemoveRightRotation() {
        Integer intToTest = new Integer(20);
        avlTree.add(14);
        avlTree.add(16);
        avlTree.add(intToTest);
        avlTree.add(8);
        avlTree.add(5);

        assertSame("Test 9.0 - The correct integer reference is returned on remove", avlTree.remove(intToTest), intToTest);

        assertEquals("Test 9.1 - Size of AVL should change on remove", 4, avlTree.size());
        assertNotNull("Test 9.2 - Root node was set to null on remove", avlTree.getRoot());
        assertNotNull("Test 9.3 - Right child node was set to null on remove", avlTree.getRoot().getRight());
        assertNotNull("Test 9.4 - Left child node was set to null on remove", avlTree.getRoot().getLeft());
        assertEquals("Test 9.5 - Left-right child node should be null after this operation", null, avlTree.getRoot().getLeft().getRight());
        assertEquals("Test 9.6 - Left-left child node should be null after this rotation", null, avlTree.getRoot().getLeft().getLeft());
        assertNotNull("Test 9.7 - Right child node was set to null on remove", avlTree.getRoot().getRight());
        assertNotNull("Test 9.8 - Right-left child node was set to null on remove", avlTree.getRoot().getRight().getLeft());

        assertEquals("Test 9.9 - Root node data is incorrect after a right rotation removal", (Integer)8, avlTree.getRoot().getData());
        assertEquals("Test 9.10 - Left child node data is incorrect after a left rotation removal", (Integer)5, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 9.11 - Right child node data is incorrect after a left rotation removal", (Integer)16, avlTree.getRoot().getRight().getData());
        assertEquals("Test 9.12 - Right-left child node data is incorrect after a left rotation removal", (Integer)14, avlTree.getRoot().getRight().getLeft().getData());

        assertEquals("Test 9.13 - Root node has correct height after a left rotation removal", 2, avlTree.getRoot().getHeight());
        assertEquals("Test 9.14 - Root node has correct balance factor after a left rotation removal", -1, avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 9.15 - Left child node has correct height after a left rotation removal", 0, avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 9.16 - Left child node has correct balance factor after a left rotation removal", 0, avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals("Test 9.17 - Right child node has correct height after a left rotation removal", 1, avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 9.18 - Right child node has correct balance factor after a left rotation removal", 1, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals("Test 9.19 - Right-left child node has correct height after a left rotation removal", 0, avlTree.getRoot().getRight().getLeft().getHeight());
        assertEquals("Test 9.20 - Right-left child node has correct balance factor after a left rotation removal", 0,
                avlTree.getRoot().getRight().getLeft().getBalanceFactor());

        avlTree.remove(16);
        assertEquals("Test 9.21 - Right child was not updated after removal", (Integer)14, avlTree.getRoot().getRight().getData());

    }

    // Test 10 - More remove cases (adapted from TA tests)
    @Test
    public void testRemoveTA() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       526   856
                 / \                     /
               386 526                 386
         */
        Integer toBeRemoved = 477;
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame("Test 10.0 - The correct data reference is removed from the tree.", toBeRemoved, avlTree.remove(477));

        assertEquals("Test 10.1 - Size of AVL should be 4", 4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals("Test 10.2 - Root data is correct after some removals", (Integer) 646, root.getData());
        assertEquals("Test 10.3 - Root height is correct after some removals", 2, root.getHeight());
        assertEquals("Test 10.4 - Root balance factor is correct after some removals", 1, root.getBalanceFactor());
        assertEquals("Test 10.5 - Left child data is correct after some removals", (Integer) 526, root.getLeft().getData());
        assertEquals("Test 10.6 - Left child height is correct after some removals", 1, root.getLeft().getHeight());
        assertEquals("Test 10.7 - Left child balance factor is correct after some removals", 1, root.getLeft().getBalanceFactor());
        assertEquals("Test 10.8 - Right child data is correct after some removals", (Integer) 856, root.getRight().getData());
        assertEquals("Test 10.9 - Right child height is correct after some removals", 0, root.getRight().getHeight());
        assertEquals("Test 10.10 - Right child balance factor is correct after some removals", 0, root.getRight().getBalanceFactor());
        assertEquals("Test 10.11 - Left-left child data is correct after some removals", (Integer) 386, root.getLeft().getLeft().getData());
        assertEquals("Test 10.12 - Left-left child height is correct after some removals", 0, root.getLeft().getLeft().getHeight());
        assertEquals("Test 10.13 - Left-left child balance factor is correct after some removals", 0, root.getLeft().getLeft().getBalanceFactor());


        avlTree = new AVL<>();
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(10);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(15);
        avlTree.remove(15);

        assertEquals("Test 10.14 - Root does not change after non-rotating leaf removal", (Integer)5, avlTree.getRoot().getData());
        assertEquals("Test 10.16 - Right child does not change after non-rotating leaf removal", (Integer)10, avlTree.getRoot().getRight().getData());
        assertEquals("Test 10.17 - Right-right child is updated to null", null, avlTree.getRoot().getRight().getRight());


    }

    // Test 11 - Get methods
    @Test
    public void testGet() {
        ArrayList<Integer> integersObjs = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            Integer integer = new Integer(i * 5);
            avlTree.add(integer);
            integersObjs.add(integer);
        }

        for (Integer integer : integersObjs) {
            assertSame("Test 11." + integer / 5 + " - " + "AVL finds datum "
                    + integer + " in the tree.", integer, avlTree.get(integer));
        }
    }

    // Test 12 - Contains methods
    @Test
    public void testContains() {
        ArrayList<Integer> intObjs = new ArrayList<>(15);
        for (int i = 0; i < 10; i++) {
            Integer integer = new Integer(i);
            avlTree.add(integer);
            intObjs.add(integer);
        }
        for (Integer integer : intObjs) {
            assertTrue("Test 12." + (integer + 1) + " - AVL.contains(" + integer + ") correctly returns true", avlTree.contains(integer));
        }
        assertTrue("Test 11.12 - AVL.contains(11) correctly returns false", !avlTree.contains(11));
    }

    // Test 13 - Contains methods (adapted from TA tests)
    @Test
    public void testDeepestBranch() {
        // [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(15);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(13);
        avlTree.add(20);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(6);
        avlTree.add(8);
        avlTree.add(14);
        avlTree.add(17);
        avlTree.add(25);
        avlTree.add(0);
        avlTree.add(9);
        avlTree.add(30);

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        expected.add(5);
        expected.add(2);
        expected.add(1);
        expected.add(0);
        expected.add(7);
        expected.add(8);
        expected.add(9);
        expected.add(15);
        expected.add(20);
        expected.add(25);
        expected.add(30);

        assertEquals("Test 13.1 - Deepest branch method returns incorrect data.", expected, avlTree.deepestBranches());
    }

    // Test 14 - Sorted in between tests (adapted from TA tests)
    @Test
    public void testSortedInBetween() {
        /*
                                10
                            /        \
                           5          15
                         /   \      /    \
                        2     7    13    20
                       / \   / \     \  / \
                      1   4 6   8   14 17  25
                     /           \          \
                    0             9         30
         */

        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(15);
        avlTree.add(2);
        avlTree.add(7);
        avlTree.add(13);
        avlTree.add(20);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(6);
        avlTree.add(8);
        avlTree.add(14);
        avlTree.add(17);
        avlTree.add(25);
        avlTree.add(0);
        avlTree.add(9);
        avlTree.add(30);

        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(9);
        expected.add(10);
        expected.add(13);

        assertNotNull("Test 14.1 - sortedInBetween() should never return null.", avlTree.sortedInBetween(7, 8));
        assertEquals("Test 14.2 - sortedInBetween() should not include its bounds.", 0, avlTree.sortedInBetween(7, 8).size());
        assertNotNull("Test 14.3 - sortedInBetween() should never return null.", avlTree.sortedInBetween(7, 14));
        assertEquals("Test 14.4 - sortedInBetween() has correct data (between 7 and 14)", expected, avlTree.sortedInBetween(7, 14));

        List<Integer> expected2 = new ArrayList<>();
        expected2.add(4);
        expected2.add(5);
        expected2.add(6);
        expected2.add(7);

        assertEquals("Test 14.5 - sortedInBetween() has correct data (between 3 and 8)", expected2, avlTree.sortedInBetween(3, 8));


    }

    // Test 15 - Exception handling
    @Test
    public void textExceptions() {

        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(4);

        assertDoesThrowException("Test 15.1 - AVL(null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> new AVL<>(null));

        assertDoesThrowException("Test 15.2 - add(null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.add(null));

        assertDoesThrowException("Test 15.3 - remove(null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.remove(null));

        assertDoesThrowException("Test 15.4 - remove() should throw NoSuchElementException when node is not found",
                NoSuchElementException.class, () -> avlTree.remove(6));

        assertDoesThrowException("Test 15.5 - get(null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.get(null));

        assertDoesThrowException("Test 15.6 - get() should throw NoSuchElementException when node is not found",
                NoSuchElementException.class, () -> avlTree.get(6));

        assertDoesThrowException("Test 15.7 - contains(null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.contains(null));

        assertDoesThrowException("Test 15.8 - sortedInBetween(null, null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.sortedInBetween(null, null));

        assertDoesThrowException("Test 15.9 - sortedInBetween(value, null) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.sortedInBetween(3, null));

        assertDoesThrowException("Test 15.10 - sortedInBetween(null, value) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.sortedInBetween(null, 3));

        ArrayList<Integer> intListWithNull = new ArrayList<>();
        intListWithNull.add(3);
        intListWithNull.add(null);
        intListWithNull.add(5);
        assertDoesThrowException("Test 15.11 - AVL(list) should throw IllegalArgumentException when it has null elements.",
                IllegalArgumentException.class, () -> new AVL<>(intListWithNull));

        assertDoesThrowException("Test 15.12 - sortedInBetween(5, 2) should throw IllegalArgumentException",
                IllegalArgumentException.class, () -> avlTree.sortedInBetween(5, 2));


    }

    // Test 16 - clear()
    @Test
    public void clearTest() {
        avlTree.add(5);
        avlTree.add(53);
        avlTree.add(532);
        avlTree.add(1);
        assertEquals("Test 16.1 - height() returns the correct value.", 2, avlTree.height());
        avlTree.clear();
        assertEquals("Test 16.2 - clear() correctly sets the root to null.", null, avlTree.getRoot());
        assertEquals("Test 16.3 - clear() correctly sets the size to 0.", 0, avlTree.size());
        assertEquals("Test 16.4 - height() returns -1 when the tree does not have a root.", -1, avlTree.height());

    }

    // Test 17 - Removing with successor replacements
    @Test
    public void successorRemove() {
        Integer three = new Integer(3);
        avlTree.add(10);
        avlTree.add(5);
        avlTree.add(15);
        avlTree.add(6);
        avlTree.add(three);
        avlTree.add(12);
        avlTree.add(25);
        avlTree.add(1);
        avlTree.add(4);


        /*
        Before the removal:
                   10
                /      \
               5        15
             /   \     /  \
            3     6   12   25
           / \
          1   4

        When 5 is removed, it is replaced with the successor, putting the left subtree out of balance.
         */

        avlTree.remove(5);

        assertNotNull("Test 17.1 - Root is not null", avlTree.getRoot());
        assertNotNull("Test 17.2 - Left child is not null", avlTree.getRoot().getLeft());
        assertEquals("Test 17.3 - Replaced node height was incorrectly updated after a successor replacement.", 2, avlTree.getRoot().getLeft().getHeight());
        assertEquals("Test 17.4 - Replaced node balance factor was incorrectly updated updated after a successor replacement", -1, avlTree.getRoot().getLeft().getBalanceFactor());
        assertNotNull("Test 17.5 - Left-left child is not null", avlTree.getRoot().getLeft().getLeft());
        assertNotNull("Test 17.6 - Left-right child is not null", avlTree.getRoot().getLeft().getRight());
        assertNotNull("Test 17.7 - Left-right-left child is not null", avlTree.getRoot().getLeft().getRight().getLeft());

        assertEquals("Test 17.8 - Root is 10 after a removal", (Integer) 10, avlTree.getRoot().getData());
        assertSame("Test 17.9 - Left node is the same reference to &three", three, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 17.10 - Left-left is 1 after a removal", (Integer) 1, avlTree.getRoot().getLeft().getLeft().getData());
        assertEquals("Test 17.11 - Left-right is 6 after a removal", (Integer) 6, avlTree.getRoot().getLeft().getRight().getData());
        assertEquals("Test 17.12 - Left-right-left is 4 after a removal", (Integer) 4, avlTree.getRoot().getLeft().getRight().getLeft().getData());

        assertEquals("Test 17.13 - Left-right child has incorrect height", 1, avlTree.getRoot().getLeft().getRight().getHeight());
        assertEquals("Test 17.15 - Left-right child has incorrect balance factor", 1, avlTree.getRoot().getLeft().getRight().getBalanceFactor());

        avlTree.remove(25);

        assertEquals("Test 17.16 - Leaf node was correctly removed", null, avlTree.getRoot().getRight().getRight());


        /*
        Before the removal:
                   10
                /      \
               3        15
             /   \     /
            1     6   12
                 /
                4

        When 10 is removed, it is replaced with its successor, putting the root out of balance.
        What is the balance factor of the left child node (3), and what does that mean for your rotation algorithm?


                   12
                /      \
               3        15
             /   \
            1     6
                 /
                4


                   12 (balance factor 2)
                /      \
               3        15
             /   \
            1     6
                 /
                4

           The left child, 3, is right heavy and has a balance factor of -1.
           This means we need a left-right rotation.


           Left rotation on the left child (3):

                   12
                /      \
               6        15
             /
            3
           /  \
          1    4

        Finally, we do a right rotation:

                  6
                /   \
               3     12
              /  \     \
             1    4    15


         */

        avlTree.remove(10);


        assertNotNull("Test 17.17 - Root is not null", avlTree.getRoot());
        assertEquals("Test 17.18 - Root node height was not updated after a successor replacement.", 2, avlTree.getRoot().getHeight());
        assertEquals("Test 17.19 - Root note balance factor was not updated after a successor replacement.", 0, avlTree.getRoot().getBalanceFactor());
        assertEquals("Test 17.20 - Root node has correct value after successor replacement", (Integer) 6, avlTree.getRoot().getData());


        assertNotNull("Test 17.21 - Right child is not null", avlTree.getRoot().getRight());
        assertEquals("Test 17.22 - Intermediate node height was not updated after a successor replacement.", 1, avlTree.getRoot().getRight().getHeight());
        assertEquals("Test 17.23 - Intermediate node balance factor was not updated after a successor replacement.", -1, avlTree.getRoot().getRight().getBalanceFactor());
        assertEquals("Test 17.24 - Right child has correct value after successor replacement and rotation", (Integer)12, avlTree.getRoot().getRight().getData());
        assertNotNull("Test 17.25 - Right-right child is not null", avlTree.getRoot().getRight().getRight());
        assertEquals("Test 17.26 - Right-right child is 15 after a removal", (Integer) 15, avlTree.getRoot().getRight().getRight().getData());


        assertNotNull("Test 17.27 - Left-left child is not null", avlTree.getRoot().getLeft().getLeft());
        assertNotNull("Test 17.28 - Left-right child is not null", avlTree.getRoot().getLeft().getRight());
        assertEquals("Test 17.29 - Left child is 3 after a removal", (Integer) 3, avlTree.getRoot().getLeft().getData());
        assertEquals("Test 17.30 - Left-left child is 4 after a removal", (Integer) 1, avlTree.getRoot().getLeft().getLeft().getData());
        assertEquals("Test 17.31 - Left-right child is 1 after a removal", (Integer) 4, avlTree.getRoot().getLeft().getRight().getData());


        avlTree.add(11);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(8);
        avlTree.add(9);
        avlTree.add(10);

        avlTree.remove(6);

        assertEquals("Test 17.32 - Root node is 7", (Integer)7, avlTree.getRoot().getData());
        assertEquals("Test 17.33 - Right child node is 11", (Integer)11, avlTree.getRoot().getRight().getData());
        assertEquals("Test 17.34 - Right-left node is 9", (Integer)9, avlTree.getRoot().getRight().getLeft().getData());
        assertEquals("Test 17.35 - Right-left-left child is 8", (Integer)8, avlTree.getRoot().getRight().getLeft().getLeft().getData());
        assertEquals("Test 17.35 - Right-left-right child is 10", (Integer)10, avlTree.getRoot().getRight().getLeft().getRight().getData());

    }

    // MARK: UI Utilities

    public void printTestURL(String testId) {
        HashSet<Integer> occurrenceSet = new HashSet<>();
        String queryString = VERSION + ":" + testId + ":" + getStringifiedTree(avlTree.getRoot(), occurrenceSet);
        String b64QueryString = Base64.getEncoder().encodeToString(queryString.getBytes());
        System.out.println("=================");
        System.out.println("View your tree at: ");
        System.out.println(URL + "/?test=" + b64QueryString);
        System.out.println("=================");
    }

    public String getStringifiedTree(AVLNode<Integer> node, HashSet<Integer> occurrenceSet) {
        if (node == null) {
            return "";
        } else {
            String string = "";
            if (occurrenceSet.contains(node.getData())) {
                return "[*" + node.getData() + "*," + node.getHeight() + "," + node.getBalanceFactor() + "+|]";
            }
            occurrenceSet.add(node.getData());
            string += "[" + node.getData() + "," + node.getHeight() + "," + node.getBalanceFactor() + "+";
            string += getStringifiedTree(node.getLeft(), occurrenceSet);
            string += "|";
            string += getStringifiedTree(node.getRight(), occurrenceSet);
            string += "]";
            return string;
        }
    }

    private void assertDoesThrowException(String message, Class exceptionType, Runnable runnable) throws AssertionError {
        try {
            runnable.run();
            throw new AssertionError(message);
        } catch (Exception ex) {
            if (!exceptionType.isInstance(ex)) {
                AssertionError failure = new AssertionError(String.format("Unexpected exception thrown for test '%s': %s%n", message, ex.toString()));
                failure.setStackTrace(ex.getStackTrace());
                throw failure;
            }
            assertNotNull("Test 15.1000 - All exceptions thrown must have messages.", ex.getMessage());
        }
    }
}
