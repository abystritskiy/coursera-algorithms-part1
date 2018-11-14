import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<ArrayList<Point>> segmentsList = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        if (pointsCopy.length > 1) {
            for (int i = 1; i < pointsCopy.length; i++) {
                if (points[i] == null || points[i-1] == null || pointsCopy[i].slopeTo(pointsCopy[i - 1]) == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            if (pointsCopy[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            Point p = pointsCopy[i];
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                if (j == i) continue;
                Point q = pointsCopy[j];
                double pq = p.slopeTo(q);
                for (int k = j; k < pointsCopy.length - 1; k++) {
                    if (k == j || k == i) continue;
                    Point r = pointsCopy[k];
                    double pr = p.slopeTo(r);

                    if (Math.abs(pq - pr) < 0.0001) continue;

                    for (int m = k + 1; m < pointsCopy.length; m++) {
                        if (m == k || m == j || m == i) continue;
                        Point s = pointsCopy[m];
                        double ps = p.slopeTo(s);

                        if (Math.abs(pq-ps) < 0.0001) continue;

                        Point[] segmentPoints = new Point[]{p, q, r, s};

                        Point min = segmentPoints[0];
                        Point max = segmentPoints[0];
                        for (Point point : segmentPoints) {
                            if (point.compareTo(min) < 0) {
                                min = point;
                            } else if (point.compareTo(max) > 0) {
                                max = point;
                            }
                        }
                        addSegment(min, max);
                    }
                }
            }
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

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentsList.size()];
        for (int i = 0; i < segmentsList.size(); i++) {
            segments[i] = new LineSegment(segmentsList.get(i).get(0), segmentsList.get(i).get(1));
        }
        return segments.clone();
    }

}