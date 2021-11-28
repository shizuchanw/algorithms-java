/*
Flora Wang
Nov.16, 2021
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = (Integer.parseInt(args[0]));
        RandomizedQueue<String> temp = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) temp.enqueue(StdIn.readString());
        for (int i = 0; i < k; i++) {
            StdOut.println(temp.dequeue());
        }
    }
}
