import edu.princeton.cs.algs4.ResizingArrayQueue;

public class Board {
    private int[][] board;

    private int emptyRow;
    private int emptyCol;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
                board[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return board.length;
    }


    public int hamming() {
        int hScore = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cell = board[j][i];
                int expected = (n * j + i + 1 == n * n ? 0 : n * j + i + 1);
                if (cell != 0 && cell != expected) {
                    hScore++;
                }
            }
        }

        return hScore;
    }

    public int manhattan() {
        int mScore = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cell = board[i][j];
                if (cell != 0) {
                    mScore += Math.abs((cell - 1) / n - i) + Math.abs((cell - 1) % n - j);
                }

            }
        }
        return mScore;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] copyBlocks = new int[dimension()][dimension()];

        for (int i = 0; i < dimension(); i++) {
            System.arraycopy(board[i], 0, copyBlocks[i], 0, board[i].length);
        }

        Board twinBoard = new Board(copyBlocks);

        if (board[0][0] == 0 || board[0][1] == 0) {
            twinBoard.swap(1, 0, 1, 1);
        } else {
            twinBoard.swap(0, 0, 0, 1);
        }
        return twinBoard;
    }


    private int[][] getBlocksCopyWithSwitch(int x0, int y0, int x1, int y1) {
        int[][] copyBlocks = new int[dimension()][dimension()];

        for (int i = 0; i < dimension(); i++) {
            System.arraycopy(board[i], 0, copyBlocks[i], 0, board[i].length);
        }

        int temp = copyBlocks[y0][x0];
        copyBlocks[y0][x0] = copyBlocks[y1][x1];
        copyBlocks[y1][x1] = temp;
        return copyBlocks;
    }


    private void swap(int r1, int c1, int r2, int c2) {
        int tmp = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = tmp;

        if (board[r1][c1] == 0) {
            emptyRow = r1;
            emptyCol = c1;
        }
        if (board[r2][c2] == 0) {
            emptyRow = r2;
            emptyCol = c2;
        }

    }


    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (that.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (that.board[i][j] != this.board[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int x0 = emptyCol;
        int y0 = emptyRow;
        ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
        if (x0 != 0) {
            int[][] blocksZeroLeft = getBlocksCopyWithSwitch(x0, y0, x0 - 1, y0);
            Board zeroLeft = new Board(blocksZeroLeft);
            queue.enqueue(zeroLeft);
        }

        if (x0 != dimension() - 1) {
            int[][] blocksZeroRight = getBlocksCopyWithSwitch(x0, y0, x0 + 1, y0);
            Board zeroRight = new Board(blocksZeroRight);
            queue.enqueue(zeroRight);
        }

        if (y0 != 0) {
            int[][] blocksZeroUp = getBlocksCopyWithSwitch(x0, y0, x0, y0 - 1);
            Board zeroUp = new Board(blocksZeroUp);
            queue.enqueue(zeroUp);
        }

        if (y0 != dimension() - 1) {
            int[][] blocksZeroBottom = getBlocksCopyWithSwitch(x0, y0, x0, y0 + 1);
            Board zeroBottom = new Board(blocksZeroBottom);
            queue.enqueue(zeroBottom);
        }
        return queue;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
    }

}