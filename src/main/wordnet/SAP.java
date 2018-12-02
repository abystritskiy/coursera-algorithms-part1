import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }

    public int length(int v, int w) {
        if (v < 0 || v > G.V() || w < 0 || w > G.V()) {
            throw new java.lang.IllegalArgumentException();
        }

        BreadthFirstDirectedPaths bfsdV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsdW = new BreadthFirstDirectedPaths(G, w);
        Ancestor ancestor = getAncestor(bfsdV, bfsdW);
        return ancestor.len;
    }
    public int ancestor(int v, int w) {
        if (v < 0 || v > G.V() || w < 0 || w > G.V()) {
            throw new java.lang.IllegalArgumentException();
        }

        BreadthFirstDirectedPaths bfsdV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsdW = new BreadthFirstDirectedPaths(G, w);
        Ancestor ancestor = getAncestor(bfsdV, bfsdW);
        return ancestor.ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int a : v) {
            if (a < 0 || a >= G.V()) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        for (int b : w) {
            if (b < 0 || b >= G.V()) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        BreadthFirstDirectedPaths bfsdV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsdW = new BreadthFirstDirectedPaths(G, w);

        Ancestor ancestor = getAncestor(bfsdV, bfsdW);
        return ancestor.len;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int a : v) {
            if (a < 0 || a >= G.V()) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        for (int b : w) {
            if (b < 0 || b >= G.V()) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        BreadthFirstDirectedPaths bfsdV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsdW = new BreadthFirstDirectedPaths(G, w);
        Ancestor ancestor = getAncestor(bfsdV, bfsdW);
        return ancestor.ancestor;
    }


    private Ancestor getAncestor(BreadthFirstDirectedPaths bfsdV, BreadthFirstDirectedPaths bfsdW) {
        int shortest = -1;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (bfsdV.hasPathTo(i) && bfsdW.hasPathTo(i)) {
                if (shortest == -1 || bfsdV.distTo(i) + bfsdW.distTo(i) < shortest) {
                    shortest = bfsdV.distTo(i) + bfsdW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return new Ancestor(shortest, ancestor);
    }


    public static void main(String[] args) {
        In in = new In("input/wordnet/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int x = sap.length(10, 3);
        System.out.println(x);
    }

    private class Ancestor {
        private final int len;
        private final int ancestor;
        public Ancestor(int len, int ancestor) {
            this.len = len;
            this.ancestor = ancestor;
        }
    }
}
