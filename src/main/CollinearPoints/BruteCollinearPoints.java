import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.HashMap;

public class BruteCollinearPoints {
    protected LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        // p→q→r→s
        // slope(p,q) == slope(p,r) == slope(p,s)
        if (points == null) throw new java.lang.IllegalArgumentException();
        HashMap<String, LineSegment> segmentsMap = new HashMap<>();


        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            Point p = points[i];
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                Point q = points[j];
                double pq = p.slopeTo(q);
                for (int k = 0; k < points.length; k++) {
                    if (k == j || k == i) continue;
                    Point r = points[k];
                    double pr = p.slopeTo(r);
                    for (int l = 0; l < points.length; l++) {
                        if (l == k || l == j || k == i) continue;
                        Point s = points[l];
                        double ps = p.slopeTo(s);
                        if (pq != pr && pq != ps) continue;

                        //slope must start with lowest - ends with higest
                        Point[] segmentPoints = new Point[] {p, q, r, s};

                        Point min = segmentPoints[0];
                        Point max = segmentPoints[0];
                        for (Point point: points) {
                            if (point.compareTo(min) < 0) {
                                min = point;
                            } else if (point.compareTo(max) > 0) {
                                max = point;
                            }
                        }
                        LineSegment segment = new LineSegment(min, max);
                        if (!segmentsMap.containsKey(segment.toString())) {
                            segmentsMap.put(segment.toString(), segment);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[segmentsMap.size()];
        int x = 0;
        for (LineSegment lineSegment: segmentsMap.values()) {
            segments[x++] = lineSegment;
        }
    }


    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
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

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}