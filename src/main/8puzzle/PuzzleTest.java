import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleTest {
    public static void main(String[] args) {
        In in = new In("input/8puzzle/puzzle2x2-01.txt");
//        In in = new In("input/8puzzle/puzzle00.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.manhattan());
        System.out.println(initial.manhattan());
        System.out.println(initial.manhattan());
        // solve the puzzle
        /*Solver solver = new Solver(initial);


        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }*/
    }
}
