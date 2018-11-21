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
        Palindrom palindrom = new Palindrom(s);
        String enlarged = palindrom.enlarge(k);
        return enlarged;
    }

    private static class Palindrom {
        private final String original;
        private final String palindromSimple;
        private int subsToPalindrome;
        private int numberOfNines;

        public Palindrom(String original)  {
            this.original = original;
            char[] sChar = original.toCharArray();

            int n = 0;
            int n9 = 0;

            int i = 0;
            int j = sChar.length - 1;

            while (i < j) {
                if (sChar[i] != sChar[j]) {

                    int left = Character.getNumericValue(sChar[i]);
                    int right = Character.getNumericValue(sChar[j]);
                    int maxOfLeftRight = Math.max(left, right);

                    if (maxOfLeftRight == 9) {
                        n9++;
                    }

                    sChar[i] = Character.forDigit(maxOfLeftRight, 10);
                    sChar[j] = Character.forDigit(maxOfLeftRight, 10);
                    n++;
                }
                i++;
                j--;
            }

            this.subsToPalindrome = n;
            this.numberOfNines = n9;
            this.palindromSimple = String.valueOf(sChar);
        }

        public String enlarge(int k) {
            if (k < subsToPalindrome) {
                return "-1";
            }
            int remainder = k - subsToPalindrome;

            char[] sChar = palindromSimple.toCharArray();
            char[] sCharOriginal = original.toCharArray();

            int i = 0;
            int j = sChar.length - 1;

            while (remainder > 0 && i <= j) {
                int left = Character.getNumericValue(sCharOriginal[i]);
                int right = Character.getNumericValue(sCharOriginal[j]);
                if (left != right && left != 9 && right != 9) {
                    sChar[i] = '9';
                    sChar[j] = '9';
                    remainder--;
                } else if (left == right && left != 9) {
                    if (remainder >= 2) {
                        sChar[i] = '9';
                        sChar[j] = '9';
                        remainder -= 2;
                    }
                }
                i++;
                j--;
            }
            if (remainder > 0) {
                sChar[(sChar.length + 1) / 2 - 1] = '9';
            }
            return String.valueOf(sChar);
        }
    }

    public static void main(String[] args) throws IOException {
        In in = new In("input/hr/richierich/input32.txt");
        String[] nk = in.readLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        String s = in.readLine();


        System.out.println(s);
        String result = highestValuePalindrome(s, n, k);
        System.out.println(result);

        Out out = new Out("input/hr/richierich/output32.txt");
        out.print(result);
    }
}
//java -cp out/production/coursera-algorithms-part1:lib/algs4.jar Permutation 3 < input/week2/distinct.txt