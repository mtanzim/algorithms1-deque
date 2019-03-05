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
        // for the randomized queue, remove null items during resize
        StdOut.println("resizing to new capacity: " + capacity);
        Item[] temp = (Item[]) new Object[capacity];

        int k = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != null) {
                temp[k++] = a[i];
            }
        }

        a = temp;
        n = realN;
    }

    // add item
    public void enqueue(Item item) {
        StdOut.println("Enqueing " + item);
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
        realN++;
    }

    // remove at random
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomNum = StdRandom.uniform(n);
        StdOut.println("Dequeing index: " + randomNum);
        Item item = a[randomNum];
        StdOut.println("Dequeing " + item);
        // infinite loop? Statistically impossible?
        while (item == null) {
            StdOut.println("Trying again because item is:" + item);
            randomNum = StdRandom.uniform(n);
            StdOut.println("Dequeing index: " + randomNum);
            item = a[randomNum];
            StdOut.println("Dequeing " + item);
        }
        StdOut.println("");
        a[randomNum] = null;
        realN--;
        // shrink size of array if necessary
        if (realN > 0 && realN == a.length / 4) resize(a.length / 2);
        return item;
    }

    // return random item, do not remove
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomNum = StdRandom.uniform(n);
        Item item = a[randomNum];
        while (item == null) {
            randomNum = StdRandom.uniform(n);
            item = a[randomNum];
        }
        return item;
    }

    // return a new iterator
    public Iterator<Item> iterator() {
        return new ShuffledArrayIterator();

    }

    private class ShuffledArrayIterator implements Iterator<Item> {

        private int i = 0;

        // constructor
        public ShuffledArrayIterator() {
            // remove nulls before returning iterator
            resize(realN);
            // shuffle the array at uniform
            StdRandom.shuffle(a);
        }

        public boolean hasNext() {
            return i < realN;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i++];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        StdOut.println("Hello!");
        RadomizedQueue<String> randQ = new RadomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("q")) break;

            if (!randQ.isEmpty()) {
                if (item.equals("-")) StdOut.println(randQ.dequeue() + " ");
                else if (item.equals("=")) StdOut.println("sample: " + randQ.sample());

                    // test iterator
                else if (item.equals("@")) {
                    StdOut.println("Iterator Test");
                    Iterator iter = randQ.iterator();
                    int i = 0;
                    while (iter.hasNext()) {
                        StdOut.print(iter.next() + " => ");
                        i++;
                    }
                    StdOut.println();
                    StdOut.println("End Iterator. It had size: " + i);

                }
                else {
                    randQ.enqueue(item);
                }
            }
            else {
                if (!((item.equals("-")) || (item.equals("=")) || (item.equals("@")))) {
                    randQ.enqueue(item);
                }
                else {
                    StdOut.println("Invalid operation");
                }
            }
            randQ.toStr();
        }
    }

}
