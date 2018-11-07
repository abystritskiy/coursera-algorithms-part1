import java.util.*;
import java.lang.*;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;

    public Deque() {
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        Node oldFirst = this.first;
        Node newFIrst = new Node()
        this.first = item;
        this.first.next = oldFirst;
        this.size++;
    }

    public void addLast(Item item) {

    }

    public Item removeFirst() {

    }

    public Item removeLast() {

    }

    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class Node {
        private Node next, prev;
        private Item value;

        protected void Node(Item item) {
            this.value = item;
        }

    }


    private class DequeueIterator implements Iterator<Item>
    {
        private int current = 0;

        public boolean hasNext() {
//            return
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
