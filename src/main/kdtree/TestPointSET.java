import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestPointSET {

    /**
     * @param args
     * @todo - remove before submission
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input/collinear/input40.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdOut.println("\n");
        FastCollinearPoints collinearFast = new FastCollinearPoints(points);
        System.out.println("Size: " + collinearFast.numberOfSegments());
        for (LineSegment segmentFast : collinearFast.segments()) {
            StdOut.println(segmentFast);
//            segment.draw();
        }
//        StdDraw.show();
    }
}
