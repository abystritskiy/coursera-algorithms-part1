import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;

public class BoggleSolver {
    private final Trie trie = new Trie();
    private BoggleBoard boggleBoard;
    private Cell[][] bb;
    private HashSet<String> set;

    public BoggleSolver(String[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            trie.put(dictionary[i], String.valueOf(trie.size()));
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.boggleBoard = board;
        this.bb = new Cell[board.rows()][board.cols()];

        this.set = new HashSet<>();
        for (int y = 0; y < board.rows(); y++) {
            for (int x = 0; x < board.cols(); x++) {
                bb[y][x] = new Cell(y, x, boggleBoard.getLetter(y, x));
            }
        }

        for (int y = 0; y < board.rows(); y++) {
            for (int x = 0; x < board.cols(); x++) {
                ArrayList<Cell> path = new ArrayList<>();

                Task task = new Task(bb[y][x], path);
                dfs(task);
            }
        }
        return set;
    }

    private void dfs(Task task) {
        Stack<Task> stack = new Stack<>();
        stack.push(task);

        while (!stack.empty()) {
            Task currentTask = stack.pop();

            Cell cell = currentTask.cell;
            ArrayList<Cell> path = new ArrayList<>(currentTask.path);
            path.add(cell);

            String prefix = buildPrefix(path);

            if (trie.prefixNode(prefix) == null) {
                continue;
            }

            if (trie.contains(prefix) && prefix.length() > 2) {
                set.add(prefix);
            }

            ArrayList<Cell> adj = getAdjacent(cell, path);
            for (Cell neighbour : adj) {
                Task newTask = new Task(neighbour, path);
                stack.push(newTask);
            }
        }
    }

    private String buildPrefix(List<Cell> path) {
        StringBuilder str = new StringBuilder();
        for (Cell cell : path) {
            str.append(cell.val);
        }
        return str.toString();
    }

    private ArrayList<Cell> getAdjacent(Cell cell, ArrayList<Cell> path) {
        int row = cell.row;
        int col = cell.col;

        ArrayList<Cell> adj = new ArrayList<>();
        if (col > 0) {
            if (row > 0) {
                Cell leftTop = bb[row - 1][col - 1];
                if (!path.contains(leftTop)) {
                    adj.add(leftTop);
                }
            }

            Cell left = bb[row][col - 1];
            if (!path.contains(left)) {
                adj.add(left);
            }

            if (row < boggleBoard.rows() - 1) {
                Cell leftBottom = bb[row + 1][col - 1];
                if (!path.contains(leftBottom)) {
                    adj.add(leftBottom);
                }
            }
        }

        if (row < boggleBoard.rows() - 1) {
            Cell bottom = bb[row + 1][col];
            if (!path.contains(bottom)) {
                adj.add(bottom);
            }
        }

        if (col < boggleBoard.cols() - 1) {
            if (row < boggleBoard.rows() - 1) {
                Cell rightBottom = bb[row + 1][col + 1];
                if (!path.contains(rightBottom)) {
                    adj.add(rightBottom);
                }
            }

            Cell right = bb[row][col + 1];
            if (!path.contains(right)) {
                adj.add(right);
            }

            if (row > 0) {
                Cell rightTop = bb[row - 1][col + 1];
                if (!path.contains(rightTop)) {
                    adj.add(rightTop);
                }
            }
        }
        if (row > 0) {
            Cell top = bb[row - 1][col];
            if (!path.contains(top)) {
                adj.add(top);
            }
        }
        return adj;
    }

    public int scoreOf(String word) {
        if (!trie.contains(word)) {
            return 0;
        }
        int length = word.length();
        int score;
        if (length <= 2) {
            score = 0;
        } else if (length <= 4) {
            score = 1;
        } else if (length <= 5) {
            score = 2;
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
    }

    private class Task {
        private final Cell cell;
        private final ArrayList<Cell> path;

        private Task(Cell cell, ArrayList<Cell> path) {
            this.cell = cell;
            this.path = path;
        }
    }
}