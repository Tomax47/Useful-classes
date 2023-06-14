import java.util.Iterator;

/**                           LinkedList based Dequeue                  */
public class LinkedListDeque<T extends Comparable<T>> implements Comparable<LinkedListDeque<T>>, Iterable<T> {

    private Node head;
    private Node tail;
    private int size;

    public LinkedListDeque() {
        head = null;
        tail = null;
        size = 0;
    }

    private class Node {
        T data;
        Node prev;
        Node next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Deque is empty");
        }
        T data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Deque is empty");
        }
        T data = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Deque is empty");
        }
        return head.data;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Deque is empty");
        }
        return tail.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
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

    @Override
    public int compareTo(LinkedListDeque<T> other) {
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
            return 1; // Other deque is shorter
        } else if (iterator2.hasNext()) {
            return -1; // Other deque is longer
        } else {
            return 0; // Deques are equal
        }
    }
}
