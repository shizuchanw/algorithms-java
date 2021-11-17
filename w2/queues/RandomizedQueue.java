/*
Flora Wang
Nov.16, 2021
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue
    private Item[] queue;
    private int N;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (N == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        if (N == queue.length) {
            resize(N * 2);
        }
        queue[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException("");
        if (N > 0 && N == queue.length / 4) {
            resize(N / 2);
        }
        int pos = StdRandom.uniform(N);
        Item temp = queue[pos];
        queue[pos] = queue[--N];
        queue[N] = null;
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) throw new NoSuchElementException("");
        int pos = StdRandom.uniform(N);
        return queue[pos];
    }

    // resize the array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[N * 2];
        for (int i = 0; i < N; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] randomQueue;

        private RQIterator() {
            randomQueue = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                randomQueue[i] = queue[i];
            }
            StdRandom.shuffle(randomQueue);
        }

        public boolean hasNext() {
            return current != N;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("underflow");
            Item temp = randomQueue[current];
            current++;
            return temp;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        StdOut.println(test.isEmpty());
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        StdOut.println(test.isEmpty());
        StdOut.println("sample: " + test.sample());
        for (int i : test) StdOut.print(i);
        StdOut.println();
        StdOut.println("deque: " + test.dequeue());
        StdOut.println("size: " + test.size());
        for (int i : test) StdOut.print(i);
    }
}
