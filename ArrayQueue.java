import java.util.Iterator;

/**

                  Queue based on array!
 */
public class ArrayQueue<T extends Comparable<T>> implements Comparable<ArrayQueue<T>>, Iterable<T> {

    private T[] queue;
    private int front;
    private int rear;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        queue = (T[]) new Comparable[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(T element) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = element;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T element = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator implements Iterator<T> {
        private int current = front;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            T element = queue[current];
            current = (current + 1) % queue.length;
            count++;
            return element;
        }
    }

    @Override
    public int compareTo(ArrayQueue<T> other) {
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
            return 1; // Other queue is shorter
        } else if (iterator2.hasNext()) {
            return -1; // Other queue is longer
        } else {
            return 0; // Queues are equal
        }
    }
}
