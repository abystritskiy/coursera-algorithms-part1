import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Test {

    /**
     * @param args
     * @todo - remove before submission
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input/collinear/test2b.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//            segment.draw();
        }
        StdOut.println("\n\n");
        FastCollinearPoints collinearFast = new FastCollinearPoints(points);
        System.out.println("Size: " + collinearFast.numberOfSegments());
        for (LineSegment segmentFast : collinearFast.segments()) {
            StdOut.println(segmentFast);
//            segment.draw();
        }
//        StdDraw.show();
    }
}
