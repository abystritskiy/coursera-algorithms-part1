import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Mural {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            int N =  scanner.nextInt();
            String scores = scanner.nextLine();
            int res = 0;


            bufferedWriter.write("Case #" + (i+1) +": " + res);
        }

        bufferedWriter.flush();
        bufferedWriter.close();

        scanner.close();
    }
}