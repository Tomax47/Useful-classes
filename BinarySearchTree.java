import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node root;

    private class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public void insert(T data) {
        root = insertNode(root, data);
    }

    private Node insertNode(Node node, T data) {
        if (node == null) {
            return new Node(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insertNode(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertNode(node.right, data);
        }

        return node;
    }

    public void sort() {
        List<T> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList);
        clear();
        for (T data : sortedList) {
            insert(data);
        }
    }

    private void inOrderTraversal(Node node, List<T> sortedList) {
        if (node != null) {
            inOrderTraversal(node.left, sortedList);
            sortedList.add(node.data);
            inOrderTraversal(node.right, sortedList);
        }
    }

    public void balance() {
        List<T> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList);
        clear();
        root = buildBalancedTree(sortedList, 0, sortedList.size() - 1);
    }

    private Node buildBalancedTree(List<T> sortedList, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        T midData = sortedList.get(mid);
        Node node = new Node(midData);
        node.left = buildBalancedTree(sortedList, start, mid - 1);
        node.right = buildBalancedTree(sortedList, mid + 1, end);
        return node;
    }

    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int level) {
        if (node != null) {
            printTree(node.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(node.data);
            printTree(node.left, level + 1);
        }
    }

    public void clear() {
        root = null;
    }
}
