import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final WeightedQuickUnionUF qu;
    private final int bottom;
    private final int size;
    private boolean[][] opened;
    private int numberOfOpen = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        qu = new WeightedQuickUnionUF(size * size + 2);
        bottom = size * size + 1;
        opened = new boolean[size][size];
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        if (this.isOpen(row, col)) {
            return;
        }

        opened[row - 1][col - 1] = true;
        numberOfOpen++;

        int idCell = (row - 1) * size + col;

        openCell(row, col, idCell);
    }

    private void openCell(int row, int col, int idCell) {
        if (row == 1) {
            qu.union(idCell, TOP);
        } else if (opened[row - 2][col - 1]) {
            int idTop = (row - 2) * size + col;
            qu.union(idCell, idTop);
        }

        if (row == size) {
            qu.union(idCell, bottom);
        } else if (opened[row][col - 1]) {
            int idBottom = row * size + col;
            qu.union(idCell, idBottom);
        }

        if (col > 1 && opened[row - 1][col - 2]) {
            int idLeft = (row - 1) * size + col - 1;
            qu.union(idCell, idLeft);
        }

        if (col < size && opened[row - 1][col]) {
            int idRight = (row - 1) * size + col + 1;
            qu.union(idCell, idRight);
        }

        if (size == 1) {
            qu.union(bottom, 1);
            qu.union(TOP, 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        return opened[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        int idCell = (row - 1) * size + col;
        return opened[row - 1][col - 1] && qu.connected(TOP, idCell);
    }

    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    public boolean percolates() {
        return qu.connected(bottom, TOP);
    }

    public static void main(String[] args) {
        // main methods - used for tests
    }
}
