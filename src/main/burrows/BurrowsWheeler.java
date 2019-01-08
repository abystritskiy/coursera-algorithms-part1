import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;

public class BurrowsWheeler {
    public static void transform() {
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar());
        }
        Suffix[] suffixes = new Suffix[sb.length()];
        for (int i = 0; i < sb.length(); i++) {
            suffixes[i] = new Suffix(sb.toString(), i);
        }
        Arrays.sort(suffixes);

        CircularSuffixArray csa = new CircularSuffixArray(sb.toString());
        BinaryStdOut.write(csa.index(0), Integer.SIZE);
        for (Suffix sfx: suffixes) {
            BinaryStdOut.write(sfx.getT(), Byte.SIZE);
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {

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
