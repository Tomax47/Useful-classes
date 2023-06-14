import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class IntegerHashTable {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private LinkedList<Entry>[] table;
    private int size;

    public IntegerHashTable() {
        table = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    private class Entry {
        int key;
        int value;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(int key) {
        return Math.abs(key) % table.length;
    }

    public void put(int key, int value) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
            table[index].add(new Entry(key, value));
            size++;
        } else {
            LinkedList<Entry> list = table[index];
            for (Entry entry : list) {
                if (entry.key == key) {
                    entry.value = value; // Update existing key
                    return;
                }
            }
            list.add(new Entry(key, value)); // Add new key
            size++;
        }

        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD) {
            resizeTable();
        }
    }

    public int get(int key) {
        int index = hash(key);

        if (table[index] != null) {
            LinkedList<Entry> list = table[index];
            for (Entry entry : list) {
                if (entry.key == key) {
                    return entry.value;
                }
            }
        }

        throw new IllegalArgumentException("Key not found.");
    }

    public void remove(int key) {
        int index = hash(key);

        if (table[index] != null) {
            LinkedList<Entry> list = table[index];
            Iterator<Entry> iterator = list.iterator();
            while (iterator.hasNext()) {
                Entry entry = iterator.next();
                if (entry.key == key) {
                    iterator.remove();
                    size--;
                    return;
                }
            }
        }

        throw new IllegalArgumentException("Key not found.");
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    private void resizeTable() {
        LinkedList<Entry>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;

        for (LinkedList<Entry> list : oldTable) {
            if (list != null) {
                for (Entry entry : list) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    public void printElements() {
        for (LinkedList<Entry> list : table) {
            if (list != null) {
                for (Entry entry : list) {
                    System.out.println("Key: " + entry.key + ", Value: " + entry.value);
                }
            }
        }
    }

    public void sortElements() {
        int[] keys = new int[size];
        int index = 0;
        for (LinkedList<Entry> list : table) {
            if (list != null) {
                for (Entry entry : list) {
                    keys[index++] = entry.key;
                }
            }
        }
        Arrays.sort(keys);
        for (int key : keys) {
            System.out.println("Key: " + key + ", Value: " + get(key));
        }
    }
}
