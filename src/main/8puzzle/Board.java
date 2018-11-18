import edu.princeton.cs.algs4.ResizingArrayQueue;
import java.util.ArrayList;

public class Board {
    private final int[][] blocks;

    private int hScore = 0;
    private int mScore = 0;

    public Board(int[][] blocks)  {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.blocks = blocks;
        generateHamming();
        generateManhattan();
    }

    public int dimension() {
        return blocks.length;
    }

    private void generateHamming() {
        int n = dimension();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == n - 1  && j == n-1) {
                    if (blocks[i][j] != 0) {
                        hScore++;
                    }
                } else if (blocks[i][j] != n*i + j + 1) {
                    hScore++;
                }
            }
        }
    }

    public int hamming()  {
        // number of blocks out of place
        return hScore;
    }

    private void generateManhattan() {
        int n = dimension();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                int value = blocks[i][j];
                int x, y;
                if (value == 0) {
                    x = n - 1;
                    y = n - 1;
                } else {
                    x = (value - 1) % n;
                    y = (value - 1) / n;
                }
                mScore+= Math.abs(i-y) + Math.abs(j-x);
            }
        }
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        return mScore;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin()  {
        // a board that is obtained by exchanging any pair of blocks
        int x0 = (int) (Math.random() * dimension());
        int y0 = (int) (Math.random() * dimension());
        int x1, y1;

        if (Math.random() < 0.5) {
            //horizontally
            y1 = y0;
            if (x0 == 0) {
                // swap with right
                x1 = x0 + 1;
            } else if (x0 == dimension()-1) {
                //swap with left
                x1 = x0 - 1;
            } else {
                if (Math.random() < 0.5) {
                    // swap with right
                    x1 = x0 + 1;
                } else {
                    //swap with left
                    x1 = x0 - 1;
                }
            }
        } else {
            //vertically
            x1 = x0;
            if (y0 == 0) {
                // swap with bottom
                y1 = y0 + 1;
            } else if (y0 == dimension()-1) {
                //swap with top
                y1 = y0 - 1;
            } else {
                if (Math.random() < 0.5) {
                    // swap with bottom
                    y1 = y0 + 1;
                } else {
                    //swap with top
                    y1 = y0 - 1;
                }
            }
        }
        int[][] copyBlocks = getBlocksCopyWithSwitch(x0, y0, x1, y1);
        return new Board(copyBlocks);
    }


    private int[][] getBlocksCopyWithSwitch(int x0, int y0, int x1, int y1) {
        int[][] copyBlocks = new int[dimension()][dimension()];

        for (int i=0; i<dimension(); i++) {
            System.arraycopy(blocks[i], 0, copyBlocks[i], 0, blocks[i].length);
        }

        int temp = copyBlocks[y0][x0];
        copyBlocks[y0][x0] = copyBlocks[y1][x1];
        copyBlocks[y1][x1] = temp;
        return copyBlocks;
    }


    public boolean equals(Object y) {
        // does this board equal y?
        if (y == null) return false;
        return toString() == y.toString();
    }

    private ArrayList<Integer> getZero() {
        ArrayList<Integer> zero = new ArrayList<>();
        int n = dimension();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (blocks[i][j] == 0) {
                    zero.add(i);
                    zero.add(j);
                    return zero;
                }
            }
        }
        return zero;
    }

    public Iterable<Board> neighbors() {
        //get for neighbouts if possible and neibour !isEqual to predecessor
        ArrayList<Integer> zero = getZero();
        int x0 = zero.get(1);
        int y0 = zero.get(0);
        ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
        if (x0 != 0) {
            //try move empty to left
            int[][] blocksZeroLeft = getBlocksCopyWithSwitch(x0, y0, x0-1, y0);
            Board zeroLeft = new Board(blocksZeroLeft);
            if (!equals(zeroLeft)) {
                queue.enqueue(zeroLeft);
            }
        }

        if (x0 != dimension()-1) {
            //try move empty to right
            int[][] blocksZeroRight = getBlocksCopyWithSwitch(x0, y0, x0+1, y0);
            Board zeroRight = new Board(blocksZeroRight);
            if (!equals(zeroRight)) {
                queue.enqueue(zeroRight);
            }
        }

        if (y0 != 0) {
            //try move empty to up
            int[][] blocksZeroUp = getBlocksCopyWithSwitch(x0, y0, x0, y0-1);
            Board zeroUp = new Board(blocksZeroUp);
            if (!equals(zeroUp)) {
                queue.enqueue(zeroUp);
            }
        }

        if (y0 != dimension()-1) {
            //try move empty to bottom
            int[][] blocksZeroBottom = getBlocksCopyWithSwitch(x0, y0, x0, y0+1);
            Board zeroBottom = new Board(blocksZeroBottom);
            if (!equals(zeroBottom)) {
                queue.enqueue(zeroBottom);
            }
        }
        return queue;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder str = new StringBuilder();
        for (int i=0; i<blocks.length; i++) {
            for (int j=0; j<blocks[i].length; j++) {
                str.append((i == 0 && j== 0) ? blocks[i][j] : "-"+blocks[i][j]);
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        // unit` tests (not graded)
    }

}