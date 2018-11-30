import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;


import java.util.HashMap;
import java.util.Map;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.G = G;
    }

    public int length(int v, int w) {
        // length of shortest ancestral path between v and w; -1 if no such path
        return 0;
    }

    public int ancestor(int v, int w) {
        BreadthFirstDirected bfsV = new BreadthFirstDirected(G, v);
        BreadthFirstDirected bfsW = new BreadthFirstDirected(G, w);
        HashMap<Integer, Integer> distancesV = bfsV.getReachableVertices();
        HashMap<Integer, Integer> distancesW = bfsW.getReachableVertices();

        for (Map.Entry<Integer, Integer> vert: distancesV.entrySet()) {
            StdOut.printf("%s => %s\n", vert.getKey(), vert.getValue());
        }
        StdOut.println("===========");
        for (Map.Entry<Integer, Integer> vert: distancesW.entrySet()) {
            StdOut.printf("%s => %s\n", vert.getKey(), vert.getValue());
        }
        return 0;


        // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return  0;
        // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return 0;
        // a common ancestor that participates in shortest ancestral path; -1 if no such path
    }

    public static void main(String[] args) {
        In in = new In("input/wordnet/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int x = sap.ancestor(3, 10);
    }

    protected class BreadthFirstDirected {
        private boolean[] marked;  // marked[v] = is there an s->v path?
        private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
        private int[] distTo;      // distTo[v] = length of shortest s->v path
        private HashMap<Integer, Integer> reachable =  new HashMap<>();

        public BreadthFirstDirected(Digraph G, int s) {
            marked = new boolean[G.V()];
            distTo = new int[G.V()];
            edgeTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
                distTo[v] = Integer.MAX_VALUE;
            validateVertex(s);
            bfs(G, s);
        }

        private void validateVertex(int v) {
            int V = marked.length;
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }

        }

        // BFS from single source
        private void bfs(Digraph G, int s) {
            Queue<Integer> q = new Queue<>();
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        reachable.put(w, distTo[w]);
                        q.enqueue(w);
                    }
                }
            }
        }

        public HashMap<Integer, Integer> getReachableVertices() {
            return reachable;
        }


    }
}