import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<ArrayList<Point>> segmentsList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        if (pointsCopy.length > 1) {
            for (int i = 1; i < pointsCopy.length; i++) {
                if (points[i] == null || points[i - 1] == null || pointsCopy[i].slopeTo(pointsCopy[i - 1]) == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        for (Point point : pointsCopy) {
            ArrayList<Point> listOfPoints = new ArrayList<>(Arrays.asList(pointsCopy));
            listOfPoints.sort(point.slopeOrder());

            double prevSlope = Double.NEGATIVE_INFINITY;

            Point minPoint = point;
            Point maxPoint = point;
            int pointsNum = 1;

            for (int j = 0; j < listOfPoints.size(); j++) {
                Point subPoint = listOfPoints.get(j);
                if (subPoint == point) continue;

                double slope = point.slopeTo(subPoint);

                if (slopesEquals(slope, prevSlope)) {
//                if (prevSlope == Double.NEGATIVE_INFINITY || Math.abs(slope - prevSlope) < 0.000000001) {

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

                if (j == pointsCopy.length - 1 && pointsNum >= 3) {
                    addSegment(minPoint, maxPoint);
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


    private boolean slopesEquals(double slope, double prevSlope) {
        return prevSlope == Double.NEGATIVE_INFINITY || Math.abs(slope - prevSlope) < 0.0001 ||
                (slope == Double.POSITIVE_INFINITY && prevSlope == Double.POSITIVE_INFINITY);
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