import java.util.ArrayList;

public class BoggleSolverTest {

    public static void main(String[] args) {
        // unit tests

        String[] dictionary = new String[6013];
        edu.princeton.cs.algs4.In in = new edu.princeton.cs.algs4.In("input/boggle/dictionary-algs4.txt");
        for (int i = 0; i < 6013; i++) {
            dictionary[i] = in.readLine().trim();
        }
        BoggleSolver bs = new BoggleSolver(dictionary);
        BoggleBoard bb = new BoggleBoard("input/boggle/board-q.txt");

        System.out.println(bb.toString());

        Iterable<String> words = bs.getAllValidWords(bb);
        ArrayList<String> sorted = new ArrayList<>();
        for (String word : words) {
            sorted.add(word);
        }

        java.util.Collections.sort(sorted);
        for (String word : sorted) {
            System.out.println(word);
        }
    }
}
