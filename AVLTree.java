public class AVLTree<T extends Comparable<T>> {

    private Node root;

    private class Node {
        T data;
        Node left;
        Node right;
        int height;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    public void insert(T element) {
        root = insert(root, element);
    }

    private Node insert(Node node, T element) {
        if (node == null) {
            return new Node(element);
        }

        if (element.compareTo(node.data) < 0) {
            node.left = insert(node.left, element);
        } else if (element.compareTo(node.data) > 0) {
            node.right = insert(node.right, element);
        } else {
            return node; // Duplicate elements are not allowed
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && element.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }

        if (balance < -1 && element.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }

        if (balance > 1 && element.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && element.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void sort() {
        sort(root);
    }

    private void sort(Node node) {
        if (node != null) {
            sort(node.left);
            System.out.print(node.data + " ");
            sort(node.right);
        }
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        Node rightChildOfLeftChild = leftChild.right;

        leftChild.right = node;
        node.left = rightChildOfLeftChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        leftChild.height = 1 + Math.max(getHeight(leftChild.left), getHeight(leftChild.right));

        return leftChild;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        Node leftChildOfRightChild = rightChild.left;

        rightChild.left = node;
        node.right = leftChildOfRightChild;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        rightChild.height = 1 + Math.max(getHeight(rightChild.left), getHeight(rightChild.right));

        return rightChild;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
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
}
