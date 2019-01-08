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
            suffixes[i] = new Suffix(str, i);
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

        Suffix needle = new Suffix(str, i);
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
        System.out.println(sfa.index(2)); // 3
    }
}
