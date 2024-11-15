package cis3089.mybinarysearchtree;

public class Node<E>
{
    public E data;
    public Node left = null;
    public Node right = null;

    public Node(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    public Node(E data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        return "Node(" + data.toString() + ")";
    }
}
