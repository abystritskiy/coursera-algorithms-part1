import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class GoodPlus {
    private static ArrayList<Plus> pluses = new ArrayList<>();
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

                //go top;
                int sizeLength = 0;
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

                Plus plus = new Plus(i, j, offset);
                pluses.add(plus);
            }
        }

        int total = 0;
       /* System.out.println(
                overlap(
                        new Plus(3,3,3),
                        new Plus(4, 7,3)
                )
        );*/
        Plus plus0 = new Plus(-1, -1,0);
        Plus plus1 = new Plus(-1, -1,0);

        for (int k = 0; k < pluses.size()-1; k++) {
            for (int l = k+1; l<pluses.size(); l++) {
                Plus plK = pluses.get(k);
                Plus plL = pluses.get(l);
                int sizeK = 4 * plK.sideLength + 1;
                int sizeL = 4 * plL.sideLength + 1;
                boolean overlap = overlap(plK, plL);
                if (!overlap && sizeK * sizeL > total) {
                    total = sizeK * sizeL;
                    plus0 = plK;
                    plus1 = plL;
                }
            }
        }
        System.out.println(plus0.y + "; "+plus0.x+"; "+plus0.sideLength);
        System.out.println(plus1.y + "; "+plus1.x+"; "+plus1.sideLength);
        return total;
    }

    private static class Plus {
        private int sideLength;
        private int y;
        private int x;

        public Plus(int y, int x, int sideLength)  {
            this.x = x;
            this.y = y;
            this.sideLength = sideLength;
        }

        private int getLeft() {
            return x - sideLength;
        }

        private int getRight() {
            return x + sideLength;
        }

        private int getTop() {
            return y - sideLength;
        }

        private int getBottom() {
            return y + sideLength;
        }
    }

    public static boolean overlap(Plus p1, Plus p2) {
        if (p1.x == p2.x) {
            //one above the other - check if top
            if (p1.y>p2.y) {
                if (p1.getTop() <= p2.getBottom()) {
                    return true;
                }
            } else {
                if (p2.getTop() <= p1.getBottom()) {
                    return true;
                }
            }
        }
        if (p1.y == p2.y) {
            //one next to the other - check if left
            if (p1.x>p2.x) {
                if (p1.getLeft() <= p2.getRight()) {
                    return true;
                }
            } else {
                if (p2.getLeft() <= p1.getRight()) {
                    return true;
                }
            }
        }
        Plus bottom = p1.y > p2.y ? p1 : p2;
        Plus top = p1.y > p2.y ? p2 : p1;
        Plus left = p1.x < p2.x ? p1 : p2;
        Plus right = p1.x < p2.x ? p2 : p1;

        int t1 = bottom.getTop();
        int t2 = bottom.getBottom();
        int t3 = bottom.getLeft();
        int t4 = bottom.getRight();
        int t5 = top.getTop();
        int t6 = top.getBottom();
        int t7 = top.getLeft();
        int t8 = top.getRight();

        if (bottom.getTop() <= top.y && (bottom.x >= top.getLeft() && bottom.x <= top.getRight())) {
            return true;
        }

        if (right.getLeft() <= left.x && (right.y >= left.getTop() && right.y <= left.getBottom())) {
            return true;
        }

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
