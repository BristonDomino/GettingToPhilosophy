package cis3089.mybinarysearchtree;

import cis3089.mybinarysearchtree.Node;

public class MyBinarySearchTree<E> {
    public int size;
    public Node root;
    
    public MyBinarySearchTree()
    {
        size = 0;
        root = null;
    }

    /**
     * Adds a value to the tree if it doesn't exist yet.
     * 
     * @param data Value to add.
     * @return  Returns true if the value was successfully added, false if it already
     *          exists in the tree.
     */
    public boolean add(E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        
        if (root == null) {
            root = new Node(data);
            size++;
            return true;
        }
        
        return addHelper(root, data);
    }

    private boolean addHelper(Node node, E data)
    {
        // TODO: FILL THIS IN!
        Comparable<E> comparableData = (Comparable<E>) data;

        // Traverse left if data is less than the current node's data
        if (comparableData.compareTo((E) node.data) < 0) 
        {
            if (node.left == null)
            {
                node.left = new Node<>(data);
                size++;
                return true;
            }
            else 
            {
                return addHelper(node.left, data);
            }
        }
        // Traverse right if data is greater than the current node's data
        else if (comparableData.compareTo((E) node.data) > 0)
        {
            if (node.right == null)
            {
                node.right = new Node<>(data);
                size++;
                return true;
            } 
            else 
            {
                return addHelper(node.right, data);
            }
        }
        // Data already exists in the tree
        return false;
    }

    /**
     * Removes all elements from the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Determines if a value exists in the tree.
     * 
     * @param data The value to look for.
     * @return Boolean value representing whether or not the value was found.
     */
    public boolean containsValue(E data) {
        return findNode(root, data) != null;
    }

    /**
     * Returns the entry that contains the target value, or null if there is none.
     *
     * @param target The value to look for.
     */
    private Node findNode(Node node, E data)
    {
        if (node == null) {
            return null;
        }

        Comparable<E> comparableData = (Comparable<E>) data;

        if (comparableData.compareTo((E) node.data) < 0) {
            return findNode(node.left, data);
        } else if (comparableData.compareTo((E) node.data) > 0) {
            return findNode(node.right, data);
        } else {
            return node; // Data found
        }
    }

    /**
     * Returns the object stored with this target value.
     * 
     * @param data The value to look for.
     * @return The object associated with this target value, or null if it is not found.
     */
    public E get(E data) {
        Node<E> node = findNode(root, data);
        
        if (node == null) {
            return null;
        }
        
        return node.data;
    }

    /**
     * Returns the height of the tree.  The height is the number of edges (pointers)
     * along the longest path from the root to a leaf node.
     *
     * @return The height of the tree.
     */
    public int getHeight() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        // TODO: fill this in.
        return 0;
    }

    /**
     * Returns a Boolean value representing whether or not this tree is empty.
     * 
     * @return True if there are no nodes in the tree, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of nodes in the tree.
     * 
     * @return The number of nodes in the tree.
     */
    public int size() {
        return size;
    }
    
    public static void main(String[] args) {

    }
}
