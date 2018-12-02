public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outCast = "";
        int longestDistance = -1;
        for (int i = 0; i < nouns.length; i++) {
            int distance = -1;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordnet.distance(nouns[i], nouns[j]);
                if (distance > longestDistance) {
                    longestDistance = distance;
                    outCast = nouns[i];
                }
            }
        }
        return outCast;
    }

    public static void main(String[] args) {
        // see test client below

        WordNet wn = new WordNet("input/wordnet/synsets.txt", "input/wordnet/hypernyms.txt");
        Outcast out = new Outcast(wn);
        String res = out.outcast(new String[]{"horse", "zebra", "cat", "bear", "table"});
        System.out.println(res);
    }
}