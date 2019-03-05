/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n; // size of the queue
    private Node first; // front of queue
    private Node last; // end of the queue

    // helper Node class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // constructor: init empty queue
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    // is the deque empty?
    public int size() {
        return n;
    }

    // add the item to the  front (stack push)
    public void addFirst(Item item) {
        // StdOut.println("Adding " + item + " to front!");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        if (n == 0) {
            last = first;
        }
        n++;
        // StdOut.println("Added " + item + " to front!");
        // assert check();

    }

    // add the item to the back (queue enqueue)
    public void addLast(Item item) {
        // StdOut.println("Adding " + item + " to end!");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (!isEmpty()) {
            oldlast.next = last;
        }
        if (n == 0) {
            first = last;
        }
        n++;
        // StdOut.println("Added " + item + " to end!");
        return;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in the stack in LIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("START -> ");
        for (Item item : this)
            s.append(item + " -> ");
        s.append(" END");
        return s.toString();
    }

    // remove and return the item from the front
    // same as stack pop
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque empty!");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (first != null) {
            first.prev = null;
        }
        n--;
        if (n == 0) {
            last = first;
        }
        return item;                   // return the saved item
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque empty!");
        Item item = last.item;        // save item to return
        // StdOut.println("Removing " + item);
        last = last.prev;            // delete first node

        if (last != null) {
            // StdOut.println("Last now contains " + last.item);
            last.next = null;
        }
        n--;
        if (n == 0) {
            first = last;
        }
        return item;                   // return the saved item
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {

        // constructor test
        StdOut.println("Hello, starting Deque!");
        Deque<String> deque = new Deque<String>();
        StdOut.println("Size of queue:");
        StdOut.println(deque.size());
        StdOut.println("Is it empty?");
        StdOut.println(deque.isEmpty());

        // enqueue back
        deque.addLast("hello");
        deque.addLast("world");
        StdOut.println(deque.toString());

        // enqueue front/ push to stack
        deque.addFirst("hola");
        deque.addFirst("atlas");
        StdOut.println(deque.toString());

        //print deque
        StdOut.println(deque.toString());

        // pop/deque/removeFirst
        StdOut.println(deque.removeFirst());
        StdOut.println("Size of queue:");
        StdOut.println(deque.size());
        StdOut.println(deque.toString());

        // remove last
        StdOut.println(deque.removeLast());
        StdOut.println("Size of queue:");
        StdOut.println(deque.size());
        StdOut.println(deque.toString());

        //make empyty
        StdOut.println("Emptying: ");
        deque.removeLast();
        // deque.removeFirst();
        // deque.removeLast();
        StdOut.println(deque.toString());
        deque.removeLast();
        StdOut.println(deque.toString());
        // deque.removeLast();


        StdOut.println("Size of queue:");
        StdOut.println(deque.size());
        StdOut.println("Is it empty?");
        StdOut.println(deque.isEmpty());


        StdOut.println("Start again");
        deque.addFirst("Hi");
        deque.addFirst("Hello");
        deque.addFirst("Hola");
        deque.addFirst("Amigo");
        StdOut.println(deque.toString());

        deque.addLast("Atlas");
        deque.addLast("Map");
        deque.addLast("World");
        deque.addLast("Jagat");
        StdOut.println(deque.toString());

        //Empty again
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());

        StdOut.println("Size of queue:");
        StdOut.println(deque.size());
        StdOut.println("Is it empty?");
        StdOut.println(deque.isEmpty());

        // Cause an exception
        /*try {
            StdOut.println(deque.removeFirst());
        }
        catch (Exception error) {
            StdOut.println(error);
            try {
                StdOut.println(deque.removeLast());
            }
            catch (Exception errorInner) {
                StdOut.println(errorInner);

            }
        }*/


    }
}
