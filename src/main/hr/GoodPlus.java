import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class GoodPlus {
    private static int s0 = 0; //area of first biggest
    private static int s1 = 0; //area of second biggest

    private static ArrayList<Integer> plus0; //first biggest
    private static ArrayList<Integer> plus1; //second biggest


    private static final Character GOOD = 'G';

    static int twoPluses(String[] grid) {

        for (int i=0; i<grid.length; i++) {
            for (int j = 0; j < grid[i].length(); j++) {
                if (grid[i].charAt(j) != GOOD) {
                    continue;
                }

                int offset = Math.min(
                        Math.min(i, j),
                        Math.min(grid.length-1-i, grid[i].length()-1-j)
                );
                int maxSIJ = 1;
                int sizeLength = 0;

                //go top;
                for (int i1=i; i1>i-offset; i1--) {
                    if (grid[i1+1].charAt(j) != GOOD) {
                        break;
                    }
                    sizeLength++;
                }
                offset = Math.min(offset, sizeLength);

                //go bottom;
                sizeLength = 0;
                for (int i1=i; i1<i+offset; i1++) {
                    if (grid[i1-1].charAt(j) != GOOD) {
                        break;
                    }
                    sizeLength++;
                }
                offset = Math.min(offset, sizeLength);

                //go left
                sizeLength = 0;
                for (int j1=j; j1>j-offset; j1--) {
                    if (grid[i].charAt(j1-1) != GOOD) {
                        break;
                    }
                    sizeLength++;
                }
                offset = Math.min(offset, sizeLength);

                //go right
                sizeLength = 0;
                for (int j1=j; j1<j+offset; j1++) {
                    if (grid[i].charAt(j1+1) != GOOD) {
                        break;
                    }
                    sizeLength++;
                }
                offset = Math.min(offset, sizeLength);
                maxSIJ += 4 * offset;

                if (maxSIJ > s0) {
                    //replace s0 with maxSIJ
                    int s0old = s0;
                    s0 = maxSIJ;
                    ArrayList<Integer> oldPlus0 = plus0;
                    plus0 = new ArrayList<>();
                    plus0.add(i);
                    plus0.add(j);

                    //replace s1 with s0
                    s1 = s0old;
                    plus1 = oldPlus0;
                } else if (maxSIJ > s1) {
                    //replace s1 with maxSIJ
                    s1 = maxSIJ;
                    plus1 = new ArrayList<>();
                    plus1.add(i);
                    plus1.add(j);
                }
            }
        }
        return s0 * s1;
    }

    public static boolean overlap(ArrayList<Integer> p1, int s1, ArrayList<Integer> p2, int s2) {

        //identify leftPlus && rightPlus
        //overlap 1: rightSide of leftPlus >= leftSide of rightPlus - return true;


        //identify topPlus && bottomPlus
        //overlap 2: bottom of topPlus >= top of bottomPlus - return true;

        return false;
    }

    public static void main(String[] args) {
        In in = new In("input/hr/GoodPlus.txt");      // input file

        String[] nm = in.readLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        String[] grid = new String[n];

        for (int i = 0; i < n; i++) {
            String gridItem = in.readLine();

            //for debug only
            String line = gridItem;
            line = line.replaceAll("G", "+");
            line = line.replaceAll("B", ".");
            System.out.println( line);
            ///

            grid[i] = gridItem;
        }


        int result = twoPluses(grid);
        System.out.println("\n"+result);
    }
}
