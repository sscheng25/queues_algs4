import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 1;

    private Item[] a;         // array of items
    private int n;            // number of elements on stack
    // private int rand;

    // construct an empty randomized queue
    public RandomizedQueue() {

        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        int rand = StdRandom.uniformInt(n);
        Item item = a[rand];

        if (rand < n - 1) {
            a[rand] = a[n - 1];
            a[n - 1] = null;
        }
        else {
            a[rand] = null;
        }

        n--;
        return item;
    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        int rand = StdRandom.uniformInt(n);
        Item item = a[rand];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int initialSize;
        private int[] order;

        public ArrayIterator() {
            initialSize = n;
            current = 0;
            order = new int[n];
            for (int i = 0; i < n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return current < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = a[order[current]];
            if (size() != initialSize) {
                throw new java.util.ConcurrentModificationException();
            }
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        System.out.println("-isempty " + rq.isEmpty());

        rq.enqueue("one");
        rq.enqueue("two");
        rq.enqueue("three");
        rq.enqueue("four");
        System.out.println("-initial " + rq.size());

        System.out.println("-dequeue " + rq.dequeue());

        for (String s : rq) {
            System.out.println("  " + s);
        }

        System.out.println("-dequeued size " + rq.size());

        System.out.println("-sample " + rq.sample());
        System.out.println("-sample " + rq.sample());
        System.out.println("-sample " + rq.sample());

        System.out.println("-random iterator");
        for (String s : rq) {
            System.out.println(s);
        }

        System.out.println("-random iterator one more time");
        for (String s : rq) {
            System.out.println(s);
        }
    }
}


