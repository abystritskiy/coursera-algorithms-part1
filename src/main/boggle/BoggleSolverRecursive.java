import edu.princeton.cs.algs4.TrieST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BoggleSolverRecursive {
    private final TrieST<Integer> trie;
    private BoggleBoard boggleBoard;
    private HashSet<String> set;

    public BoggleSolverRecursive(String[] dictionary) {
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
                StringBuilder str = new StringBuilder();
                Cell cell = new Cell(y, x, boggleBoard.getLetter(y, x));
                HashSet<String> cellUsed = new HashSet<>();
                dfs(cell, str, cellUsed);
            }
        }
        return set;
    }

    private void dfs(Cell cell, StringBuilder str, Set<String> pointsUsed) {
        str.append(cell.val);
        pointsUsed.add(cell.getCode());

        String prefix = str.toString();
        List<Cell> adj = getAdjacent(cell, pointsUsed);

        if (trie.contains(str.toString()) && str.length() > 2) {
            set.add(str.toString());
        }

        if (adj.isEmpty() || !trie.keysWithPrefix(prefix).iterator().hasNext()) {
            if (cell.val.length() == 1) {
                str.deleteCharAt(str.length() - 1);
            } else {
                str.delete(str.length() - 2, str.length());
            }
            pointsUsed.remove(cell.getCode());
            return;
        }
        for (Cell neighbour : adj) {
            dfs(neighbour, str, pointsUsed);
        }

        if (cell.val.length() == 1) {
            str.deleteCharAt(str.length() - 1);
        } else {
            str.delete(str.length() - 2, str.length());
        }
        pointsUsed.remove(cell.getCode());
    }

    private List<Cell> getAdjacent(Cell cell, Set<String> pointsUsed) {
        int row = cell.row;
        int col = cell.col;

        List<Cell> adj = new ArrayList<>();
        if (col > 0) {
            Cell left = new Cell(row, col - 1, boggleBoard.getLetter(row, col - 1));
            if (!pointsUsed.contains(left.getCode())) {
                adj.add(left);
            }
            if (row > 0) {
                Cell leftTop = new Cell(row - 1, col - 1, boggleBoard.getLetter(row - 1, col - 1));
                if (!pointsUsed.contains(leftTop.getCode())) {
                    adj.add(leftTop);
                }
            }
            if (row < boggleBoard.rows() - 1) {
                Cell leftBottom = new Cell(row + 1, col - 1, boggleBoard.getLetter(row + 1, col - 1));
                if (!pointsUsed.contains(leftBottom.getCode())) {
                    adj.add(leftBottom);
                }
            }
        }
        if (col < boggleBoard.cols() - 1) {
            Cell right = new Cell(row, col + 1, boggleBoard.getLetter(row, col + 1));
            if (!pointsUsed.contains(right.getCode())) {
                adj.add(right);
            }
            if (row > 0) {
                Cell rightTop = new Cell(row - 1, col + 1, boggleBoard.getLetter(row - 1, col + 1));
                if (!pointsUsed.contains(rightTop.getCode())) {
                    adj.add(rightTop);
                }
            }
            if (row < boggleBoard.rows() - 1) {
                Cell rightBottom = new Cell(row + 1, col + 1, boggleBoard.getLetter(row + 1, col + 1));
                if (!pointsUsed.contains(rightBottom.getCode())) {
                    adj.add(rightBottom);
                }
            }
        }
        if (row > 0) {
            Cell top = new Cell(row - 1, col, boggleBoard.getLetter(row - 1, col));
            if (!pointsUsed.contains(top.getCode())) {
                adj.add(top);
            }
        }
        if (row < boggleBoard.rows() - 1) {
            Cell bottom = new Cell(row + 1, col, boggleBoard.getLetter(row + 1, col));
            if (!pointsUsed.contains(bottom.getCode())) {
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
    }

    private class Cell {
        private final int row;
        private final int col;
        private final String val;

        private Cell(int row, int col, char val) {
            this.row = row;
            this.col = col;
            this.val = val == 'Q' ? "QU" : String.valueOf(val);
        }

        private String getCode() {
            return row + "-" + col + "-" + val;
        }
    }
}