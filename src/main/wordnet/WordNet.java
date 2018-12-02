import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;


public class WordNet {
    private final Digraph G;
    private final SAP sap;

    private final ST<String, Integer> nouns;
    private final ST<Integer, String> synsets;


    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.IllegalArgumentException();
        }

        this.nouns = new ST<>();
        this.synsets = new ST<>();

        In in = new In(synsets);
        int size = 0;
        while (in.hasNextLine()) {
            size++;
            String[] row = in.readLine().split(",");
            int key = Integer.parseInt(row[0]);

            this.synsets.put(key, row[1]);

            String[] words = row[1].split(" ");

            for (String word : words) {

                this.nouns.put(word, key);
            }
        }

        this.G = new Digraph(size);
        In csvIn = new In(hypernyms);
        while (csvIn.hasNextLine()) {
            String[] hyps = csvIn.readLine().split(",");
            int source = Integer.parseInt(hyps[0]);
            for (int j = 1; j < hyps.length; j++) {
                int target = Integer.parseInt(hyps[j]);
                validateVertex(source);
                validateVertex(target);
                this.G.addEdge(source, target);
            }
        }
        sap = new SAP(G);
    }

    private void validateVertex(int s) {
        if (s < 0 || s >= this.G.V()) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public Iterable<String> nouns() {
        return nouns.keys();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return nouns.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if (!nouns.contains(nounA) || !nouns.contains(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        int ancestorId = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        return this.synsets.get(ancestorId);
    }

    public static void main(String[] args) {
        // do unit testing of this class
    }
}