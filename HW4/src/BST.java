import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
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
    private BSTNode<T> addH(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addH(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addH(data, curr.getRight()));
        } else if (data.equals(curr.getData())) {
            return curr;
        }
        return curr;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }

        BSTNode<T> dummyNode = new BSTNode<T>(null);

        root = removeH(root, data, dummyNode);

        if (dummyNode == null) {
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
    private BSTNode<T> removeH(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            return null;
        }
        if (data.equals(curr.getData())) {
            dummy.setData(data);

            if (curr.getLeft() != null && curr.getRight() != null) {
                BSTNode<T> temp = new BSTNode<T>(null);
                curr.setLeft(predecessor(curr.getLeft(), temp));
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
        return curr;

    }

    /**
     * Helper method for removeH (pointer reinforcement). Finds the predecessor
     * of a node, keeps track of its data and removes it.
     *
     * @param curr the current node
     * @param temp keep track of the predecessor
     * @return node
     */
    private BSTNode<T> predecessor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getRight() == null) {
            temp.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(predecessor(curr.getRight(), temp));
            return curr;
        }
    }


    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
        T result = getH(root,data);
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
    private T getH(BSTNode<T> curr, T data) {
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
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
    private boolean containsH(BSTNode<T> curr, T data) {
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
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderH(root, list);
        return list;
    }

    /**
     * recursively gets preorder order for tree
     * @param curr current node
     * @param list add to list the elements in specified order
     */
    private void preorderH(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        list.add(curr.getData());
        preorderH(curr.getLeft(), list);
        preorderH(curr.getRight(), list);
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inorderH(root, list);
        return list;
    }

    /**
     * recursively gets inorder order for tree
     * @param curr current node
     * @param list add to list the elements in specified order
     */
    private void inorderH(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        inorderH(curr.getLeft(), list);
        list.add(curr.getData());
        inorderH(curr.getRight(), list);
    }


    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postorderH(root, list);
        return list;
    }

    /**
     * recursively gets postorder order for tree
     * @param curr current node
     * @param list add to list the elements in specified order
     */
    private void postorderH(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        postorderH(curr.getLeft(), list);
        postorderH(curr.getRight(), list);
        list.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<T>();
        Queue<BSTNode<T>> levelOrder = new LinkedList<BSTNode<T>>();
        if (root != null) {
            levelOrder.add(root);
        }
        BSTNode<T> temp;
        while (!levelOrder.isEmpty()) {
            temp = levelOrder.remove();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                levelOrder.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                levelOrder.add(temp.getRight());
            }
        }
        return list;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        return isBSTH(treeRoot, null, null);
    }

    /**
     * helper method for determining if it is a valid BST
     *
     * @param <T> generic type
     * @param curr current node
     * @param low low bounds
     * @param high high bounds
     * @return boolean
     */
    private static <T extends Comparable<? super T>> boolean isBSTH(
            BSTNode<T> curr, T low, T high) {
        if (curr == null) {
            return true;
        }
        return (low == null || curr.getData().compareTo(low) > 0)
                && (high == null || curr.getData().compareTo(high) < 0)
                && isBSTH(curr.getLeft(), low, curr.getData())
                && isBSTH(curr.getRight(), curr.getData(), high);
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightH(root);
    }

    /**
     * Helper method to find height
     *
     * @param curr current node
     * @return int as height
     */
    private int heightH(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return 1 + Math.max(heightH(curr.getLeft()),
                    heightH(curr.getRight()));
        }
    }

    /**
     * Returns the size of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
