import edu.princeton.cs.algs4.StdRandom;

import java.lang.Object;
import java.lang.Override;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private LinkedList<Item> items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new LinkedList<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return items.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return items.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item add to list should not be null.");
        }

        items.addLast(item);
    }

    // remove and return a random item
    public Item dequeue() {
        int randomIndex = StdRandom.uniform(items.size());

        Item last = items.removeLast();
        Item removed = items.get(randomIndex);

        items.set(randomIndex, last);
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomIndex = StdRandom.uniform(items.size());
        return items.get(randomIndex);
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {

        Object[] itemArray = items.toArray();
        StdRandom.shuffle(itemArray);

        Iterator<Item> it = new Iterator<Item>() {

            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < items.size() && itemArray[currentIndex] != null;
            }

            @Override
            public Item next() {
                Object item = itemArray[currentIndex];
                if (item == null) {
                    throw new NoSuchElementException();
                }
                currentIndex++;
                return (Item) item;
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
        RandomizedQueue<String> list = new RandomizedQueue<String>();
        list.enqueue("apple0");
        list.enqueue("apple1");
        list.enqueue("apple2");
        list.enqueue("apple3");
        list.enqueue("apple4");
        list.enqueue("apple5");

        list.dequeue();

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("==================");

        Iterator<String> it2 = list.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
    }
}