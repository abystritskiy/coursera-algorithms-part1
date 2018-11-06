import java.util.*;
import java.io.*;
import java.lang.*;
import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;
    private int first;
    private int last;

    public Deque() {
        size = 1;
        s = (Item[]) new Object[size];
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return s[first] == null;
    }

    public int size() {
        if (isEmpty()) {
            return  0;
        }
        return last - first + 1;
    }

    public void addFirst(Item item) {
        if (last + 1 > size) {
            resize(2 * size);
        }
        Item[] copy  = (Item[]) new Object[size];
        copy[first] = item;

        for (int i = first; i<=last; i++) {
            copy[i+1] = s[i];
        }
        s = copy;
        last++;
    }

    public void addLast(Item item) {
        if (last + 1 > size) {
            resize(2 * size);
        }
        s[last] = item;
        last++;
    }

    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = s[first];
        first++;
        return item;
    }

    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = s[last];
        s[last] = null;
        last--;
        if (last < size / 4) {
            resize(size / 2);
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    public static void main(String[] args) {
        // unit testing (optional)
    }

    private void resize(int c) {
        Item[] copy = (Item []) new Object[c];
        for (int i = first; i <= last; i++) {
            copy[i] = s[i];
        }
        size = c;
        s = copy;
        first = 0;
        last = size - 1;
    }

    private class DequeueIterator implements Iterator<Item>
    {
        private int current = 0;

        public boolean hasNext() {
            return current != last;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = s[current];
            current++;
            return item;
        }
    }
}
