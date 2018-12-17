import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TrieST;
import java.util.ArrayList;

public class BoggleSolver
{
    private final TrieST trie = new TrieST();
    public BoggleSolver(String[] dictionary) {

        for (int i=0; i<dictionary.length; i++) {
            trie.put(dictionary[i], i);
        }
        Iterable<String> list = trie.keysWithPrefix("WO");
        for (String match: list) {
            System.out.println(match);
        }
        // Initializes the data structure using the given array of strings as the dictionary.
        // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        // Returns the set of all valid words in the given Boggle board, as an Iterable.
        return new ArrayList<>();
    }

    public int scoreOf(String word) {
        // Returns the score of the given word if it is in the dictionary, zero otherwise.
        // (You can assume the word contains only the uppercase letters A through Z.)
        return 0;
    }

    public static void main(String[] args) {
        String filename = "input/boggle/dictionary-algs4.txt";
        String[] dictionary = new String[6013];
        In in = new In(filename);
        for (int i=0; i<6013; i++) {
            dictionary[i] = in.readLine().trim();
        }
        BoggleSolver bs = new BoggleSolver(dictionary);

    }
}