import java.util.*;
import java.io.*;
import java.lang.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;
    private int first;
    private int last;

    public RandomizedQueue() {
        size = 1;
        s = (Item[]) new Object[size];
        first = 0;
        last = 0;
    }
    public boolean isEmpty() {
        return s[first] == null;
    }
    public int size() {
        if (this.isEmpty()) {
            return 0;
        }
        return last - first + 1;
    }
    public void enqueue(Item item) {
        if (last + 1 > size) {
            resize
        }
    }
    public Item dequeue() {
        // remove and return a random item
    }
    public Item sample() {
        // return a random item (but do not remove it)
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
    }
    public static void main(String[] args) {
        // unit testing (optional)
    }
}