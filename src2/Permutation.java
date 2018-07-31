import java.lang.IllegalArgumentException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        while (number-- > 0) {
            System.out.println(rq.dequeue());
        }

    }
}