import java.util.NoSuchElementException;
import java.util.Iterator;

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
            resize(s.length * 2, s.length);
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

        if (size >= 1 && size == s.length / 4) {
            resize(s.length / 2, size);
        }

        return item;
    }

    private void resize(int newCapacity, int copyLength) {
        Item[] copy = (Item[]) new Object[newCapacity];
        System.arraycopy(s, 0, copy, 0, copyLength);
        s = copy;
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

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int copySize = size;
        private final Item[] sCopy;

        private RandomizedQueueIterator() {
            sCopy = (Item[]) new Object[copySize];
            System.arraycopy(s, 0, sCopy, 0, size);
        }

        public boolean hasNext() {
            return copySize > 0;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randomIndex = StdRandom.uniform(copySize);
            Item next = sCopy[randomIndex];
            if (randomIndex != copySize - 1) {
                sCopy[randomIndex] = sCopy[copySize - 1];
            }
            sCopy[--copySize] = null;
            return next;
        }
    }

}