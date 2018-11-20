import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class RichieRich {

    // Complete the highestValuePalindrome function below.
    static String highestValuePalindrome(String s, int n, int k) {


        int subs = 0;

        int REDIX=10;
        char[] sChrOrig = s.toCharArray();
        char[] sChr = new char[n];

        System.arraycopy(sChrOrig, 0, sChr, 0, n);

        Palindrom palindrom = new Palindrom(s);

        return String.valueOf(sChr);
    }

    protected static String convertToPalindrom(String S) {
        int n = 0;
        for (int i=0; i <= S.length()-1-i; i++) {
            if (S.charAt(i) != S.charAt(S.length()-1-i)) {
                n++;
            }
        }

        return "";
    }

    private static class Palindrom {
        private final String original;
        private final String palindrom;
        private int subsToPalindrome;
        private int numberOfNine;

        public Palindrom(String original)  {
            this.original = original;
            char[] sChar = original.toCharArray();

            int n = 0;
            int n9 = 0;

            int i = 0;
            int j = sChar.length - 1;


            while (i < j) {
                j = sChar.length - 1 - i;
                if (sChar[i] != sChar[j]) {

                    int left = Character.getNumericValue(sChar[i]);
                    int right = Character.getNumericValue(sChar[j]);
                    int maxOfIJ = Math.max(left, right);

                    if (maxOfIJ == 9) {
                        n9++;
                    }

                    sChar[i] = Character.forDigit(maxOfIJ, 10);
                    sChar[j] = Character.forDigit(maxOfIJ, 10);
                    n++;
                }
            }

            this.subsToPalindrome = n;
            this.numberOfNine = n9;
            this.palindrom = String.valueOf(sChar);
        }

    }


    public static void main(String[] args) throws IOException {
        In in = new In("input/hr/richierich/input32.txt");
        String[] nk = in.readLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        String s = in.readLine();

        String result = highestValuePalindrome(s, n, k);

        Out out = new Out("input/hr/richierich/output32.txt");
        out.print(result);
    }
}
//java -cp out/production/coursera-algorithms-part1:lib/algs4.jar Permutation 3 < input/week2/distinct.txt