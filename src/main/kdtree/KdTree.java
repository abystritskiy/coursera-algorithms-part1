import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class KdTree {
    private Draw draw = new Draw();
    private TreeSet<Point2D> set;


    public KdTree() {
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {

    }

    public int size() {

    }


    public void insert(Point2D p) {

    }

    public boolean contains(Point2D p) {

    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {

    }

    public Point2D nearest(Point2D p) {

    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }

    protected Node findParent(Point2D p)
    {
        Node parent = null;
        Node next = new Node(new double[]{p.x(), p.y()}, 0);
        int axis;
        while (next != null) {
            axis = next.axis;
            parent = next;
            if (p.x() > next.position[axis]) {
                next = next.right;
            } else {
                next = next.left;
            }
        }
        return parent;
    }


    private class Node {
        int axis;
        int id;

        double[] position;
        boolean checked;
        boolean orientation;

        Node parent = null;
        Node left = null;
        Node right = null;

        public Node(double[] position, int axis) {
            this.position = new double[] {position[0], position[1]};
            this.axis = axis;
        }

    }

}
