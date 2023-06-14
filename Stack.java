import java.util.EmptyStackException;

public class Stack<T extends Comparable<T>> {
    private boolean useArray;
    private Object stack;

    public Stack(boolean useArray) {
        this.useArray = useArray;
        if (useArray) {
            stack = new ArrayStack<>();
        } else {
            stack = new LinkedStack<>();
        }
    }

    public void push(T element) {
        if (useArray) {
            ((ArrayStack<T>) stack).push(element);
        } else {
            ((LinkedStack<T>) stack).push(element);
        }
    }

    public T pop() {
        if (useArray) {
            return ((ArrayStack<T>) stack).pop();
        } else {
            return ((LinkedStack<T>) stack).pop();
        }
    }

    public T peek() {
        if (useArray) {
            return ((ArrayStack<T>) stack).peek();
        } else {
            return ((LinkedStack<T>) stack).peek();
        }
    }

    public boolean isEmpty() {
        if (useArray) {
            return ((ArrayStack<T>) stack).isEmpty();
        } else {
            return ((LinkedStack<T>) stack).isEmpty();
        }
    }

    public int size() {
        if (useArray) {
            return ((ArrayStack<T>) stack).size();
        } else {
            return ((LinkedStack<T>) stack).size();
        }
    }

    private class ArrayStack<T extends Comparable<T>> {
        private static final int DEFAULT_CAPACITY = 10;
        private T[] array;
        private int top;

        @SuppressWarnings("unchecked")
        public ArrayStack() {
            array = (T[]) new Comparable[DEFAULT_CAPACITY];
            top = -1;
        }

        public void push(T element) {
            if (top == array.length - 1) {
                expandArray();
            }
            top++;
            array[top] = element;
        }

        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            T element = array[top];
            array[top] = null;
            top--;
            return element;
        }

        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return array[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public int size() {
            return top + 1;
        }

        @SuppressWarnings("unchecked")
        private void expandArray() {
            T[] newArray = (T[]) new Comparable[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    private class LinkedStack<T extends Comparable<T>> {
        private Node top;
        private int size;

        public LinkedStack() {
            top = null;
            size = 0;
        }

        private class Node {
            T data;
            Node next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        public void push(T element) {
            Node newNode = new Node(element);
            newNode.next = top;
            top = newNode;
            size++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            T element = top.data;
            top = top.next;
            size--;
            return element;
        }

        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public int size() {
            return size;
        }
    }

    /**
                                    Implementation

     Stack<Integer> arrayStack = new Stack<>(true); // Create a stack based on an array
     arrayStack.push(1);
     arrayStack.push(2);
     System.out.println(arrayStack.pop()); // Output: 2

     Stack<String> linkedStack = new Stack<>(false); // Create a stack based on a linked list
     linkedStack.push("Hello");
     linkedStack.push("World");
     System.out.println(linkedStack.pop()); // Output: World

     */
}
