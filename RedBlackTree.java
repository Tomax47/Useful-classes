public class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        T data;
        Node left;
        Node right;
        Node parent;
        boolean color;

        Node(T data) {
            this.data = data;
            this.color = RED;
        }
    }

    public void insert(T data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
            root.color = BLACK;
        } else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                int comparison = data.compareTo(current.data);
                if (comparison < 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        newNode.parent = parent;
                        break;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        newNode.parent = parent;
                        break;
                    }
                }
            }
            fixInsertion(newNode);
        }
    }

    private void fixInsertion(Node node) {
        while (node.parent != null && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    public void sort() {
        if (root == null) {
            return;
        }
        sortRecursive(root);
    }

    private void sortRecursive(Node node) {
        if (node != null) {
            sortRecursive(node.left);
            System.out.print(node.data + " ");
            sortRecursive(node.right);
        }
    }

    public void balance() {
        if (root == null) {
            return;
        }
        balanceRecursive(root);
    }

    private void balanceRecursive(Node node) {
        if (node != null) {
            balanceRecursive(node.left);
            fixInsertion(node);
            balanceRecursive(node.right);
        }
    }

    public void printTree() {
        printTreeRecursive(root, "", true);
    }

    private void printTreeRecursive(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("└─");
                indent += "  ";
            } else {
                System.out.print("├─");
                indent += "| ";
            }
            System.out.println(node.data);
            printTreeRecursive(node.left, indent, false);
            printTreeRecursive(node.right, indent, true);
        }
    }
}
