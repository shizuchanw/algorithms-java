/*
Flora Wang
Nov.16, 2021
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int len;

    private class Node {
        private Item item;
        private Node head;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        len = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (len == 0);
    }

    // return the number of items on the deque
    public int size() {
        return len;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item can't be null");
        len++;
        // create new first node
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.head = null;

        if (first != null) {
            first.head = newFirst;
            newFirst.next = first;
        }
        else {
            newFirst.next = null;
            last = newFirst;
        }
        first = newFirst;


    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item can't be null");
        len++;
        // create new last node
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;

        if (last != null) {
            newLast.head = last;
            last.next = newLast;
        }
        else {
            newLast.head = null;
            first = newLast;
        }
        last = newLast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        len--;
        Item oldItem = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        else {
            first.head = null;
        }
        return oldItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        len--;
        Item oldItem = last.item;
        last = last.head;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        return oldItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Deque underflow");
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        StdOut.println(test.isEmpty());
        test.addFirst(1);
        test.addLast(2);
        test.addFirst(3);
        test.addLast(4);

        StdOut.println(test.removeFirst());

        for (int s : test)
            StdOut.print(s);

        StdOut.println();
        StdOut.println(test.removeLast());

        for (int s : test)
            StdOut.print(s);

        StdOut.println("\nlist size: " + test.size());
    }
}
