import java.util.ArrayList;

public class WordNet {

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.IllegalArgumentException();
            //also should be: the input to the constructor does not correspond to a rooted DAG.
        }
    }

    public Iterable<String> nouns() {
        // returns all WordNet nouns
        return new ArrayList<String>();
    }

    public boolean isNoun(String word) {
        // is the word a WordNet noun?
        return true;
    }

    public int distance(String nounA, String nounB) {
        // distance between nounA and nounB (defined below)
        return 0;
    }

    public String sap(String nounA, String nounB) {
        // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
        // in a shortest ancestral path (defined below)
        // throw an exception if nounA or nounB not a WordNet noun.
        return "";
    }

    public static void main(String[] args) {
        // do unit testing of this class
    }

}