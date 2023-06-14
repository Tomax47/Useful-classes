import java.util.Iterator;

public class ArrayDeque<T extends Comparable<T>> implements Comparable<ArrayDeque<T>>, Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private T[] array;
    private int size;
    private int front;
    private int rear;

    public ArrayDeque() {
        array = (T[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
        front = 0;
        rear = -1;
    }

    public ArrayDeque(int capacity) {
        array = (T[]) new Comparable[capacity];
        size = 0;
        front = 0;
        rear = -1;
    }

    public void createDeque(T[] elements) {
        for (T element : elements) {
            addLast(element);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T element) {
        if (size == array.length) {
            resizeArray();
        }
        if (front == 0) {
            front = array.length - 1;
        } else {
            front--;
        }
        array[front] = element;
        size++;
    }

    public void addLast(T element) {
        if (size == array.length) {
            resizeArray();
        }
        if (rear == array.length - 1) {
            rear = 0;
        } else {
            rear++;
        }
        array[rear] = element;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty.");
        }
        T removedElement = array[front];
        array[front] = null;
        if (front == array.length - 1) {
            front = 0;
        } else {
            front++;
        }
        size--;
        return removedElement;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty.");
        }
        T removedElement = array[rear];
        array[rear] = null;
        if (rear == 0) {
            rear = array.length - 1;
        } else {
            rear--;
        }
        size--;
        return removedElement;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty.");
        }
        return array[front];
    }

    public T getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty.");
        }
        return array[rear];
    }

    private void resizeArray() {
        int newCapacity = array.length * 2;
        T[] newArray = (T[]) new Comparable[newCapacity];
        int j = front;
        for (int i = 0; i < size; i++) {
            newArray[i] = array[j];
            if (j == array.length - 1) {
                j = 0;
            } else {
                j++;
            }
        }
        array = newArray;
        front = 0;
        rear = size - 1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;
        private int count;

        public ArrayDequeIterator() {
            index = front;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            T element = array[index];
            if (index == array.length - 1) {
                index = 0;
            } else {
                index++;
            }
            count++;
            return element;
        }
    }

    @Override
    public int compareTo(ArrayDeque<T> other) {
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
