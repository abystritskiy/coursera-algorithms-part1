import java.util.*;
import java.lang.*;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> first;
    private Node<Item> last;

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        Node<Item> newFirst = new Node(item);
        Node<Item> oldFirst = first;
        newFirst.next = oldFirst;
        first = newFirst;
        size++;
    }

    public void addLast(Item item) {
        Node<Item> newLast = new Node(item);
        Node<Item> oldLast = last;
        newLast.prev = oldLast;
        last = newLast;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> first = this.first;
        this.first = first.next;
        size--;
        return first.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> last = this.last;
        this.last = last.prev;
        size--;
        return last.item;
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next, prev;

        private Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }


    private class DequeueIterator implements Iterator<Item> {
        private Node<Item> current;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node<Item> oldCurrent = this.current;
            current = oldCurrent.next;
            return oldCurrent.item;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }
    }
}
