import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int dequeLen;
    private Node head;
    private Node tail;

    private class Node {
        Item data;
        Node next;
        Node previous;

        public Node(Item item) {
            data = item;
            next = null;
            previous = null;
        }
    }

    // construct an empty deque
    public Deque() {
        dequeLen = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeLen == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeLen;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item add to list should not be null.");
        }

        Node node = new Node(item);

        if (dequeLen == 0) {
            node.next = null;
            node.previous = null;

            head = node;
            tail = node;
        } else {
            head.previous = node;
            node.next = head;
            head = node;
        }

        dequeLen++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item add to list should not be null.");
        }

        Node node = new Node(item);

        if (dequeLen == 0) {
            node.next = null;
            node.previous = null;

            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.previous = tail;
            tail = node;
        }

        dequeLen++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (dequeLen == 0) {
            throw new NoSuchElementException();
        }

        Node removed = head;
        head = head.next;
        head.previous = null;
        removed.next = null;

        return removed.data;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (dequeLen == 0) {
            throw new NoSuchElementException("could not remove item from empty list.");
        }

        Node removed = tail;
        tail = tail.previous;
        tail.next = null;
        removed.previous = null;

        return removed.data;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {

            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Node found = current;
                if (found == null) {
                    throw new NoSuchElementException("No more items");
                }
                current = current.next;
                return found.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> list = new Deque<String>();
        list.addFirst("apple");
        list.addFirst("pen");
        list.addFirst("applepen");
        list.addFirst("macbook");
        list.addLast("iphone");
        System.out.println(list.removeLast());
        System.out.println("=========");

        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}