import java.util.Arrays;
import java.util.Iterator;

public class DynamicArray <T extends Comparable<T>>  implements Iterable<T> {

    private T[] elements;
    private int size;

    public DynamicArray() {
        elements = (T[]) new Object[10];
        size = 0;
    }

    public int indexOf(T elem) {
        for (int i = 0; i < size; i++) {
            if(elements[i].equals(elem)) return i;
        }
        return -1;
    }

    public T get(int index) {
        return elements[index];
    }

    public void add(T elem) {
        if (size == elements.length) {
            T[] newElements = (T[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        elements[size++] = elem;
    }

    public void remove(int index) {
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
    }

    public void insert(T elem, int index) {
        if (size == elements.length) {
            T[] newElements = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, index);
            newElements[index] = elem;
            System.arraycopy(elements, index, newElements, index + 1, size - index);
            elements = newElements;
            size++;
        } else {
            for (int i = size - 1; i >= index; i--) {
                elements[i + 1] = elements[i];
            }
            elements[index] = elem;
            size++;
        }
    }

    public void set(T elem, int index) {

    }

    public T getMin() {
        T min = elements[0];
        for (int i = 1; i < size; i++) {
            if (elements[i].compareTo(min) < 0) {
                min = elements[i];
            }
        }
        return min;
    }

    public T getMax() {
        T max = elements[0];
        for (int i = 1; i < size; i++) {
            if (elements[i].compareTo(max) > 0) {
                max = elements[i];
            }
        }
        return max;
    }

    public void traverse() {
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public void sort() {
        for (int i = 0; i < size-1; i++) {
            for (int j = i+1; j < size; j++) {
                if (elements[i].compareTo(elements[j]) > 0) {
                    T temp = elements[i];
                    elements[i] = elements[j];
                    elements[j] = temp;
                }
            }
        }
    }

    public void clear() {
        elements = (T[]) new Object[10];
        size = 0;
    }


    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(elements).iterator();
    }
}
