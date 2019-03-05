/* *****************************************************************************
 *  Name: Permutation
 *  Date: Mar 3, 2019
 *  Description: Client application for assignment 2
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // String line = StdIn.readString(args);
        // StdOut.println(line);
        int k = Integer.parseInt(args[0]);
        RadomizedQueue<String> randQ = new RadomizedQueue<String>();

        assert (k > 0);
        StdOut.println("Hello from Permutation with k: " + k);
        // StdOut.println(args[1]);
        // enqueue everything
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            // if (item.equals("\n")) break;
            // if (item.equals("quit")) break;
            StdOut.println(item);
            randQ.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(randQ.sample());
        }
    }
}
