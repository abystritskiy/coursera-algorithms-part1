import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    public static void transform() {
        String str = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(str);
        BinaryStdOut.write(csa.index(0), Integer.SIZE);

        for (int i = 0; i < str.length(); i++) {
            BinaryStdOut.write(str.charAt((csa.index(i) + str.length() - 1) % str.length()));
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String str = BinaryStdIn.readString();

        int n = str.length();
        int[] count = new int[R + 1], next = new int[n];

        for (int i = 0; i < n; i++) {
            count[str.charAt(i) + 1]++;
        }

        for (int i = 1; i < R + 1; i++) {
            count[i] += count[i - 1];
        }

        for (int i = 0; i < n; i++) {
            next[count[str.charAt(i)]++] = i;
        }

        for (int i = next[first], c = 0; c < n; i = next[i], c++) {
            BinaryStdOut.write(str.charAt(i));
        }

        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-")) {
                transform();
            } else {
                inverseTransform();
            }
        }
    }
}
