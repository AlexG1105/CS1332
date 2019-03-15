import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.AfterClass;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Collections;

import static org.junit.Assert.*;

/*
    Bruh AVLs are mad confusing
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APETesterAVL {
    private static final int TIMEOUT = 300;
    private static AVL<Integer> intList;
    private static ArrayList<Integer> sortedList;
    private static int numTestsPassed = 0;
    private static int numExceptionsFailed = 0;

    @Before
    public void setup() {
        intList = new AVL<>();
        sortedList = new ArrayList<>();
    }

    @Test
    public void testAdd() {
        // add all numbers from 1 to 5 (inclusive)
        for (int i = 1; i <= 5; i++) {
            intList.add(i);
        }

        //          2
        //         / \
        //        1   4
        //           / \
        //          3   5

        AVLNode<Integer> root = intList.getRoot();
        // this method just creates an inorder list
        inOrder(root);
        Object[] correctOrder = {1,2,3,4,5};
        Object[] studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST ADD 1-5 =");
        printTree(intList);

        assertEquals("Tree not properly re-balanced", -1, root.getBalanceFactor());
        assertArrayEquals("Nodes are not in the correct order", correctOrder, studentOrder);
        assertEquals("Size not correctly increased", 5, intList.size());

        // that only tested left rotations so lets test right ones
        // this also includes double rotations, this section is really tricky
        // add all numbers from 13 to 10 (inclusive)
        for (int i = 13; i >= 10; i--) {
            intList.add(i);
        }

        //          4
        //         / \
        //       2     12
        //      / \   / \
        //     1   3 10  13
        //           / \
        //          5  11

        // clear sorted list to prepare for next comparison
        sortedList.clear();
        root = intList.getRoot();
        inOrder(root);
        Object[] newCorrectOrder = {1,2,3,4,5,10,11,12,13};
        studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST ADD 13-10 =");
        printTree(intList);

        assertEquals("Tree not properly re-balanced", -1, root.getBalanceFactor());
        assertArrayEquals("Nodes are not in the correct order", newCorrectOrder, studentOrder);
        assertEquals("Size not correctly increased", 9, intList.size());

        // test handling of duplicates
        intList.add(10);
        sortedList.clear();
        root = intList.getRoot();
        inOrder(root);
        studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST ADD DUPLICATE =");
        printTree(intList);

        assertEquals("Tree shouldn't change from duplicate", -1, root.getBalanceFactor());
        assertArrayEquals("Tree shouldn't change when duplicate is added", newCorrectOrder, studentOrder);
        assertEquals("Size shouldn't update if duplicate is added", 9, intList.size());
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        // start off by adding all numbers 1 to 10 inclusive
        /*
            ********THIS USES ADD() SO DEBUG THAT METHOD BEFORE TRYING TO DEBUG REMOVE********
         */
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }

        //          4
        //         /  \
        //       2      8
        //      / \    / \
        //     1   3  6   9
        //           / \   \
        //          5   7   10

        // start off easy, let's just make sure the right value is returned and size is adjusted
        for (int i = 10; i > 0; i--) {
            assertEquals("Remove returns incorrect value", i, (int)intList.remove(i));
            assertEquals("Size not correctly updated", i-1, intList.size());
        }

        // now let's make sure the list is structured correctly after each remove
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }

        //          4
        //         /  \
        //       2      8
        //      / \    / \
        //     1   3  6   9
        //           / \   \
        //          5   7   10

        // remove an inner node
        intList.remove(8);

        //          4
        //         /  \
        //       2      9
        //      / \    / \
        //     1   3  6   10
        //           / \
        //          5   7

        sortedList.clear();
        AVLNode<Integer> root = intList.getRoot();
        inOrder(root);
        Object[] correctOrder = {1,2,3,4,5,6,7,9,10};
        Object[] studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST REMOVE 8 =");
        printTree(intList);

        assertEquals("Tree not properly re-balanced", -1, root.getBalanceFactor());
        assertArrayEquals("Tree not restructured properly after remove (make sure successor is used)",
                correctOrder, studentOrder);

        // now let's remove the node
        intList.remove(4);

        //          5
        //         /  \
        //       2      9
        //      / \    / \
        //     1   3  6   10
        //             \
        //              7

        sortedList.clear();
        root = intList.getRoot();
        inOrder(root);
        Object[] newCorrectOrder = {1,2,3,5,6,7,9,10};
        studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST REMOVE 4 =");
        printTree(intList);

        assertEquals("Tree not properly re-balanced", -1, root.getBalanceFactor());
        assertArrayEquals("Tree not restructured properly after remove (make sure successor is used)",
                newCorrectOrder, studentOrder);

        // let's do a double shift on the remove (because that's a lot of fun)
        intList.remove(10);

        //          5
        //         /  \
        //       2      7
        //      / \    / \
        //     1   3  6   9

        sortedList.clear();
        root = intList.getRoot();
        inOrder(root);
        Object[] finalCorrectOrder = {1,2,3,5,6,7,9};
        studentOrder = sortedList.toArray();
        // print your tree
        System.out.println("= POST REMOVE 10 =");
        printTree(intList);

        assertEquals("Tree not properly re-balanced", 0, root.getBalanceFactor());
        assertArrayEquals("Tree not restructured properly after remove (make sure successor is used)",
                finalCorrectOrder, studentOrder);
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testListConstructor() {
        List<Integer> constructorList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            constructorList.add(i);
        }
        AVL<Integer> madeFromConstructor = new AVL<>(constructorList);

        //          4
        //         /  \
        //       2      8
        //      / \    / \
        //     1   3  6   9
        //           / \   \
        //          5   7   10

        AVLNode<Integer> root = madeFromConstructor.getRoot();
        // this method just creates an inorder list
        inOrder(root);
        Object[] correctOrder = {1,2,3,4,5,6,7,8,9,10};
        Object[] studentOrder = sortedList.toArray();
        assertArrayEquals("Nodes are not in the correct order", correctOrder, studentOrder);
        assertEquals("Size not correctly increased", 10, madeFromConstructor.size());
        // print your tree
        System.out.println("= BUILT FROM CONSTRUCTOR =");
        printTree(madeFromConstructor);
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }

        for (int i = 1; i <= 10; i++) {
            assertEquals("Get returns wrong value", i, (int)intList.get(i));
            assertEquals("Size shouldn't change from get()", 10, intList.size());
        }
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }

        for (int i = 1; i <= 10; i++) {
            assertTrue("Contains returns wrong answer", intList.contains(i));
            assertEquals("Size shouldn't change from get()", 10, intList.size());
        }
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }
        intList.clear();
        assertEquals("Size should be reset to 0", 0, intList.size());
        assertEquals("Tree should be empty", null, intList.getRoot());
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals("Height of null tree is -1", -1, intList.height());
        for (int i = 1; i <= 10; i++) {
            intList.add(i);
        }
        assertEquals("Height is incorrect", 3, intList.height());
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testDeepestBranches() {
        // add 1-14 inclusive
        for (int i = 1; i <= 14; i++) {
            intList.add(i);
        }

        //             8
        //         /       \
        //       4          12
        //      / \         / \
        //    2     6     10   13
        //   / \   / \    / \   \
        //  1   3 5   7  9   11  14

        Object[] answer = {8,4,2,1,3,6,5,7,12,10,9,11,13,14};
        Object[] student = intList.deepestBranches().toArray();
        assertArrayEquals("Method returns incorrect order", answer, student);

        // make some small modifications
        intList.remove(5);
        intList.remove(9);
        intList.remove(11);

        //             8
        //         /       \
        //       4          12
        //     /   \       /  \
        //    2     6     10   13
        //   / \     \          \
        //  1   3     7          14

        Object[] finalAnswer = {8,4,2,1,3,6,7,12,13,14};
        student = intList.deepestBranches().toArray();
        assertArrayEquals("Method returns incorrect order", finalAnswer, student);
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT)
    public void testSortBetween() {
        // add 1-14 inclusive
        for (int i = 1; i <= 14; i++) {
            intList.add(i);
        }

        //             8
        //         /       \
        //       4          12
        //      / \         / \
        //    2     6     10   13
        //   / \   / \    / \   \
        //  1   3 5   7  9   11  14

        Object[] answer = {6,7,8,9,10,11,12};
        Object[] student = intList.sortedInBetween(5,13).toArray();
        assertArrayEquals("Method returns incorrect order", answer, student);

        // Idk what else to test with this, it's kinda an all or nothing deal
        numTestsPassed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestConstructor() {
        AVL<String> errorList = new AVL<>(null);
        numExceptionsFailed++;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestAddNull() {
        intList.add(null);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestRemoveNull() {
        intList.remove(null);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void exceptionTestRemoveNonexistent() {
        intList.remove(10);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestGetNull() {
        intList.get(null);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void exceptionTestGetNonexistent() {
        intList.get(10);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestContainsNull() {
        intList.contains(null);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestSortedNull1() {
        intList.sortedInBetween(null, 4);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestSortedNull2() {
        intList.sortedInBetween(4, null);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestSortedWrongOrder() {
        intList.sortedInBetween(5, 4);
        numExceptionsFailed++;
        System.out.println("oof");
    }

    private void inOrder(AVLNode<Integer> curr) {
        if (curr == null) {
            return;
        } else {
            inOrder(curr.getLeft());
            sortedList.add(curr.getData());
            inOrder(curr.getRight());
        }
    }

    /*
        THIS SECTION IS JUST FOR THE PRINTING TREE METHOD
     */
    public static <T extends Comparable<? super T>> void printTree(AVL<T>  tree) {
        AVLNode<T> root = tree.getRoot();
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<? super T>> void printNodeInternal(List<AVLNode<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<AVLNode<T>> newNodes = new ArrayList<AVLNode<T>>();
        for (AVLNode<T> node : nodes) {
            if (node != null) {
                System.out.print(node.getData());
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null) {
                    System.out.print("/");
                } else {
                    printWhitespaces(1);
                }
                printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<? super T>> int maxLevel(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }

        return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null) {
                return false;
            }
        }

        return true;
    }

    @AfterClass
    public static void finalResults() {
        System.out.println("=====================================");
        if (numTestsPassed == 9 && numExceptionsFailed == 0) {
            System.out.println("CONGRATS! YOU PASSED ALL THE TESTS!");
            try {
                Desktop.getDesktop().browse(new URL(" https://ape-unit.github.io/MinigameMashup/").toURI());
            } catch (Exception e) {}
        } else if (numTestsPassed == 9) {
            System.out.println("You passed all the tests but failed " + numExceptionsFailed + " exceptions");
        } else if (numExceptionsFailed == 0){
            System.out.println("You passed all the exceptions but failed " + (9 - numTestsPassed) + " tests");
        } else {
            System.out.println("You passed failed " + numExceptionsFailed + " and " + (9 - numTestsPassed) + " tests");
        }
        System.out.println("=====================================");
    }
}
