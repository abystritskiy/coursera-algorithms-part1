import edu.princeton.cs.algs4.TrieST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BoggleSolver {
    private final TrieST<Integer> trie;
    private BoggleBoard boggleBoard;
    private HashSet<String> set;

    public BoggleSolver(String[] dictionary) {
        trie = new TrieST<>();
        for (int i = 0; i < dictionary.length; i++) {
            trie.put(dictionary[i], i);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.boggleBoard = board;
        this.set = new HashSet<>();

        for (int y = 0; y < board.rows(); y++) {
            for (int x = 0; x < board.cols(); x++) {
                ArrayList<Integer> from = new ArrayList<>();
                StringBuilder str = new StringBuilder();
                HashSet<ArrayList<Integer>> pointsUsed = new HashSet<>();
                dfs(y, x, str, pointsUsed);
            }
        }
        return set;
    }

    private void dfs(int row, int col, StringBuilder str, HashSet<ArrayList<Integer>> pointsUsed) {
        ArrayList<Integer> pointUsed = new ArrayList<>();
        pointUsed.add(row);
        pointUsed.add(col);
        pointsUsed.add(pointUsed);

        String chr = String.valueOf(boggleBoard.getLetter(row, col));
        chr = chr.equals("Q") ? "QU" : chr;
        str.append(chr);

        String prefix = str.toString();
        List<List<Integer>> adj = getAdjacent(row, col, pointsUsed);

        if (adj.isEmpty() || !trie.keysWithPrefix(prefix).iterator().hasNext()) {
            if (chr.length() == 1) {
                str.deleteCharAt(str.length() - 1);
            } else {
                str.delete(str.length() - 2, str.length());
            }
            pointsUsed.remove(pointUsed);
            return;
        }

        if (trie.contains(str.toString()) && str.length() > 2) {
            set.add(str.toString());
        }
        for (List<Integer> neighbour : adj) {
            dfs(neighbour.get(0), neighbour.get(1), str, pointsUsed);
        }

        if (chr.length() == 1) {
            str.deleteCharAt(str.length() - 1);
        } else {
            str.delete(str.length() - 2, str.length());
        }
        pointsUsed.remove(pointUsed);
    }

    private List<List<Integer>> getAdjacent(int row, int col, HashSet<ArrayList<Integer>> from) {
        List<List<Integer>> adj = new ArrayList<>();
        if (col > 0) {
            ArrayList<Integer> left = new ArrayList<>();
            left.add(row);
            left.add(col - 1);
            if (!from.contains(left)) {
                adj.add(left);
            }
            if (row > 0) {
                ArrayList<Integer> leftTop = new ArrayList<>();
                leftTop.add(row - 1);
                leftTop.add(col - 1);
                if (!from.contains(leftTop)) {
                    adj.add(leftTop);
                }
            }
            if (row < boggleBoard.rows() - 1) {
                ArrayList<Integer> leftBottom = new ArrayList<>();
                leftBottom.add(row + 1);
                leftBottom.add(col - 1);
                if (!from.contains(leftBottom)) {
                    adj.add(leftBottom);
                }
            }
        }
        if (col < boggleBoard.cols() - 1) {
            ArrayList<Integer> right = new ArrayList<>();
            right.add(row);
            right.add(col + 1);
            if (!from.contains(right)) {
                adj.add(right);
            }
            if (row > 0) {
                ArrayList<Integer> rightTop = new ArrayList<>();
                rightTop.add(row - 1);
                rightTop.add(col + 1);
                if (!from.contains(rightTop)) {
                    adj.add(rightTop);
                }
            }
            if (row < boggleBoard.rows() - 1) {
                ArrayList<Integer> rightBottom = new ArrayList<>();
                rightBottom.add(row + 1);
                rightBottom.add(col + 1);
                if (!from.contains(rightBottom)) {
                    adj.add(rightBottom);
                }
            }
        }
        if (row > 0) {
            ArrayList<Integer> top = new ArrayList<>();
            top.add(row - 1);
            top.add(col);
            if (!from.contains(top)) {
                adj.add(top);
            }
        }
        if (row < boggleBoard.rows() - 1) {
            ArrayList<Integer> bottom = new ArrayList<>();
            bottom.add(row + 1);
            bottom.add(col);
            if (!from.contains(bottom)) {
                adj.add(bottom);
            }
        }

        return adj;
    }

    public int scoreOf(String word) {
        int length = word.length();
        int score;
        if (length <= 2) {
            score = 0;
        } else if (length <= 4) {
            score = 1;
        } else if (length <= 5) {
            score = 1;
        } else if (length <= 6) {
            score = 3;
        } else if (length <= 7) {
            score = 5;
        } else {
            score = 11;
        }
        return score;
    }

    public static void main(String[] args) {
        // unit tests

        String[] dictionary = new String[6013];
        edu.princeton.cs.algs4.In in = new edu.princeton.cs.algs4.In(/*"input/boggle/dictionary-zingarelli2005.txt"*/);
        for (int i = 0; i < 6013; i++) {
            dictionary[i] = in.readLine().trim();
        }
        BoggleSolver bs = new BoggleSolver(dictionary);
        BoggleBoard bb = new BoggleBoard("input/boggle/board-q.txt");


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