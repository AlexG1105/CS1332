import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * A collection of test cases for the binary search tree assignment
 * @author Joseph Azevedo
 */
public class BSTStudentTests2 {
    private BST<Integer> intBST;
    private BST<String> strBST;
    public static final int TIMEOUT = 20000;
    public static final int CALLS = 1000;

    @Before
    public void setup() {
        intBST = new BST<>();
        strBST = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyInitialize() {
        // empty initialization
        assertNull(intBST.getRoot());
        assertEquals(intBST.size(), 0);
    }

    /**
     * Uses BST.contains
     */
    @SuppressWarnings("ConstantConditions")
    @Test(timeout = TIMEOUT)
    public void testDataInitialize() {
        // null collection given
        assertThrowableThrown(() -> new BST<String>(null),
                IllegalArgumentException.class);

        // null containing collection
        List<String> nullContaining = generateList(Integer::toString, 10);
        nullContaining.set(0, null);
        Collections.shuffle(nullContaining);
        assertThrowableThrown(() -> new BST<>(nullContaining),
                IllegalArgumentException.class);

        // initial size & nonnull root
        List<Integer> l1 = generateList(10);
        Collections.shuffle(l1);
        intBST = new BST<>(l1);
        assertEquals(intBST.size(), l1.size());
        assertNotNull(intBST.getRoot());

        // overall collection
        List<String> l2 = generateList(a -> a + "0", 10);
        Collections.shuffle(l2);
        strBST = new BST<>(l2);
        for (String element : l2) {
            assertTrue(strBST.contains(element));
        }
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    @Test(timeout = TIMEOUT)
    public void testAddBehavior() {
        // null element
        assertThrowableThrown(() -> intBST.add(null),
                IllegalArgumentException.class);

        // empty bst setting root
        String a = new String("test");
        strBST.add(a);
        assertNotNull(strBST.getRoot());
        assertEquals(strBST.size(), 1);
        assertSame(strBST.getRoot().getData(), a);

        // root unchanged with nonempty bst
        BSTNode<String> oldRoot = strBST.getRoot();
        strBST.add("test2");
        assertSame(strBST.getRoot(), oldRoot);

        // duplicate add
        strBST = new BST<>();
        strBST.add("a");
        assertEquals(strBST.size(), 1);
        strBST.add("a");
        assertEquals(strBST.size(), 1);
        assertEquals(strBST.preorder().size(), 1);
    }

    /**
     * Uses BST.inorder and BST.isBST
     */
    @Test(timeout = TIMEOUT)
    public void testAddCalls() {
        List<Integer> list = generateList(CALLS);
        Collections.shuffle(list);
        List<Integer> mirror = new ArrayList<>(CALLS);
        for (int i = 0; i < CALLS; ++i) {
            int oldSize = intBST.size();
            intBST.add(list.get(i));

            // assert size incremented
            assertEquals(intBST.size(), oldSize + 1);

            // test relative ordering of nodes
            assertTrue(BST.isBST(intBST.getRoot()));

            // test buildup
            mirror.add(list.get(i));
            assertCollectionsEqual(intBST.inorder(), mirror, false);
        }
        // test final sorted order
        List<Integer> sorted = generateList(CALLS);
        assertArrayEquals(intBST.inorder().toArray(), sorted.toArray());
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    @Test(timeout = TIMEOUT)
    public void testRemoveBehavior() {
        // test exceptions
        assertThrowableThrown(() -> intBST.remove(0),
                NoSuchElementException.class);
        int intItem = 1;
        intBST.add(intItem);
        assertThrowableThrown(() -> intBST.remove(null),
                IllegalArgumentException.class);
        assertThrowableThrown(() -> intBST.remove(0),
                NoSuchElementException.class);

        // test identity equality
        String item = new String("abc");
        strBST.add(item);
        String removed = strBST.remove("abc");
        assertSame(item, removed);

        // test size decrement
        int oldSize = intBST.size();
        intBST.remove(intItem);
        assertEquals(intBST.size(), oldSize - 1);
    }

    @Test(timeout = TIMEOUT)
    public void testIsBSTEdgeCase() {
        final Random rand = new Random();
        for (int i = 0; i < CALLS; ++i) {
            intBST = makePseudoBalancedTree(0, (int)(CALLS * 0.25) + (int)(i * 0.75), 0.5d, rand);
            assertTrue(BST.isBST(intBST.getRoot()));
            List<Integer> levelorder = intBST.levelorder();
            // find the first deep node with two leaf children (from a random order)
            Optional<NodeInfo<Integer>> toInvalidate = levelorder.stream()
                    .collect(shuffleStream())
                    .filter(j -> {
                        NodeInfo<Integer> node = nodeInfo(intBST.getRoot(), j);
                        return children(node.getNode()) == 2
                            && children(node.getNode().getLeft()) == 0
                            && children(node.getNode().getRight()) == 0
                            && getDepth(intBST.getRoot(), j) > 2;
                    }).map(j -> nodeInfo(intBST.getRoot(), j))
                    .findFirst();
            if (toInvalidate.isPresent()) {
                BSTNode<Integer> node = toInvalidate.get().getNode();
                int depth = getDepth(intBST.getRoot(), node.getData());
                // select a random node that is at least one depth above this
                // one and is on the opposite side of this one (relative to its
                // parent)
                Optional<NodeInfo<Integer>> target = levelorder.stream()
                        .filter(j -> getDepth(intBST.getRoot(), j) < depth)
                        .map(j -> nodeInfo(intBST.getRoot(), j))
                        .filter(info -> isIn(node.getData(), info.getNode()))
                        .filter(info -> info.isLeft() ^ isOnLeftOf(node, info.getNode()))
                        .collect(shuffleStream())
                        .findFirst();
                if (target.isPresent()) {
                    NodeInfo<Integer> targetInfo = target.get();
                    boolean isLeft = isOnLeftOf(node, targetInfo.getNode());
                    if (isLeft) {
                        // invalidate the right child of the original node
                        int newData = targetInfo.getNode().getData() + 1;
                        if (intBST.contains(newData)) intBST.remove(newData);
                        node.getRight().setData(newData);
                    } else {
                        // invalidate the left child of the original node
                        int newData = targetInfo.getNode().getData() - 1;
                        if (intBST.contains(newData)) intBST.remove(newData);
                        node.getLeft().setData(newData);
                    }
                    assertFalse(BST.isBST(intBST.getRoot()));
                }
            }
        }
    }

    /**
     * Uses BST.inorder and BST.isBST
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveCalls() {
        // add randomly ordered numbers to the tree
        List<String> list = generateList(Integer::toString, CALLS);
        Collections.shuffle(list);
        strBST = new BST<>(list);
        // remove in a different order as added, and create non-interned
        // duplicate strings
        List<String> list2 = generateList(Integer::toString, CALLS);
        Collections.shuffle(list2);
        List<String> mirror = generateList(Integer::toString, CALLS);
        for (int i = 0; i < CALLS; ++i) {
            int oldSize = strBST.size();
            NodeInfo<String> toRemove = nodeInfo(strBST.getRoot(), list2.get(i));
            // make sure the node to remove is in the tree
            assertNotNull(toRemove.getNode());
            int children = children(toRemove.getNode());
            String removed;
            BSTNode<String> parent = toRemove.getParent();

            // case of remove by predecessor
            if (children == 2 && parent != null) {
                // Ensure remove by predecessor
                List<String> inorder = strBST.inorder();
                int index = inorder.indexOf(list2.get(i));
                if (index != 0) {
                    String pre = inorder.get(index - 1);
                    removed = strBST.remove(list2.get(i));
                    // Ensure predecessor now lies in the old position
                    assertEquals(pre, toRemove.isLeft() ? parent.getLeft().getData() : parent.getRight().getData());
                } else {
                    removed = strBST.remove(list2.get(i));
                }
            } else {
                removed = strBST.remove(list2.get(i));
            }

            // size decrement
            assertEquals(strBST.size(), oldSize - 1);

            // identity equality of element from original list
            assertSame(removed, list.get(list.indexOf(list2.get(i))));

            // test relative ordering of nodes
            if (strBST.size() != 0) assertTrue(BST.isBST(strBST.getRoot()));

            // test buildup
            mirror.remove(list2.get(i));
            assertCollectionsEqual(strBST.inorder(), mirror, false);
        }
        // should be empty now
        assertEquals(strBST.size(), 0);
        assertNull(strBST.getRoot());
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    @Test(timeout = TIMEOUT)
    public void testGetBehavior() {
        // test exceptions
        assertThrowableThrown(() -> intBST.get(0),
                NoSuchElementException.class);
        int intItem = 1;
        intBST.add(intItem);
        assertThrowableThrown(() -> intBST.get(null),
                IllegalArgumentException.class);
        assertThrowableThrown(() -> intBST.get(0),
                NoSuchElementException.class);

        // test identity equality
        String item = new String("abc");
        strBST.add(item);
        String found = strBST.get("abc");
        assertSame(item, found);

        // test size is the same
        int oldSize = intBST.size();
        intBST.get(intItem);
        assertEquals(intBST.size(), oldSize);
    }

    @Test(timeout = TIMEOUT)
    public void testContainsBehavior() {
        // test exceptions
        assertThrowableThrown(() -> intBST.contains(null),
                IllegalArgumentException.class);
    }

    @SuppressWarnings("BoxingBoxedValue")
    @Test(timeout = TIMEOUT)
    public void testGetContainsCalls() {
        // add randomly ordered numbers to the tree
        List<Integer> list = generateList(Integer::new, CALLS);
        Collections.shuffle(list);
        intBST = new BST<>(list);
        for (int i = 0; i < CALLS; ++i) {
            // assert contains
            assertTrue(intBST.contains(list.get(i)));
            assertFalse(intBST.contains(list.get(i) + CALLS));

            // assert get & exception
            assertEquals(intBST.get(list.get(i)), list.get(i));
            final int ind = i;
            assertThrowableThrown(() -> intBST.get(list.get(ind) + CALLS),
                    NoSuchElementException.class);

            // assert identity equality
            int sameVal = new Integer(list.get(i));
            assertSame(intBST.get(sameVal), list.get(i));
            assertNotSame(intBST.get(sameVal), sameVal);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testPostPreorder() {
        for (int i = 1; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);

            List<Integer> preorder = intBST.preorder();
            List<Integer> postorder = intBST.postorder();

            // make sure the collections accurately represent dataset
            assertCollectionsEqual(preorder, list, false);
            assertCollectionsEqual(postorder, list, false);

            // make sure the tree roots are at the beginning or end of the lists
            // (depending on pre or post order)
            assertEquals(preorder.get(0), intBST.getRoot().getData());
            assertEquals(postorder.get(postorder.size() - 1), intBST.getRoot().getData());

            // make sure the bottommost element on either the left or right is
            // placed first or last (depending on pre or post order)
            assertEquals(preorder.get(preorder.size() - 1), getBottom(intBST.getRoot(), false));
            assertEquals(postorder.get(0), getBottom(intBST.getRoot(), true));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testPreorderTreeCreation() {
        for (int i = 0; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);

            // Create a new BST with the preorder of this one as its template
            // and make sure they have the same shape (a required property of
            // the preorder traversal)
            List<Integer> preorder = intBST.preorder();
            BST<Integer> dupBST = new BST<>(preorder);
            assertTreesEqual(intBST, dupBST);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInorderInt() {
        for (int i = 0; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            List<Integer> sorted = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);
            List<Integer> inorder = intBST.inorder();

            // ensure same contents
            assertCollectionsEqual(inorder, list, false);

            // ensure same order
            assertArrayEquals(inorder.toArray(), sorted.toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInorderStr() {
        int length = (int)(Math.ceil(Math.log10(CALLS)));
        String format = "%0" + length + "d";
        for (int i = 0; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<String> list = generateList(j -> String.format(format, j), i);
            List<String> sorted = generateList(j -> String.format(format, j), i);
            Collections.shuffle(list);
            strBST = new BST<>(list);
            List<String> inorder = strBST.inorder();

            // ensure same contents
            assertCollectionsEqual(inorder, list, false);

            // ensure same order
            assertArrayEquals(inorder.toArray(), sorted.toArray());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder2() {
        for (int i = 1; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);
            List<Integer> levelorder = intBST.levelorder();

            // ensure same contents
            assertCollectionsEqual(levelorder, list, false);

            // ensure ordered by level
            List<Integer> depthOrder = removeConsecutiveElements(levelorder.stream()
                    .map(a -> getDepth(intBST.getRoot(), a))
                    .collect(Collectors.toList()));
            List<Integer> orderModel = generateList(Collections.max(depthOrder) + 1);
            assertListsEqual(depthOrder, orderModel, true);
        }
    }

    /**
     * uses BST.add (circular dependency with other test)
     */
    @Test(timeout = TIMEOUT)
    public void testIsBST2() {
        for (int i = 0; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);

            assertTrue(BST.isBST(intBST.getRoot()));
            if (intBST.getRoot() != null) {
                intBST.getRoot().setLeft(new BSTNode<>(intBST.getRoot().getData() + 10));
                assertFalse(BST.isBST(intBST.getRoot()));
            }
        }
    }

    /**
     * uses BST.levelorder
     */
    @Test(timeout = TIMEOUT)
    public void testHeight2() {
        // height of -1 for empty
        assertEquals(intBST.height(), -1);

        for (int i = 1; i < CALLS; ++i) {
            // add randomly ordered numbers to the tree
            List<Integer> list = generateList(i);
            Collections.shuffle(list);
            intBST = new BST<>(list);

            // get max depth (= to height)
            int maxDepth = Collections.max(intBST.levelorder().stream()
                    .map(a -> getDepth(intBST.getRoot(), a))
                    .collect(Collectors.toList()));
            assertEquals(intBST.height(), maxDepth);
        }
    }


    @Test(timeout = TIMEOUT)
    public void testClear() {
        // make sure root is set to null and size is set to 0 upon clear
        intBST.add(0);
        assertEquals(intBST.size(), 1);
        assertNotNull(intBST.getRoot());
        intBST.clear();
        assertEquals(intBST.size(), 0);
        assertNull(intBST.getRoot());
    }

    // ---------------
    // utility methods
    // ---------------

    public static <K extends Comparable<? super K>> int getDepth(BSTNode<K> root, K value) {
        if (root == null) return -1;
        else if (root.getData().equals(value)) return 0;
        else if (root.getData().compareTo(value) > 0) return 1 + getDepth(root.getLeft(), value);
        else return 1 + getDepth(root.getRight(), value);
    }

    public static <K extends Comparable<? super K>> NodeInfo<K> nodeInfo(BSTNode<K> root, K value) {
        return rNodeInfo(null, root, value, false);
    }

    private static <K extends Comparable<? super K>> NodeInfo<K> rNodeInfo(BSTNode<K> parent, BSTNode<K> current, K value, boolean isLeft) {
        if (current == null) return new NodeInfo<>(null, null, false);
        else if (current.getData().equals(value)) return new NodeInfo<>(current, parent, isLeft);
        else if (current.getData().compareTo(value) > 0) return rNodeInfo(current, current.getLeft(), value, true);
        else return rNodeInfo(current, current.getRight(), value, false);
    }

    public static class NodeInfo<K extends Comparable<? super K>> {
        private BSTNode<K> node;
        private BSTNode<K> parent;
        private boolean isLeft;
        public NodeInfo(BSTNode<K> node, BSTNode<K> parent, boolean isLeft) {
            this.node = node;
            this.parent = parent;
            this.isLeft = isLeft;
        }
        public boolean isLeft() {
            return isLeft;
        }
        public BSTNode<K> getParent() {
            return parent;
        }
        public BSTNode<K> getNode() {
            return node;
        }
    }

    public static BST<Integer> makePseudoBalancedTree(int min, int max, double skew, Random random) {
        final int seed = (int)((max - min) * skew);
        final int seedWidth = 2 + random.nextInt((max - min) / 10);
        final int leftSeedCount = (seed - min) / seedWidth;
        final int rightSeedCount = (max - min) / seedWidth;
        List<Integer> list = generateList(j -> min + (seedWidth * (j + 1)), leftSeedCount - 1);
        list.add(seed);
        Collections.reverse(list);
        list.addAll(generateList(j -> seed + (seedWidth * (j + 1)), rightSeedCount - 1));
        List<Integer> population = generateList(j -> min + j, max - min + 1);
        population.removeAll(list);
        Collections.shuffle(population);
        list.addAll(population);
        return new BST<>(list);
    }

    public static <K extends Comparable<? super K>> K getBottom(BSTNode<K> root, boolean preferLeft) {
        if (preferLeft) return root.getLeft()  == null ? (root.getRight() == null ? root.getData() : getBottom(root.getRight(), true))
                : getBottom(root.getLeft(), true);
        else            return root.getRight() == null ? (root.getLeft() == null  ? root.getData() : getBottom(root.getLeft(), false))
                : getBottom(root.getRight(), false);
    }

    public static <K extends Comparable<? super K>> boolean isOnLeftOf(BSTNode<K> descendent, BSTNode<K> ancestor) {
        return (descendent != ancestor)
                && (ancestor != null)
                && (ancestor.getLeft() != null)
                && isIn(descendent.getData(), ancestor.getLeft());
    }

    public static <K extends Comparable<? super K>> boolean isIn(K data, BSTNode<K> root) {
        return root != null
                && (root.getData().equals(data)
                || (root.getLeft() != null && isIn(data, root.getLeft()))
                || (root.getRight() != null && isIn(data, root.getRight())));
    }


    @SuppressWarnings("ConstantConditions")
    public static <K extends Comparable<? super K>> boolean areSubtreesEqual(BSTNode<K> a, BSTNode<K> b) {
        return (a == null && b == null) || (
                a != null && b != null && a.getData().equals(b.getData())
                        && a.getLeft()  != null ? (b.getLeft()  != null && areSubtreesEqual(a.getLeft(),  b.getLeft()))  : b.getLeft()  == null
                        && a.getRight() != null ? (b.getRight() != null && areSubtreesEqual(a.getRight(), b.getRight())) : b.getRight() == null);
    }

    public static <K extends Comparable<? super K>> int children(BSTNode<K> node) {
        if (node.getLeft() == null && node.getRight() == null) return 0;
        else if (node.getLeft() == null ^ node.getRight() == null) return 1;
        else return 2;
    }

    public static <K> List<K> generateList(IntFunction<K> generator,
                                           int size) {
        List<K> accumulator = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) accumulator.add(generator.apply(i));
        return accumulator;
    }

    public static List<Integer> generateList(int size) {
        List<Integer> accumulator = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) accumulator.add(i);
        return accumulator;
    }

    public static <K> Collector<K, ?, Stream<K>> shuffleStream() {
        return Collectors.collectingAndThen(Collectors.toList(), collected -> {
            Collections.shuffle(collected);
            return collected.stream();
        });
    }

    public static <K> List<K> removeConsecutiveElements(List<K> list) {
        List<K> newList = new ArrayList<>();
        newList.add(list.get(0));
        for(int i = 1; i < list.size(); ++i) {
            if(list.get(i - 1) != list.get(i)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    public static <K> boolean equals(K a, K b) {
        return (a == b) || (a != null && b != null & a.equals(b));
    }

    @FunctionalInterface
    public interface Procedure { void run(); }

    public <T extends Throwable> void assertThrowableThrown(Procedure op,
                                                            Class<T> c) {
        boolean caught = false;
        try {
            op.run();
        } catch (Throwable t) {
            if (t.getClass().equals(c)) caught = true;
        } finally {
            assertTrue(caught);
        }
    }

    public <K> void assertCollectionsEqual(Collection<K> a, Collection<K> b,
                                           boolean ignoreDuplicates) {
        assertTrue((a.size() == b.size() || ignoreDuplicates)
                && (new HashSet<>(a)).equals(new HashSet<>(b)));
    }

    public <K extends Comparable<? super K>> void assertTreesEqual(BST<K> a, BST<K> b) {
        assertTrue(areSubtreesEqual(a.getRoot(), b.getRoot()));
    }

    public <K> void assertListsEqual(List<K> a, List<K> b, boolean useOrder) {
        if (useOrder) {
            if (a.size() == b.size()) {
                boolean equals = true;
                if (a instanceof RandomAccess && b instanceof RandomAccess) {
                    for (int i = 0; i < a.size(); ++i) {
                        equals &= equals(a.get(i), b.get(i));
                    }
                } else {
                    Iterator<K> iterA = a.iterator();
                    Iterator<K> iterB = b.iterator();
                    while (iterA.hasNext()) {
                        equals &= equals(iterA.next(), iterB.next());
                    }
                }
                assertTrue(equals);
            } else fail();
        } else assertCollectionsEqual(a, b, false);
    }
}
