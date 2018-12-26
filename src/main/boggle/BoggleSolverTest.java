import java.util.ArrayList;

public class BoggleSolverTest {

    public static void main(String[] args) {
        // unit tests

        String[] dictionary = new String[6013];
        edu.princeton.cs.algs4.In in = new edu.princeton.cs.algs4.In("input/boggle/dictionary-algs4.txt");
//        edu.princeton.cs.algs4.In in = new edu.princeton.cs.algs4.In("input/boggle/dictionary-16q.txt");
        for (int i = 0; i < 6013; i++) {
            String line = in.readLine();
            if (line != null) {
                dictionary[i] = line.trim();
            }
        }


        BoggleSolver bs = new BoggleSolver(dictionary);
        BoggleBoard bb = new BoggleBoard("input/boggle/board4x4.txt");
//        BoggleBoard bb = new BoggleBoard("input/boggle/board-16q.txt");

        System.out.println(bb.toString());
        Iterable<String> words = bs.getAllValidWords(bb);
        ArrayList<String> sorted = new ArrayList<>();
        for (String word : words) {
            sorted.add(word);
        }

        java.util.Collections.sort(sorted);
        int i = 0;
        for (String word : sorted) {
            System.out.println(word);
            i++;
        }
        System.out.println("Total: "+i);

        System.out.println(bs.scoreOf("ABLE"));
    }
}