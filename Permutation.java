import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]), count = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        // Implement reservoir sampling algorithm to tackle the bonus task
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            if (rq.size() < k) {
                rq.enqueue(s);
            }
            else {
                if (StdRandom.uniform() < (double) k / count) {
                    rq.dequeue();
                    rq.enqueue(s);
                }
            }
        }

        for (String s : rq) {
            System.out.println(s);
        }
    }
}
