import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<ArrayList<Point>> segmentsList = new ArrayList<>();
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        for (Point point: points) {
            if (point == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        validatePoints(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            Point p = pointsCopy[i];
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {

                Point q = pointsCopy[j];
                double pq = p.slopeTo(q);

                for (int k = j+1; k < pointsCopy.length - 1; k++) {

                    Point r = pointsCopy[k];
                    double pr = p.slopeTo(r);

                    if (Double.compare(pq, pr) !=  0) continue;

                    for (int m = k + 1; m < pointsCopy.length; m++) {

                        Point s = pointsCopy[m];
                        double ps = p.slopeTo(s);

                        if (Double.compare(pq, ps) !=  0) continue;

                        Point[] segmentPoints = new Point[]{p, q, r, s};

                        Arrays.sort(segmentPoints);
                        addSegment(segmentPoints[0], segmentPoints[3]);
                    }
                }
            }
        }

        segments = new LineSegment[segmentsList.size()];
        for (int i = 0; i < segmentsList.size(); i++) {
            segments[i] = new LineSegment(segmentsList.get(i).get(0), segmentsList.get(i).get(1));
        }
    }

    private void addSegment(Point minPoint, Point maxPoint) {
        for (ArrayList<Point> el : segmentsList) {
            if (el.get(0) == minPoint && el.get(1) == maxPoint) {
                return;
            }
        }
        ArrayList<Point> segmentPoints = new ArrayList<>();
        segmentPoints.add(minPoint);
        segmentPoints.add(maxPoint);
        segmentsList.add(segmentPoints);
    }

    private void validatePoints(Point[] points) {
        if (points.length > 1) {
            for (int i = 1; i < points.length; i++) {
                if (points[i] == null || points[i-1] == null ||
                        points[i].slopeTo(points[i - 1]) == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[segmentsList.size()];
        System.arraycopy(segments, 0, ret, 0, segments.length);

        return ret;
    }
}