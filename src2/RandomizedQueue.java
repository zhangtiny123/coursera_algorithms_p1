import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int Len;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return Len == 0;
    }

    public int size() {
        return Len;
    }

    private void resize(int capacity) {
        assert capacity >= Len;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < Len; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        autoEnlarge();
        items[Len++] = item;
    }

    private void autoEnlarge() {
        if (Len == items.length) resize(2*items.length);
    }

    public Item dequeue() {
        assertNotEmpty();
        int index = randomIndex();
        Item item = items[index];
        items[index] = items[Len-1];
        items[Len-1] = null;
        Len--;
        autoShrink();
        return item;
    }

    private void assertNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    private void autoShrink() {
        if (Len > 0 && Len == items.length/4) resize(items.length/2);
    }

    private int randomIndex()
    {
        return StdRandom.uniform(0, Len);
    }

    public Item sample() {
        assertNotEmpty();
        return items[randomIndex()];
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] r;
        private int i;

        public RandomArrayIterator() {
            copyQueue();
            StdRandom.shuffle(r);
        }

        private void copyQueue() {
            r = (Item[]) new Object[Len];
            for (int i = 0; i < Len; i++) {
                r[i] = items[i];
            }
        }

        public boolean hasNext() {
            return i < Len;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return r[i++];
        }
    }
}