import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

public class TestPointSET {

    /**
     * @param args
     * @todo - remove before submission
     */
    public static void main(String[] args) {
        String filename = "input/kdtree/input10.txt";//args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        System.out.println(kdtree.isEmpty());
        System.out.println(brute.isEmpty());
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
            System.out.println(kdtree.size());
            System.out.println(brute.size());
            System.out.println(kdtree.isEmpty());
            System.out.println(brute.isEmpty());
        }

    }
}
