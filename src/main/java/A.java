import edu.princeton.cs.algs4.In;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class A {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main2(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            String[] options = scanner.nextLine().split(" ");
            int N =  Integer.parseInt(options[0]);
            int P = Integer.parseInt(options[1]);

            BigInteger num = new BigInteger("2").pow(N);

            for (int j = 0; j < P; j++) {
                String pref = scanner.nextLine();
                int prefLen = pref.length();
                BigInteger excluded = new BigInteger("2").pow(N-prefLen);

                num = num.subtract(excluded);

            }
            if (num .compareTo(new BigInteger("0")) < 0) num = new BigInteger("0");

            bufferedWriter.write("Case #" + (i+1) +": " + num);
        }

        bufferedWriter.flush();
        bufferedWriter.close();

        scanner.close();
    }

    public static void main(String[] args) throws IOException {

        In in = new In("input/ks/sample11.txt");      // input file

        int T = Integer.parseInt(in.readLine());
        for (int i = 0; i < T; i++) {
            String[] options = in.readLine().split(" ");
            int N =  Integer.parseInt(options[0]);
            int P = Integer.parseInt(options[1]);

            BigInteger num = new BigInteger("2").pow(N);

            for (int j = 0; j < P; j++) {
                String pref = in.readLine();
                int prefLen = pref.length();
                BigInteger excluded = new BigInteger("2").pow(N-prefLen);

                num = num.subtract(excluded);

            }
            if (num .compareTo(new BigInteger("0")) < 0) num = new BigInteger("0");
            System.out.println("Case #" + (i+1) +": "+ num);
        }
        in.close();
    }
}