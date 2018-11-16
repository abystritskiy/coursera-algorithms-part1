import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Board {
    private final int[][] blocks;
    private final int move;

    public Board(int[][] blocks)  {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.blocks = blocks;
        move = 0;

    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming()  {
        // number of blocks out of place
        int hamming = 0;
        int n = dimension();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == n - 1  && j == n-1) {
                    if (blocks[i][j] != 0) {
                        hamming++;
                    }
                } else if (blocks[i][j] != n*i + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming+move;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
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

                manhattan+= Math.abs(i-y) + Math.abs(j-x);
            }
        }
        return manhattan+move;
    }

    public boolean isGoal() {
        int n = dimension();
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == n - 1  && j == n-1) {
                    if (blocks[i][j] != 0) {
                        return false;
                    }
                } else if (blocks[i][j] != n*i + j + 1) {
                    return false;
                }
            }
        }
        return true;
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
        int[][] copyBlocks = new int[dimension()][dimension()];

        for (int i=0; i<dimension(); i++) {
            System.arraycopy(blocks[i], 0, copyBlocks[i], 0, blocks[i].length);
        }

        int temp = copyBlocks[x0][y0];
        copyBlocks[x0][y0] = copyBlocks[x1][y1];
        copyBlocks[x1][y1] = temp;

        return new Board(copyBlocks);
    }



    public boolean equals(Object y) {
        // does this board equal y?

        return toString() == y.toString();
    }

    public Iterable<Board> neighbors() {
       return new Neighbours(new ArrayList<Board>());
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuffer str = new StringBuffer();
        for (int i=0; i<blocks.length; i++) {
            for (int j=0; j<blocks[i].length; j++) {
                str.append((i == 0 && j== 0) ? blocks[i][j] : "-"+blocks[i][j]);
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
    }

    private class Neighbours<Board> implements Iterable<Board>
    {
        ArrayList<Board> neighbours;

        private Neighbours(ArrayList<Board> neighbours) {
            this.neighbours = neighbours;
        }

        public ArrayList<Board> getBoards() {
            return neighbours;
        }

        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator<Board>(this);
        }
    }

    private class BoardIterator<Board> implements Iterator<Board> {
        private ArrayList<Board> boards;
        private int position = 0;

        private BoardIterator(Neighbours<Board> neighbours) {
            boards = neighbours.getBoards();
        }

        public boolean hasNext() {
            return position < boards.size();
        }

        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return boards.get(position++);
        }

        public void remove(Board board) {
            throw new UnsupportedOperationException();
        }
    }
}