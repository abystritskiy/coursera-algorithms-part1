public class Suffix implements Comparable<Suffix> {
    private String str;
    private int shift;

    public Suffix(String str, int shift) {
        this.str = str;
        this.shift = shift;
    }
    private char getCharAt(int position) {
        return str.charAt(((position + shift) % str.length()));
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

    public char getT() {
        return str.charAt(((str.length()-1 + shift) % str.length()));
    }
}