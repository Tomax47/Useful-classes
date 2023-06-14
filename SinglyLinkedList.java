import java.util.Iterator;

public class SinglyLinkedList<T extends Comparable<T>> implements Comparable<SinglyLinkedList<T>>, Iterable<T> {

    private Node head;
    private int size;

    public SinglyLinkedList() {
        head = null;
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

    public void createList(T[] array) {
        for (T item : array) {
            add(item);
        }
    }

    public int indexOf(T element) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1; // Element not found
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public void add(T element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remove(T element) {
        if (head == null) {
            return;
        }
        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        Node prev = null;
        while (current != null && !current.data.equals(element)) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            return; // Element not found
        }
        prev.next = current.next;
        size--;
    }

    public void insert(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node newNode = new Node(element);
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = element;
    }

    public T getMinimum() {
        if (head == null) {
            return null;
        }
        T minimum = head.data;
        Node current = head.next;
        while (current != null) {
            if (current.data.compareTo(minimum) < 0) {
                minimum = current.data;
            }
            current = current.next;
        }
        return minimum;
    }

    public T getMaximum() {
        if (head == null) {
            return null;
        }
        T maximum = head.data;
        Node current = head.next;
        while (current != null) {
            if (current.data.compareTo(maximum) > 0) {
                maximum = current.data;
            }
            current = current.next;
        }
        return maximum;
    }

    @Override
    public Iterator<T> iterator() {
        return new SinglyLinkedListIterator();
    }

    private class SinglyLinkedListIterator implements Iterator<T> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    public void traverse() {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public void sort() {
        if (head == null || head.next == null) {
            return; // Already sorted or empty list
        }

        Node current = head;
        while (current != null) {
            Node minNode = current;
            Node temp = current.next;
            while (temp != null) {
                if (temp.data.compareTo(minNode.data) < 0) {
                    minNode = temp;
                }
                temp = temp.next;
            }
            T tempData = current.data;
            current.data = minNode.data;
            minNode.data = tempData;
            current = current.next;
        }
    }

    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int compareTo(SinglyLinkedList<T> other) {
        Iterator<T> iterator1 = iterator();
        Iterator<T> iterator2 = other.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            T element1 = iterator1.next();
            T element2 = iterator2.next();
            int comparison = element1.compareTo(element2);
            if (comparison != 0) {
                return comparison;
            }
        }

        if (iterator1.hasNext()) {
            return 1; // Other list is shorter
        } else if (iterator2.hasNext()) {
            return -1; // Other list is longer
        } else {
            return 0; // Lists are equal
        }
    }
}
