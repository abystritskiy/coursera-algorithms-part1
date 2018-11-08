import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        for (int i = 0; i < n; i++) {
            System.out.println(rq.dequeue());
        }
    }

    public static void main2(String[] args) {

        // java -cp out/production/coursera-algorithms-part1:lib/algs4.jar Permutation 3 < input/week2/distinct.txt
        int n = Integer.parseInt(args[0]);
        Deque<String> deque = new Deque<>();
        int j = 0;
        while (!StdIn.isEmpty()) {
            if (j%2 == 0) {
                deque.addFirst(StdIn.readString());
            } else {
                deque.addFirst(StdIn.readString());
            }
            j++;
        }

        for (int i = 0; i < n; i++) {
            System.out.println(deque.removeLast());
        }
    }
}
