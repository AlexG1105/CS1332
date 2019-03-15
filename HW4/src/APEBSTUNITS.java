

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Additional BST tests
 *
 * @version 1.0
 * @author Matt Carroll
 */
public class APEBSTUNITS {
    private BST<Integer> bst;
    private BST<Integer> bstBigTree;
    public static final int TIMEOUT = 200;

    private static int failures = 0;






    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }
    };
    @Before
    public void setup() {
        bst = new BST<Integer>();
        bstBigTree = new BST<Integer>();
        bstBigTree.add(24);
        bstBigTree.add(3);
        bstBigTree.add(30);
        bstBigTree.add(1);
        bstBigTree.add(7);
        bstBigTree.add(35);
        bstBigTree.add(10);
        bstBigTree.add(33);
        bstBigTree.add(40);
        bstBigTree.add(9);
        bstBigTree.add(20);
        bstBigTree.add(50);
        bstBigTree.add(8);
        bstBigTree.add(60);
        /*
                      24
                    /    \
                   3      30
                  / \      \
                 1   7      35
                      \     / \
                      10   33  40
                      / \       \
                     9  20       50
                    /             \
                   8              60
        */
    }

    @Test(timeout = TIMEOUT)
    public void APETestAdd() {
        /*
                      5
                     / \
                    3   8
                       / \
                      6   9
        */
        bst.add(5);
        bst.add(3);
        bst.add(8);
        assertEquals("Your size is not interating", 3, bst.size());

        bst.add(8);
        bst.add(6);
        bst.add(9);
        assertEquals("Adding a duplicate is increasing your size but it shouldn't be", 5, bst.size());
        assertEquals("5 is not root", (Integer) 5, bst.getRoot().getData());
        assertEquals("3 is not the left child of the root", (Integer) 3, bst.getRoot().getLeft().getData());
        assertEquals("8 is not the right child of the root", (Integer) 8, bst.getRoot().getRight().getData());
        assertEquals("6 is not where expected", (Integer) 6, bst.getRoot().getRight().getLeft().getData());
        assertEquals("9 is not where expected", (Integer) 9, bst.getRoot().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void APETestConstructor() {
        ArrayList<Integer> tester = new ArrayList<>();
        tester.add(1);
        tester.add(2);
        tester.add(3);
        BST<Integer> testConstruct = new BST<>(tester);
        assertEquals("Did not insert correctly", "[1, 2, 3]", testConstruct.inorder().toString());
    }

    @Test(timeout = TIMEOUT)
    public void APETestRemove() {
        /*
                      24                                 20
                    /    \                             /    \
                   3      30                          3      30
                  / \      \                         / \      \
                 1   7      35            ->        1   7      33
                      \     / \                          \      \
                      10   33  40                        10      40
                      / \       \                        /
                     9  20      50                      9
                    /             \
                   8              60
        */

        int[] thingsToRemove = {35, 8, 50, 60, 24};
        int[][] expectedOrders = {{24, 3, 30, 1, 7, 33, 10, 40, 9, 20, 50, 8, 60},
                {24, 3, 30, 1, 7, 33, 10, 40, 9, 20, 50, 60},
                {24, 3, 30, 1, 7, 33, 10, 40, 9, 20, 60},
                {24, 3, 30, 1, 7, 33, 10, 40, 9, 20},
                {20, 3, 30, 1, 7, 33, 10, 40, 9}};
        for (int i = 0; i < thingsToRemove.length-1; i++) {
            assertEquals("The incorrect value was returned",
                    (Integer) thingsToRemove[i], bstBigTree.remove(thingsToRemove[i]));
            //make sure level order is working to test this part
            assertEquals("The tree is not the correct order after the remove",
                    Arrays.toString(expectedOrders[i]), bstBigTree.levelorder().toString());
        }
        assertEquals("The root was not removed correctly", (Integer) 24, bstBigTree.remove(24));
        assertEquals("The tree is not the correct order "
                        + "after the root remove", "[20, 3, 30, 1, 7, 33, 10, 40, 9]",
                bstBigTree.levelorder().toString());
        assertEquals("The size was was not correct", 9, bstBigTree.size());

        bst.add(5);
        bst.add(4);

        assertEquals("The root was not removed correctly", (Integer) 5, bst.remove(5));
        assertEquals("The tree is not the correct order "
                        + "after the root remove", "[4]",
                bst.levelorder().toString());

        bst.add(6);

        assertEquals("The root was not removed correctly", (Integer) 4, bst.remove(4));
        assertEquals("The tree is not the correct order "
                        + "after the root remove", "[6]",
                bst.levelorder().toString());

        assertEquals("The root was not removed correctly", (Integer) 6, bst.remove(6));
        assertEquals("The tree is not the correct order "
                        + "after the root remove", "[]",
                bst.levelorder().toString());

        BST<String> apeTree = new BST<String>();
        BSTNode<String> ape = new BSTNode<>("APES");
        apeTree.add(ape.getData());
        assertSame("The item removed was not the real child but instead" +
                        "just had equivalent data. Make sure to remove the exact child"
                , ape.getData(), apeTree.remove("APES"));
    }

    @Test(timeout = TIMEOUT)
    public void APETestGetContains() {
        assertFalse("There is an issue when the tree has no root", bst.contains(10));

        //see bstBigTree up top
        assertTrue("Contains method ran into an issue", bstBigTree.contains(30));
        assertEquals("Get method is unsuccessful", (Integer) 30, bstBigTree.get(30));
        assertTrue(bstBigTree.contains(60));
        assertEquals((Integer) 60, bstBigTree.get(60));
        assertTrue(bstBigTree.contains(9));
        assertEquals((Integer) 9, bstBigTree.get(9));

        assertFalse("This should return false when the "
                + "data is not contained but it failed to", bstBigTree.contains(12));
    }

    @Test(timeout = TIMEOUT)
    public void APETestGetDifferent() {

        Integer testingInteger = new Integer(12);

        bst.add(5);
        bst.add(2);
        bst.add(4);
        bst.add(testingInteger);
        bst.add(30);
        bst.add(7);

        //ensure the instance gotten is the correct memory location
        assertSame("The memory location of the returned item " +
                        "was not the same as the one passed in"
                , testingInteger, bst.get(new Integer(12)));
    }

    @Test(timeout = TIMEOUT)
    public void APETestLevelorder() {
        //look at bstBigTree to see what the tree looks like
        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(24);
        levelorder.add(3);
        levelorder.add(30);
        levelorder.add(1);
        levelorder.add(7);
        levelorder.add(35);
        levelorder.add(10);
        levelorder.add(33);
        levelorder.add(40);
        levelorder.add(9);
        levelorder.add(20);
        levelorder.add(50);
        levelorder.add(8);
        levelorder.add(60);

        // Should be [24, 3, 30, 1, 7, 35, 10, 33, 40, 9, 20, 50, 8, 60]
        assertEquals("the level order was incorrect", levelorder, bstBigTree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void APETestPreorder() {
        //look at bstBigTree to see what the tree looks like
        List<Integer> preorder = new ArrayList<>();
        preorder.add(24);
        preorder.add(3);
        preorder.add(1);
        preorder.add(7);
        preorder.add(10);
        preorder.add(9);
        preorder.add(8);
        preorder.add(20);
        preorder.add(30);
        preorder.add(35);
        preorder.add(33);
        preorder.add(40);
        preorder.add(50);
        preorder.add(60);

        // Should be [24, 3, 1, 7, 10, 9, 8, 20, 30, 35, 33, 40, 50, 60]
        assertEquals("the pre order was incorrect", preorder, bstBigTree.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void APETestInorder() {
        //look at bstBigTree to see what the tree looks like
        List<Integer> inorder = new ArrayList<>();
        inorder.add(1);
        inorder.add(3);
        inorder.add(7);
        inorder.add(8);
        inorder.add(9);
        inorder.add(10);
        inorder.add(20);
        inorder.add(24);
        inorder.add(30);
        inorder.add(33);
        inorder.add(35);
        inorder.add(40);
        inorder.add(50);
        inorder.add(60);

        // Should be [1, 3, 7, 8, 9, 10, 20, 24, 30, 33, 35, 40, 50, 60]
        assertEquals("the in order was incorrect", inorder, bstBigTree.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void APETestPostorder() {
        //look at bstBigTree to see what the tree looks like
        List<Integer> postorder = new ArrayList<>();
        postorder.add(1);
        postorder.add(8);
        postorder.add(9);
        postorder.add(20);
        postorder.add(10);
        postorder.add(7);
        postorder.add(3);
        postorder.add(33);
        postorder.add(60);
        postorder.add(50);
        postorder.add(40);
        postorder.add(35);
        postorder.add(30);
        postorder.add(24);

        // Should be [1, 8, 9, 20, 10, 7, 3, 33, 60, 50, 40, 35, 30, 24]
        assertEquals("the post order was incorrect", postorder, bstBigTree.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void APETestIsBST() {
        /*
                    50
                  /    \   should return false
                25      75
               /  \    /  \
              1   26  5   76
        */

        BSTNode<Integer> root = new BSTNode<>(50);
        root.setLeft(new BSTNode<>(25));
        root.setRight(new BSTNode<>(75));
        root.getLeft().setLeft(new BSTNode<>(1));
        root.getLeft().setRight(new BSTNode<>(26));
        root.getRight().setLeft(new BSTNode<>(5));
        root.getRight().setRight(new BSTNode<>(76));

        assertFalse("This is not a binary tree becaus of the 5 under 75", BST.isBST(root));

        /*
                    50
                  /    \   should return true
                25      75
               /  \    /  \
              1   26  51   76
        */

        root.getRight().setLeft(new BSTNode<>(51));

        assertEquals("This is a binary tree",true, BST.isBST(root));

        /*
                    50
                  /    \   should return false
                25      75
               /  \    /  \
              1   24  51   76
        */

        root.getLeft().setRight(new BSTNode<>(24));

        assertFalse("This is not a binary tree because of the 24 under 25", BST.isBST(root));
    }

    @Test(timeout = TIMEOUT)
    public void APETestHeight() {
        assertEquals("Incorrect height", 5, bstBigTree.height());
        assertEquals("Incorrect height for empty tree",-1, bst.height());
    }


    //Test exceptions below
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEExceptionTestConstructor() {
        BST<Integer> testConstruct = new BST<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEExceptionTestAdd() {
        bst.add(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void APEExceptionTestRemoveEmpty() {
        bst.remove(5);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEExceptionTestRemoveNull() {
        bstBigTree.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void APEExceptionTestGetEmpty() {
        bst.get(5);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEExceptionTestGetNull() {
        bstBigTree.get(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void APEExceptionTestContainsNull() {
        bstBigTree.contains(null);
    }

    @AfterClass
    public static void ApeInvasionBegins() throws Exception {

        if (failures <= 0)
        {
            //here
            TimeUnit unit = TimeUnit.MILLISECONDS;
            System.out.println();
            System.out.println();            System.out.println();            System.out.println();            System.out.println();            System.out.println();            System.out.println();
            System.out.println("Well done.");

            unit.sleep(2000);
            System.out.println();
            System.out.println();
            System.out.println("You have stopped the invasion of the Ape Kingdom.");

            unit.sleep(2000);
            System.out.println();
            System.out.println();
            System.out.println("The aliens are retreating back into space.");

            unit.sleep(2000);
            System.out.println();
            System.out.println();
            System.out.println( "You must defeat them once and for all.");
            unit.sleep(2000);
            System.out.println();
            System.out.println();
            System.out.println("PREPARE YOUR BATTLESHIP.... (open your default web-browser if nothing happens)");

            unit.sleep(2000);
            System.out.println();
            System.out.println();
            try {
                Desktop.getDesktop().browse(new URL("https://ape-unit.github.io/ape-invasion/").toURI());
            } catch (Exception e) {}
        } else {
            System.out.println("you are not deserving of ape. continue to fix your tests.");
        }
    }
}