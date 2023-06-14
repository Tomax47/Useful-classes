import java.util.Iterator;

public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {

    private Node head;
    private int size;

    public PriorityQueue() {
        head = null;
        size = 0;
    }

    private class Node {
        T data;
        int priority;
        Node next;

        Node(T data, int priority) {
            this.data = data;
            this.priority = priority;
            this.next = null;
        }
    }

    public void enqueue(T element, int priority) {
        Node newNode = new Node(element, priority);
        if (head == null || priority < head.priority) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.priority <= priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        T dequeuedElement = head.data;
        head = head.next;
        size--;
        return dequeuedElement;
    }

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new PriorityQueueIterator();
    }

    private class PriorityQueueIterator implements Iterator<T> {
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
}
