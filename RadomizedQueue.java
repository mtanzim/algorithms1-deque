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

    private Item[] removeNull(Item[] origArr, int size) {
        Item[] temp = (Item[]) new Object[size];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (origArr[i] != null) {
                temp[k++] = origArr[i];
            }
        }
        return temp;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        // for the randomized queue, remove null items during resize
        // Item[] temp = (Item[]) new Object[capacity];
        /*int k = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != null) {
                temp[k++] = a[i];
            }
        }*/

        a = removeNull(a, capacity);
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

        private Item[] a_shuffled;         // array of items
        private int i = 0;

        // constructor
        public ReverseArrayIterator() {
            // i = n - 1;
            // int k = n - 1;
            // copy over non-null items
            /*for (int j = k; j > 0; j--) {
                if (a[j] != null) {
                    a_shuffled[k] = a[j];
                    k--;
                }
            }*/
            // i is now the same size as the number of non-null items

            a_shuffled = removeNull(a, realN);
            // now shuffle the array
            StdRandom.shuffle(a_shuffled);
            // i = a_shuffled.length - 1;

        }

        public boolean hasNext() {
            return i < realN;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a_shuffled[i++];
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
