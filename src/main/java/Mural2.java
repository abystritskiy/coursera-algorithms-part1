import edu.princeton.cs.algs4.In;
import java.io.IOException;
import java.util.Scanner;

public class Mural2 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        In in = new In("input/ks/sample21.txt");      // input file

        int T = in.readInt();

        for (int i = 0; i<T; i++) {
            int cells  = in.readInt();
            String score = String.valueOf(in.readInt());

            int maxLeft = 0;
            int j = 1;
            int k = 1;
            while (j+k < score.length()) {
                System.out.println(score.charAt(j-1));
                maxLeft += (int) score.charAt(j-1);
                j++;
                j++;
            }

            System.out.println("Case #" + (i+1) +": "+ maxLeft);
        }


        scanner.close();
    }
}