import java.util.ArrayList;
import java.util.Iterator;

public class Heap<T extends Comparable<T>> implements Comparable<Heap<T>>, Iterable<T> {

    private ArrayList<T> heapList;

    public Heap() {
        heapList = new ArrayList<>();
    }

    public void createHeap(T[] array) {
        clear();
        for (T item : array) {
            heapList.add(item);
        }
        heapify();
    }

    public int indexOf(T element) {
        return heapList.indexOf(element);
    }

    public T get(int index) {
        if (index < 0 || index >= heapList.size()) {
            throw new IndexOutOfBoundsException();
        }
        return heapList.get(index);
    }

    public void add(T element) {
        heapList.add(element);
        siftUp(heapList.size() - 1);
    }

    public void remove(T element) {
        int index = heapList.indexOf(element);
        if (index != -1) {
            int lastIndex = heapList.size() - 1;
            swap(index, lastIndex);
            heapList.remove(lastIndex);
            siftDown(index);
        }
    }

    public void changeValue(int index, T newValue) {
        if (index < 0 || index >= heapList.size()) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = heapList.get(index);
        heapList.set(index, newValue);
        if (newValue.compareTo(oldValue) < 0) {
            siftUp(index);
        } else if (newValue.compareTo(oldValue) > 0) {
            siftDown(index);
        }
    }

    public T getMinimum() {
        if (heapList.isEmpty()) {
            return null;
        }
        return heapList.get(0);
    }

    public T getMaximum() {
        if (heapList.isEmpty()) {
            return null;
        }
        T maximum = heapList.get(0);
        for (T element : heapList) {
            if (element.compareTo(maximum) > 0) {
                maximum = element;
            }
        }
        return maximum;
    }

    private void heapify() {
        int startIndex = getParentIndex(heapList.size() - 1);
        for (int i = startIndex; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && heapList.get(index).compareTo(heapList.get(parentIndex)) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    private void siftDown(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);
        int smallestIndex = index;
        if (leftChildIndex < heapList.size() && heapList.get(leftChildIndex).compareTo(heapList.get(smallestIndex)) < 0) {
            smallestIndex = leftChildIndex;
        }
        if (rightChildIndex < heapList.size() && heapList.get(rightChildIndex).compareTo(heapList.get(smallestIndex)) < 0) {
            smallestIndex = rightChildIndex;
        }
        if (smallestIndex != index) {
            swap(index, smallestIndex);
            siftDown(smallestIndex);
        }
    }

    private void swap(int index1, int index2) {
        T temp = heapList.get(index1);
        heapList.set(index1, heapList.get(index2));
        heapList.set(index2, temp);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return (2 * index) + 1;
    }

    private int getRightChildIndex(int index) {
        return (2 * index) + 2;
    }

    @Override
    public Iterator<T> iterator() {
        return heapList.iterator();
    }

    public void traverse() {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public void clear() {
        heapList.clear();
    }

    @Override
    public int compareTo(Heap<T> other) {
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
            return 1; // Other heap is shorter
        } else if (iterator2.hasNext()) {
            return -1; // Other heap is longer
        } else {
            return 0; // Heaps are equal
        }
    }

    public boolean isMaxHeap() {
        for (int i = 0; i < heapList.size(); i++) {
            int leftChildIndex = getLeftChildIndex(i);
            int rightChildIndex = getRightChildIndex(i);

            if (leftChildIndex < heapList.size() && heapList.get(leftChildIndex).compareTo(heapList.get(i)) > 0) {
                return false;
            }

            if (rightChildIndex < heapList.size() && heapList.get(rightChildIndex).compareTo(heapList.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isMinHeap() {
        for (int i = 0; i < heapList.size(); i++) {
            int leftChildIndex = getLeftChildIndex(i);
            int rightChildIndex = getRightChildIndex(i);

            if (leftChildIndex < heapList.size() && heapList.get(leftChildIndex).compareTo(heapList.get(i)) < 0) {
                return false;
            }

            if (rightChildIndex < heapList.size() && heapList.get(rightChildIndex).compareTo(heapList.get(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    public void convertToMaxHeap() {
        if (!isMaxHeap()) {
            int size = heapList.size();
            for (int i = size / 2 - 1; i >= 0; i--) {
                siftDown(i);
            }
        }
    }

    public void convertToMinHeap() {
        if (!isMinHeap()) {
            int size = heapList.size();
            for (int i = size / 2 - 1; i >= 0; i--) {
                siftUp(i);
            }
        }
    }

    public void printHeap() {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();
    }

}
