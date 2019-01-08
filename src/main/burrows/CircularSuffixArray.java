import java.util.Arrays;

public class CircularSuffixArray {
    private String str;
    private Suffix[] suffixes;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.str = s;
        suffixes = new Suffix[length()];
        for (int i = 0; i < length(); i++) {
            suffixes[i] = new Suffix(i);
        }
        Arrays.sort(suffixes);
    }

    public int length() {
        return str.length();
    }

    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new java.lang.IllegalArgumentException();
        }

        Suffix needle = new Suffix(i);
        for (int j = 0; j < length(); j++) {
            Suffix sfx = suffixes[j];
            if (needle.equals(sfx)) {
                return j;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        CircularSuffixArray sfa = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(sfa.index(11)); // 3
    }

    private class Suffix implements Comparable<Suffix> {
        private String str = CircularSuffixArray.this.str;
        private int shift;

        private char getCharAt(int position) {
            return str.charAt(((position + shift) % str.length()));
        }

        private Suffix(int shift) {
            this.shift = shift;
        }

        public boolean equals(Suffix that) {
            return that.shift == this.shift;
        }

        public int compareTo(Suffix that) {
            for (int i = 0; i < str.length(); i++) {
                char thisChar = this.getCharAt(i);
                char thatChar = that.getCharAt(i);
                if (thisChar != thatChar) {
                    return thisChar > thatChar ? 1 : -1;
                }
            }
            return 0;
        }
    }
}
