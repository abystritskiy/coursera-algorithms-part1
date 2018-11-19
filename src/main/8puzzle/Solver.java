import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchBoard last;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<SearchBoard> minPQ = new MinPQ<>();
        SearchBoard initialSearchBoard = new SearchBoard(initial, null);
        minPQ.insert(initialSearchBoard);

        MinPQ<SearchBoard> minPQTwin = new MinPQ<>();
        SearchBoard twinSearchBoard = new SearchBoard(initial.twin(), null);
        minPQTwin.insert(twinSearchBoard);


        while (!minPQ.isEmpty()) {
            SearchBoard currentSB = minPQ.delMin();
            SearchBoard currentTwinSB = minPQTwin.delMin();

            if (currentSB.getBoard().isGoal()) {
                last = currentSB;
                solvable = true;
                break;
            }

            if (currentTwinSB.getBoard().isGoal()) {
                last = currentTwinSB;
                solvable = false;
                break;
            }

            for (Board neighbour : currentSB.getBoard().neighbors()) {
                if (currentSB.getPredecessor() == null || !currentSB.getBoard().equals(neighbour)) {
                    SearchBoard child = new SearchBoard(neighbour, currentSB);
                    minPQ.insert(child);
                }
            }

            for (Board neighbourTwin : currentTwinSB.getBoard().neighbors()) {
                if (currentTwinSB.getPredecessor() == null || !currentTwinSB.getBoard().equals(neighbourTwin)) {
                    SearchBoard child = new SearchBoard(neighbourTwin, currentTwinSB);
                    minPQTwin.insert(child);
                }
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return last.getMove();
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> solutionsStack = new Stack<>();
            SearchBoard current = last;
            while (current != null) {
                solutionsStack.push(current.getBoard());
                current = current.getPredecessor();
            }
            return solutionsStack;
        }
        return null;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
    }

    private class SearchBoard implements Comparable<SearchBoard> {
        private final SearchBoard predecessor;
        private final Board board;
        private final int move;

        private SearchBoard(Board board, SearchBoard predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            if (predecessor == null) {
                this.move = 0;
            } else {
                this.move = predecessor.move+1;
            }

        }

        private int getMove() {
            return move;
        }

        private int getScore() {
            return board.manhattan() + this.move;
        }

        public int compareTo(SearchBoard that) {
            if (that.getScore() == getScore()) return 0;
            return getScore() > that.getScore() ? 1 : -1;
        }

        private SearchBoard getPredecessor() {
            return this.predecessor;
        }

        private Board getBoard() {
            return this.board;
        }
    }
}