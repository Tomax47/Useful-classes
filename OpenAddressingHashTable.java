import java.util.Arrays;
import java.util.Iterator;

import static com.sun.beans.introspect.ClassInfo.clear;

public class OpenAddressingHashTable<K, V> implements Iterable<V> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final double DEFAULT_LOAD_FACTOR = 0.7;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private double loadFactor;

    public OpenAddressingHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public OpenAddressingHashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    private static class Entry<K, V> {
        K key;
        V value;
        boolean deleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    private int getNextIndex(int currentIndex) {
        return (currentIndex + 1) % capacity;
    }

    private int findIndex(K key) {
        int index = hash(key);
        int startIndex = index;

        while (table[index] != null) {
            if (!table[index].deleted && table[index].key.equals(key)) {
                return index;
            }
            index = getNextIndex(index);
            if (index == startIndex) {
                break; // Reached back to the starting point
            }
        }

        return -1; // Key not found
    }

    public void put(K key, V value) {
        if (size >= capacity * loadFactor) {
            resize();
        }

        int index = hash(key);
        int startIndex = index;

        while (table[index] != null && !table[index].deleted) {
            if (table[index].key.equals(key)) {
                table[index].value = value;
                return;
            }
            index = getNextIndex(index);
            if (index == startIndex) {
                break; // Reached back to the starting point
            }
        }

        table[index] = new Entry<>(key, value);
        size++;
    }

    public V get(K key) {
        int index = findIndex(key);
        if (index != -1) {
            return table[index].value;
        }
        return null; // Key not found
    }

    public void remove(K key) {
        int index = findIndex(key);
        if (index != -1) {
            table[index].deleted = true;
            size--;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] newTable = new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            if (entry != null && !entry.deleted) {
                int index = hash(entry.key);
                int startIndex = index;

                while (newTable[index] != null) {
                    index = getNextIndex(index);
                    if (index == startIndex) {
                        throw new RuntimeException("Hash table is full.");
                    }
                }

                newTable[index] = entry;
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry<K, V> entry : table) {
            if (entry != null && !entry.deleted) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Iterator<V> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<V> {
        private int currentIndex;

        HashTableIterator() {
            currentIndex = -1;
            findNextValidIndex();
        }

        @Override
        public boolean hasNext() {
            return currentIndex < capacity - 1;
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the hash table.");
            }
            currentIndex++;
            V value = table[currentIndex] != null && !table[currentIndex].deleted ? table[currentIndex].value : null;
            findNextValidIndex();
            return value;
        }

        private void findNextValidIndex() {
            while (currentIndex < capacity - 1 && (table[currentIndex + 1] == null || table[currentIndex + 1].deleted)) {
                currentIndex++;
            }
        }
    }

    public void printTable() {
        System.out.println(Arrays.toString(table));
    }

    public void sort() {
        Entry<K, V>[] entries = new Entry[size];
        int entryIndex = 0;

        for (Entry<K, V> entry : table) {
            if (entry != null && !entry.deleted) {
                entries[entryIndex] = entry;
                entryIndex++;
            }
        }

        Arrays.sort(entries, 0, entryIndex, (a, b) -> a.key.hashCode() - b.key.hashCode());

        // Reconstruct the hash table with the sorted entries
        clear();
        for (Entry<K, V> entry : entries) {
            put(entry.key, entry.value);
        }
    }
}
