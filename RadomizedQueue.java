/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RadomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;         // array of items
    private int n;            // number of elements on stack, despite null elements
    private int realN;            // number of elements on stack, without nullElemens

    // construct an empty randomized queue
    public RadomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
        realN = 0;
    }

    public boolean isEmpty() {
        return realN == 0;
    }


    // is the randomized queue empty?
    public int size() {
        return realN;
    }

    private void toStr() {
        for (int i = 0; i < n; i++) {
            StdOut.print(a[i] + " -> ");
        }
        StdOut.println("");
        StdOut.println("Size of non null: " + size());
        StdOut.println("Size: " + n);
        StdOut.println("");

    }

    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        // for the randomized queue, remove null items during resize
        Item[] temp = (Item[]) new Object[capacity];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != null) {
                temp[k++] = a[i];
            }
        }

        a = temp;
        n = realN;
        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    public void enqueue(Item item) {
        StdOut.println("Enqueing " + item);
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
        realN++;
    }

    // add the item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomNum = StdRandom.uniform(n);
        StdOut.println("Dequeing index: " + randomNum);
        Item item = a[randomNum];
        StdOut.println("Dequeing " + item);
        // prevents infinite loops, but may not work?
        // int k = 0;
        while (item == null) {
            StdOut.println("Trying again because item is:" + item);
            randomNum = StdRandom.uniform(n);
            StdOut.println("Dequeing index: " + randomNum);
            item = a[randomNum];
            StdOut.println("Dequeing " + item);
            // k++;
        }
        StdOut.println("");
        a[randomNum] = null;
        realN--;
        // shrink size of array if necessary
        if (realN > 0 && realN == a.length / 4) resize(a.length / 2);
        return item;
    }

    // remove and return a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomNum = StdRandom.uniform(n);
        Item item = a[randomNum];
        // prevents infinite loops, but may not work?
        // int k = 0;
        while (item == null) {
            randomNum = StdRandom.uniform(n);
            item = a[randomNum];
            // k++;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();

    }

    // an iterator, doesn't implement remove() since it's optional
    // needs to be randomized!!!
    private class ReverseArrayIterator implements Iterator<Item> {

        private Item[] a_shuffled = (Item[]) new Object[realN];         // array of items
        private int i;


        // constructor
        public ReverseArrayIterator() {
            i = n - 1;
            int k = i;
            // copy over non-null items
            for (int j = i; j > 0; j--) {
                if (a[j] != null) {
                    a_shuffled[k] = a[j];
                    k--;
                }
            }
            // i is now the same size as the number of non-null items
            i = a_shuffled.length;

            // now shuffle the array
            StdRandom.shuffle(a_shuffled);

        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }


    // unit testing (optional)
    public static void main(String[] args) {
        StdOut.println("Hello!");
        RadomizedQueue<String> randQ = new RadomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) randQ.enqueue(item);
                // if (item.equals("=")) StdOut.println(randQ.sample());
            else if (!randQ.isEmpty()) StdOut.println(randQ.dequeue() + " ");
            if (!randQ.isEmpty()) StdOut.println("sample: " + randQ.sample());
            randQ.toStr();
        }
        StdOut.println("(" + randQ.size() + " left on stack)");
    }

}
