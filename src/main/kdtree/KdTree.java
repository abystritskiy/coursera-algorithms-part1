import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import java.util.ArrayList;

public class KdTree {
    private Draw draw;
    private Node root;
    private int size;

    public KdTree() {
        size = 0;
        draw = new Draw();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p, "x");
        } else {
            Node next = root;
            while (next != null) {
                if (next.axis == "x") {
                    if (p.x() > next.point.x()) {
                        if (next.right == null) {
                            insertRight(next, p);
                            return;
                        } else {
                            next = next.right;
                        }
                    } else {
                        if (next.left == null) {
                            insertLeft(next, p);
                            return;
                        } else {
                            next = next.left;
                        }
                    }
                } else {
                    if (p.y() > next.point.y()) {
                        if (next.right == null) {
                            insertRight(next, p);
                            return;
                        } else {
                            next = next.right;
                        }
                    } else {
                        if (next.left == null) {
                            insertLeft(next, p);
                            return;
                        } else {
                            next = next.left;
                        }
                    }
                }
            }
        }
        size++;
    }

    private void insertRight(Node root, Point2D child) {
        String newPointAxis = (root.axis == "x" ? "y" : "x");
        Node newPoint = new Node(child, newPointAxis);
        root.right = newPoint;
    }

    private void insertLeft(Node root, Point2D child) {
        String newPointAxis = (root.axis == "x" ? "y" : "x");
        Node newPoint = new Node(child, newPointAxis);
        root.left = newPoint;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node next = root;

        while (next != null) {
            if (next.point.equals(p)) {
                return true;
            }
            if (next.axis == "x") {
                if (p.x() > next.point.x()) {
                    next = next.right;
                } else {
                    next = next.left;
                }
            } else {
                if (p.y() > next.point.y()) {
                    next = next.right;
                } else {
                    next = next.left;
                }
            }
        }

        return false;
    }

    public void draw() {
        Stack<Node> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            draw.point(node.point.x(), node.point.y());
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> range = new ArrayList<>();

        Stack<Node> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            Point2D point = node.point;
            if (rect.contains(point)) {
                range.add(point);
            }
            if (node.axis == "x") {
                if (rect.xmin() <= point.x() && node.left != null) {
                    stack.push(node.left);
                } else if (rect.xmax() >= point.x() && node.right != null) {
                    stack.push(node.right);
                }
            } else {
                if (rect.ymin() <= point.y() && node.left != null) {
                    stack.push(node.left);
                } else if (rect.ymax() >= point.y() && node.right != null) {
                    stack.push(node.right);
                }
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        ResizingArrayQueue<Node> queue = new ResizingArrayQueue<>();

        Node closestNode = root;
        double minDistance = p.distanceTo(root.point);
        queue.enqueue(closestNode);

        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            Node leftNode = node.left;
            Node rightNode = node.right;

            if (leftNode != null &&  p.distanceTo(leftNode.point) <= minDistance) {
                closestNode = leftNode;
                minDistance = p.distanceTo(leftNode.point);
                queue.enqueue(leftNode);
            } else if (leftNode != null &&  p.distanceTo(leftNode.point) < minDistance) {
                closestNode = rightNode;
                minDistance = p.distanceTo(rightNode.point);
                queue.enqueue(leftNode);
            }
        }
        return closestNode.point;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }


    private class Node {
        Point2D point;
        String axis;

        Node left;
        Node right;

        public Node(Point2D point, String axis) {
            this.point = point;
            this.axis = axis;
            this.left = this.right = null;
        }

    }

}
