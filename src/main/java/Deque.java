import java.util.NoSuchElementException;
import java.util.Iterator;

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
        return size <= 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> newFirst = new Node<>(item);
        if (first != null) {
            Node<Item> oldFirst = first;
            newFirst.next = oldFirst;
            oldFirst.prev = newFirst;
        }
        first = newFirst;
        size++;
        if (size == 1) {
            last = newFirst;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> newLast = new Node<>(item);
        if (last != null) {
            Node<Item> oldLast = last;
            newLast.prev = oldLast;
            oldLast.next = newLast;
        }
        last = newLast;
        size++;
        if (size == 1) {
            first = newLast;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> firstNode = first;
        first = firstNode.next;
        size--;
        return firstNode.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> lastNode = last;
        last = lastNode.prev;
        size--;
        return lastNode.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private static class Node<Item> {
        public final Item item;
        private Node<Item> next, prev;

        private Node(Item nodeItem) {
            item = nodeItem;
            next = null;
            prev = null;
        }
    }


    private class DequeueIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove(Item item) {
            throw new UnsupportedOperationException();
        }
    }
}
