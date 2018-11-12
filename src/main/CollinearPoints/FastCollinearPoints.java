import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
public class FastCollinearPoints {
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new java.lang.IllegalArgumentException();
        ArrayList<LineSegment> segmentsList = new ArrayList<>();

         for (int i = 0; i<points.length; i++) {
            Point point0 = points[i];
         }


    }
    public int numberOfSegments() {
        // the number of line segments
        return  0;
    }
    public LineSegment[] segments() {
        // the line segments
        return new LineSegment[] {};
    }

    /**
     * @todo - remove before submission
     * @param args
     */
    public static void main (String[] args) {
        // read the n points from a file
        In in = new In("input/collinear/input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        /*// print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();*/
    }
}