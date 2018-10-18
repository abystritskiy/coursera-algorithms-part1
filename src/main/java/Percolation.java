package src.main.java;

public class Percolation {
    public static int[][] matrix;
    public static int numberOrOpen;

    public Percolation(int n) {
        matrix = new int[n][n];
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        matrix[row-1][col-1] = 1;
        numberOrOpen++;
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        return matrix[row-1][col-1] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOrOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
