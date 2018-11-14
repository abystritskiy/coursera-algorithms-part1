import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<ArrayList<Point>> segmentsList = new ArrayList<>();
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        checkNull(points);
        checkDuplicates(points);

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);

        for (Point point : pointsCopy) {
            Point[] sortedPoints = new Point[pointsCopy.length];
            System.arraycopy(pointsCopy, 0, sortedPoints, 0, sortedPoints.length);
            Arrays.sort(sortedPoints, point.slopeOrder());

            double prevSlope = Double.NEGATIVE_INFINITY;

            Point minPoint = point;
            Point maxPoint = point;
            int pointsNum = 1;

            for (int j = 1; j < sortedPoints.length; j++) {
                Point subPoint = sortedPoints[j];

                if (subPoint == point) continue;

                double slope = point.slopeTo(subPoint);

                if (Double.compare(slope, prevSlope) == 0) {
                    pointsNum++;

                    if (subPoint.compareTo(minPoint) < 0) {
                        minPoint = subPoint;
                    }

                    if (subPoint.compareTo(maxPoint) > 0) {
                        maxPoint = subPoint;
                    }

                } else {
                    if (pointsNum > 3) {
                        addSegment(minPoint, maxPoint);
                    }

                    if (point.compareTo(subPoint) < 0) {
                        minPoint = point;
                        maxPoint = subPoint;
                    } else {
                        minPoint = subPoint;
                        maxPoint = point;
                    }
                    pointsNum = 2;
                }
                prevSlope = slope;

                if (j == pointsCopy.length - 1 && pointsNum > 3) {
                    addSegment(minPoint, maxPoint);
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

    private void checkDuplicates(Point[] points) {
        if (points.length > 0) {
            Point[] pointsCopy = new Point[points.length];
            System.arraycopy(points, 0, pointsCopy, 0, points.length);
            Arrays.sort(pointsCopy);
            Point currentPoint = pointsCopy[0];
            for (int i = 1; i < pointsCopy.length; i++) {
                if (pointsCopy[i].compareTo(currentPoint) == 0) {
                    throw new IllegalArgumentException();
                } else {
                    currentPoint = pointsCopy[i];
                }
            }
        }
    }

    private void checkNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
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