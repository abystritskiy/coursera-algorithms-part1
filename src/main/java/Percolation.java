import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    static WeightedQuickUnionUF QU;
    boolean[][] opened;
    int numberOfOpen = 0;
    int top = 0;
    int bottom;
    int size;

    public Percolation(int n) {
        size = n;
        QU = new WeightedQuickUnionUF(size * size + 2);
        bottom = size*size + 1;
        opened = new boolean[size][size];
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        if (this.isOpen(row, col)) {
            return;
        }

        opened[row-1][col-1] = true;
        numberOfOpen++;

        int idCell = (row - 1) * size + col;

        if (row  == 1) {
            QU.union(idCell, top);
        } else if (opened[row-2][col-1]) {
            int idTop =  (row - 2) * size + col;
            QU.union(idCell, idTop);
        }

        if (row  == size) {
            QU.union(idCell, bottom);
        } else if (opened[row][col-1]) {
            int idBottom =  row  * size + col;
            QU.union(idCell, idBottom);
        }

        if (col > 1 && opened[row-1][col-2]) {
            int idLeft =  (row - 1) * size + col - 1 ;
            QU.union(idCell, idLeft);
        }

        if (col < size && opened[row-1][col]) {
            int idRight =  (row - 1) * size + col + 1;
            QU.union(idCell, idRight);
        }

        if (size == 1)  {
            QU.union(bottom, 1);
            QU.union(top, 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        return opened[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        int idCell = (row - 1) * size + col;
        return opened[row-1][col-1] && QU.connected(top, idCell);
    }

    public int numberOfOpenSites() {
        return numberOfOpen;
    }


    public boolean percolates() {
        return QU.connected(bottom, top);
    }

    public static void main(String[] args) {

    }
}
