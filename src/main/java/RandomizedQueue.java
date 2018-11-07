import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;

    public RandomizedQueue() {
        this.size = 0;
        this.s = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == this.s.length) {
            this.resize(this.s.length * 2);
        }
        this.size++;
        s[this.size] = item;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int id = StdRandom.uniform(size);
        Item item = this.s[id];
        if (id != this.size-1) {
            this.s[id] = this.s[this.size];
            this.s[this.size] = null;
        }

        if (this.size < this.s.length / 4) {
            this.resize(this.s.length / 2);
        }

        this.size--;
        return item;
    }

    protected void resize(int capacity)
    {
        Item[] copy = (Item []) new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            copy[i] = s[i];
        }
        this.s = copy;
    }


    public Item sample() {
        if (this.isEmpty()) {
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

    private class DequeueIterator implements Iterator<Item>
    {
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
            Item item = RandomizedQueue.this.s[this.current];
            this.current++;
            return item;
        }
    }

}