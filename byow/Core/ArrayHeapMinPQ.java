package byow.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Entry> minHeap;
    private HashMap<T, Entry> entries;
    private int size;


    private class Entry {
        private T item;
        private double priority;
        private int position;

        Entry(T item, double priority, int position) {
            this.item = item;
            this.priority = priority;
            this.position = position;
        }
    }

    public ArrayHeapMinPQ() {
        this.minHeap = new ArrayList<>(); //starts at index 1 :)
        this.minHeap.add(null); //sentinel node
        this.entries = new HashMap<>();
        this.size = 0;
    }

    private void swap(int index1, int index2) {
        Entry temp = minHeap.get(index2);
        minHeap.set(index2, minHeap.get(index1));
        minHeap.set(index1, temp);
        minHeap.get(index1).position = index1;
        minHeap.get(index2).position = index2;
    }

    //accounts for null
    private void swim(int index) {
        if (index != 1) {
            int parentPos = index / 2;
            if (minHeap.get(parentPos).priority > minHeap.get(index).priority) {
                swap(index, parentPos);
                swim(parentPos);
            }
        }
    }

    // Helper add function: start at the end position
    private void addHelper(Entry entry) {
        minHeap.add(entry);
        entries.put(entry.item, entry);
        swim(entry.position);
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            addHelper(new Entry(item, priority, size + 1));
            size++;
        }
    }

    /* Returns true if the PQ contains the given item. O(log(n)) */
    @Override
    public boolean contains(T item) {
        return entries.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. O(log(n)) */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return minHeap.get(1).item;
        }
    }

    private Entry smallerChild(int index1, int index2) {
        Entry prior1 = minHeap.get(index1);
        Entry prior2 = minHeap.get(index2);
        if (prior1.priority > prior2.priority) {
            return prior2;
        } else if (prior1.priority < prior2.priority) {
            return prior1;
        } else {
            return prior1;
        }
    }

    // Check nulls, left first, then right .. accounts for null.
    private void sink(int index) {
        if (size > 1) {
            int left = index * 2;
            int right = index * 2 + 1;
            double curr = minHeap.get(index).priority;
            if (left <= size && right <= size) { //two nodes exist
                Entry smallChild = smallerChild(left, right);
                if (smallChild.priority < curr) {
                    int temp = smallChild.position;
                    swap(index, smallChild.position);
                    sink(temp);
                }
            } else if (left <= size) {
                if (minHeap.get(left).priority < curr) { // go left?
                    swap(index, left);
                }
            }
        }
    }

    /* Removes and returns the minimum item.
    Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            Entry minEntry = minHeap.get(1); //constant time
            Entry bottomEntry = minHeap.get(size); // constant time
            entries.remove(minEntry.item); //hashmap update
            minHeap.set(1, bottomEntry); //array update
            minHeap.remove(size);
            bottomEntry.position = 1;
            size--;
            sink(1);
            return minEntry.item;
        }
    }

    /* Returns the number of items in the PQ. O(log(n)) */
    @Override
    public int size() {
        return size;
    }

    // moves Entry to right place no matter where it is! Can already be in the right place to start!
    private void changePriorityHelper(T item, int index, int prevIndex) {
        //one of them will always fail immediately, they have if statements inside
        swim(index);
        sink(index);
        if (prevIndex != index) {
            changePriorityHelper(item, entries.get(item).position, index);
        }
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. O(log(n)) */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        } else {
            int index = entries.get(item).position; // constant
            entries.put(item, new Entry(item, priority, index)); // reference updated
            minHeap.set(index, entries.get(item));
            //if statements and swimming/sinking through tree
            changePriorityHelper(item, index, index);
        }

    }

    public double priority(T item) {
        return entries.get(item).priority;
    }

    public T[] toArray() {
        T[] heap = (T[]) new Object[minHeap.size()];
        for (int j = 1; j < minHeap.size(); j++) {
            heap[j] = minHeap.get(j).item;
            //System.out.print(minHeap.get(j).item + " ");
        }
        return heap;
    }

}
