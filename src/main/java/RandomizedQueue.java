import java.util.*;
import java.lang.*;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;

    public RandomizedQueue() {
        size = 0;
        s = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == s.length) {
            resize(size * 2);
        }
        s[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int id = StdRandom.uniform(size);
        Item item = s[id];
        if (id != size - 1) {
            s[id] = s[size - 1];
        }
        s[--size] = null;

        if (size <= s.length / 4) {
            this.resize(s.length / 2);
        }

        return item;
    }

    protected void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < s.length; i++) {
            copy[i] = s[i];
        }
        this.s = copy;
    }


    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int id = StdRandom.uniform(size);
        return s[id];
    }


    public static void main(String[] args) {
        // unit testing (optional)
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private int current = 0;

        public boolean hasNext() {
            return this.current != RandomizedQueue.this.s.length;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = RandomizedQueue.this.s[current];
            current++;
            return item;
        }
    }

}