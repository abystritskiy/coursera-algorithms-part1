import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> set;
    private Draw draw = new Draw();

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.size() == 0;
    }

    public int size() {
        return set.size();
    }


    public void insert(Point2D p) {
        set.add(p);
    }

    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            draw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> range = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                range.add(p);
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        Double dist = null;
        Point2D nearest = null;
        for (Point2D pSet : set) {
            Double distToPoint = p.distanceTo(pSet);
            if (dist == null || distToPoint < dist) {
                dist = distToPoint;
                nearest = pSet;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
