import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Alexander Guo
 * @userid aguo36
 * @GTID 903439488
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Helper method for updating the balance factor and heights of nodes
     *
     * @param node current node passed in
     */
    private void updateHBF(AVLNode<T> node) {
        node.setBalanceFactor(easyH(node.getLeft()) - easyH(node.getRight()));
        node.setHeight(1 + Math.max(easyH(node.getLeft()),
                easyH(node.getRight())));
    }

    /**
     * Helper method for calculating the height of a node
     *
     * @param node current passed in node
     * @return the integer representing height of the node
     */
    private int easyH(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Helper method for balancing nodes
     *
     * @param node current node
     * @return root of the new rotated subtree
     *
     */

    private AVLNode<T> balance(AVLNode<T> node) {
        if (Math.abs(node.getBalanceFactor()) > 1) {
            if (node.getBalanceFactor() > 0) {
                if (node.getLeft() != null
                    && node.getLeft().getBalanceFactor() < 0) {
                    node.setLeft(lRotation(node.getLeft()));
                    node = rRotation(node);

                } else {
                    node = rRotation(node);
                }
            } else if (node.getBalanceFactor() < 0) {
                if (node.getRight() != null
                    && node.getRight().getBalanceFactor() > 0) {
                    node.setRight(rRotation(node.getRight()));
                    node = lRotation(node);
                } else {
                    node = lRotation(node);
                }
            }
        }
        return node;
    }

    /**
     * Helper method for right rotation
     *
     * @param node current node passed in
     * @return root of rotation
     */
    private AVLNode<T> rRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        node.setLeft(temp.getRight());
        temp.setRight(node);
        updateHBF(node);
        updateHBF(temp);
        return temp;
    }


    /**
     * Helper method for left rotation
     *
     * @param node current node passed in
     * @return root of rottion
     */
    private AVLNode<T> lRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        node.setRight(temp.getLeft());
        temp.setLeft(node);
        updateHBF(node);
        updateHBF(temp);
        return temp;
    }


    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        root = addH(data, root);

    }

    /**
     * Helper method for add (pointer reinforcement). Is called recursively.
     *
     * @param data the data to be added
     * @param curr the current node
     * @return node
     */
    private AVLNode<T> addH(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addH(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addH(data, curr.getRight()));
        } else if (data.equals(curr.getData())) {
            return curr;
        }

        updateHBF(curr);
        curr = balance(curr);
        return curr;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }

        AVLNode<T> dummyNode = new AVLNode<T>(null);

        root = removeH(root, data, dummyNode);

        if (dummyNode.getData() == null) {
            throw new NoSuchElementException("Error, data is not found!");
        }

        return dummyNode.getData();
    }

    /**
     * Helper method for remove (pointer reinforcement). Is called recursively.
     *
     * @param curr the current node
     * @param data the data to be removed
     * @param dummy passed in dummy node to keep track of data
     * @return node
     */
    private AVLNode<T> removeH(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            return null;
        }
        if (data.equals(curr.getData())) {
            dummy.setData(data);

            if (curr.getLeft() != null && curr.getRight() != null) {
                AVLNode<T> temp = new AVLNode<T>(null);
                curr.setRight(successor(curr.getRight(), temp));
                curr.setData(temp.getData());
                size--;
            } else if (curr.getLeft() != null) {
                size--;
                return curr.getLeft();
            } else if (curr.getRight() != null) {
                size--;
                return curr.getRight();
            } else {
                size--;
                return null;
            }
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeH(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeH(curr.getRight(), data, dummy));
        }

        updateHBF(curr);
        curr = balance(curr);
        return curr;

    }

    /**
     * Helper method for removeH (pointer reinforcement). Finds the successor
     * of a node, keeps track of its data and removes it.
     *
     * @param curr the current node
     * @param temp keep track of the successor
     * @return node
     */
    private AVLNode<T> successor(AVLNode<T> curr, AVLNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), temp));
            updateHBF(curr);
            curr = balance(curr);
            return curr;
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        T result = getH(root, data);
        if (result == null) {
            throw new NoSuchElementException("Error, data not found!");
        }
        return result;
    }

    /**
     * Helper method for get, by implementing recursive calls
     *
     * @param curr current node
     * @param data data to be returned in tree
     * @return the node's data
     */
    private T getH(AVLNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }

        if (data.equals(curr.getData())) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getH(curr.getLeft(), data);
        } else {
            return getH(curr.getRight(), data);
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        return containsH(root, data);
    }

    /**
     * helper method to see if the tree contains data
     *
     * @param curr current node
     * @param data data to be fond
     * @return boolean representing contains
     */
    private boolean containsH(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) < 0) {
            return containsH(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsH(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<T>();
        deepestBranchesH(root, list, 0);
        return list;
    }

    /**
     *
     * @param curr current node for pointer reinforcement
     * @param list the elements we are keeping track of.
     * @param levelFromTop how many from top.
     */
    private void deepestBranchesH(AVLNode<T> curr,
                                  List<T> list, int levelFromTop) {
        if (curr == null) {
            return;
        }
        if (levelFromTop + curr.getHeight() == root.getHeight()) {
            list.add(curr.getData());
        }
        deepestBranchesH(curr.getLeft(), list, levelFromTop + 1);
        deepestBranchesH(curr.getRight(), list, levelFromTop + 1);
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Error, data is null!");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Error, "
                    + "minimum bound must be less than max bound!");
        }
        List<T> list = new ArrayList<T>();
        sortedInBetweenH(root, list, data1, data2);
        return list;
    }

    /**
     *
     * @param curr current node
     * @param list list of nodes
     * @param data1 lower bound
     * @param data2 upper bound
     */
    private void sortedInBetweenH(AVLNode<T> curr, List<T> list,
                                  T data1, T data2) {
        if (curr == null) {
            return;
        }
        if (curr.getData().compareTo(data1) > 0) {
            sortedInBetweenH(curr.getLeft(), list, data1, data2);
        }
        if (curr.getData().compareTo(data1) > 0
                && curr.getData().compareTo(data2) < 0) {
            list.add(curr.getData());
        }
        if (curr.getData().compareTo(data2) < 0) {
            sortedInBetweenH(curr.getRight(), list, data1, data2);
        }
        return;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root == null ? -1 : root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
