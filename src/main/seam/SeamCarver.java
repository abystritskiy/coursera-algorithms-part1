import java.awt.Color;

import edu.princeton.cs.algs4.*;


public class SeamCarver {
    private static final double MAX_ENERGY = 1000;
    private Picture picture;
    private Graph graphH;
    private Graph graphV;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.picture = new Picture(picture);

        graphH = new Graph(height() * width() + 2);
        graphV = new Graph(height() * width() + 2);

        int firstID = width() * height();
        int lastID = width() * height() + 1;

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                int vertex = y * width() + x;
                double energy = energy(x, y);

                // graphV - vertically connected graph
                if (y == 0) {
                    graphV.addEdge(new Edge(firstID, vertex, energy));
                } else {
                    if (x > 0) {
                        int indexLeftV = (y - 1) * width() + x - 1;
                        graphV.addEdge(new Edge(indexLeftV, vertex, energy));
                    }
                    if (x < width() - 1) {
                        int indexRightV = (y - 1) * width() + x + 1;
                        graphV.addEdge(new Edge(indexRightV, vertex, energy));
                    }
                    int indexTopV = (y - 1) * width() + x;
                    graphV.addEdge(new Edge(indexTopV, vertex, energy));

                    if (y == height() - 1) {
                        graphV.addEdge(new Edge(vertex, lastID, MAX_ENERGY));
                    }
                }

                // graphH - horizontally connected graph
                if (x == 0) {
                    graphH.addEdge(new Edge(firstID, vertex, energy));
                } else {
                    if (y > 0) {
                        int indexTopH = (y - 1) * width() + x - 1;
                        graphH.addEdge(new Edge(indexTopH, vertex, energy));
                    }
                    if (y < height() - 1) {
                        int indexBottomH = (y + 1) * width() + x - 1;
                        graphH.addEdge(new Edge(indexBottomH, vertex, energy));
                    }

                    int indexLeftH = y * width() + x - 1;
                    graphH.addEdge(new Edge(indexLeftH, vertex, MAX_ENERGY));

                    if (x == width() - 1) {
                        graphH.addEdge(new Edge(vertex, lastID, MAX_ENERGY));
                    }
                }
            }
        }
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new java.lang.IllegalArgumentException();
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return MAX_ENERGY;
        }
        Color pL = picture.get(x - 1, y);
        Color pR = picture.get(x + 1, y);
        Color pT = picture.get(x, y - 1);
        Color pB = picture.get(x, y + 1);

        return Math.sqrt(
            Math.pow(pL.getRed() - pR.getRed(), 2) +
            Math.pow(pL.getGreen() - pR.getGreen(), 2) +
            Math.pow(pL.getBlue() - pR.getBlue(), 2) +
            Math.pow(pT.getRed() - pB.getRed(), 2) +
            Math.pow(pT.getGreen() - pB.getGreen(), 2) +
            Math.pow(pT.getBlue() - pB.getBlue(), 2)
        );
    }

    public int[] findHorizontalSeam() {
        int[] hSeam = new int[width()];
        if (width() == 1) {
            for (int i=0; i<width(); i++) {
                hSeam[i] = i;
            }
            return hSeam;
        }
        int firstID = width() * height();
        int lastID = width() * height() + 1;

        SP sp = new SP(graphH, firstID);
        int x = 0;
        for (Edge edge : sp.pathTo(lastID)) {
            int edgeId = edge.to();
            if (edgeId != firstID && edgeId != lastID) {
                int y = (edgeId - x) / width();
                hSeam[x] = y;
                x++;
            }

        }
        return hSeam;
    }

    public int[] findVerticalSeam() {

        int[] vSeam = new int[height()];
        if (height() == 1) {
            for (int i=0; i<height(); i++) {
                vSeam[i] = i;
            }
            return vSeam;
        }
        int firstID = width() * height();
        int lastID = width() * height() + 1;

        SP sp = new SP(graphV, firstID);

        int y = 0;
        for (Edge edge : sp.pathTo(lastID)) {
            int edgeId = edge.to();
            if (edgeId != firstID && edgeId != lastID) {
                int x = edgeId - y * width();
                vSeam[y] = x;
                y++;
            }
        }
        return vSeam;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (picture.height() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }

        Picture newPicture = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height() - 1; y++) {
                if (y < seam[x]) {
                    newPicture.set(x, y, picture.get(x, y));
                } else {
                    newPicture.set(x, y, picture.get(x, y + 1));
                }
            }
        }
        picture = newPicture;
    }

    public void removeVerticalSeam(int[] seam) {
        if (picture.height() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }

        Picture newPicture = new Picture(width() - 1, height());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height() - 1; y++) {
                if (x < seam[y]) {
                    newPicture.set(x, y, picture.get(x, y));
                } else {
                    newPicture.set(x, y, picture.get(x + 1, y));
                }
            }
        }
        picture = newPicture;
    }

    private class Graph {
        private final int V;
        private int E;
        private Bag<Edge>[] adj;

        public Graph(int V) {
            if (V < 0) {
                throw new IllegalArgumentException();
            }
            this.V = V;
            this.E = 0;
            adj = (Bag<Edge>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>();
            }
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }

        public void addEdge(Edge e) {
            int v = e.from();
            adj[v].add(e);
            E++;
        }

        public Iterable<Edge> edges() {
            Bag<Edge> list = new Bag<>();
            for (int v = 0; v < V; v++) {
                for (Edge e : adj(v)) {
                    list.add(e);
                }
            }
            return list;
        }

        public Iterable<Edge> adj(int v) {
            return adj[v];
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " " + E + "\n");
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (Edge e : adj[v]) {
                    s.append(e + "  ");
                }
                s.append("\n");
            }
            return s.toString();
        }
    }

    private class Edge implements Comparable<Edge> {
        private final int v, w;
        private final double weight;

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public int from() {
            return v;
        }

        public int to() {
            return w;
        }

        public int compareTo(Edge that) {
            if (this.weight < that.weight) {
                return -1;
            } else if (this.weight > that.weight) {
                return +1;
            } else {
                return 0;
            }
        }

        public double weight() {
            return weight;
        }

        public String toString() {
            return v + "->" + w + " " + String.format("%5.2f", weight);
        }
    }


    private class SP {
        private double[] distTo;
        private Edge[] edgeTo;
        private IndexMinPQ<Double> pq;

        public SP(Graph G, int s) {
            for (Edge e : G.edges()) {
                if (e.weight() < 0) {
                    throw new IllegalArgumentException();
                }
            }

            distTo = new double[G.V()];
            edgeTo = new Edge[G.V()];

            for (int v = 0; v < G.V(); v++) {
                distTo[v] = Double.POSITIVE_INFINITY;
            }

            distTo[s] = 0.0;

            pq = new IndexMinPQ<Double>(G.V());
            pq.insert(s, distTo[s]);
            while (!pq.isEmpty()) {
                int v = pq.delMin();
                for (Edge e : G.adj(v)) {
                    relax(e);
                }
            }
        }

        private void relax(Edge e) {
            int v = e.from(), w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }

        public double distTo(int v) {
            return distTo[v];
        }

        public boolean hasPathTo(int v) {
            return distTo[v] < Double.POSITIVE_INFINITY;
        }

        public Iterable<Edge> pathTo(int v) {
            if (!hasPathTo(v)) {
                return null;
            }
            ;
            Stack<Edge> path = new Stack<>();
            for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
                path.push(e);
            }
            return path;
        }
    }
}