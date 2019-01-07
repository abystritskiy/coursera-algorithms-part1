import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" MoveToFront - < W:\stuff\Java\percolation\input\burrows\abra.txt
https://theasciicode.com.ar/

MAC:
cd /Users/o9834/work/Java/coursera-algorithms-part1/src/main/burrows
javac -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar MoveToFront.java
java -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar  MoveToFront  - < /Users/o9834/work/Java/coursera-algorithms-part1/input/burrows/abra.txt | java -cp "/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar" edu.princeton.cs.algs4.HexDump 16

java -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar  MoveToFront  - < /Users/o9834/work/Java/coursera-algorithms-part1/input/burrows/abra.txt | java -cp "/Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar" MoveToFront +
 */

public class MoveToFront {
    private static byte[] charTable;
    private static HashMap<Byte, Integer> charMap;
    private final static int RADIX = 256;

    public static void encode() {
        ArrayList<Integer> out = new ArrayList<>();

        charTable = new byte[RADIX];
        charMap = new HashMap<>();

        for (int i=0; i<RADIX; i++) {
            charTable[i] = (byte) (i);
            charMap.put((byte) (i), i) ;
        }


        while (!BinaryStdIn.isEmpty()) {
            byte c = BinaryStdIn.readByte();
            int position = charMap.get(c);
            out.add(position);
            rebuildMap(position);
        }

        for (Integer chr: out) {
            BinaryStdOut.write(chr, Byte.SIZE);
        }

        BinaryStdOut.close();
    }

    public static void decode() {
        System.out.println("decode!");
        // apply move-to-front decoding, reading from standard input and writing to standard output
        ArrayList<Character> out = new ArrayList<>();

        charTable = new byte[RADIX];
        charMap = new HashMap<>();

        for (int i=0; i<RADIX; i++) {
            charTable[i] = (byte) (i);
            charMap.put((byte) (i), i) ;
        }
//        byte[] seq = new byte[] {41, 42, 52, 02, 44, 01, 45, 01, 04, 04, 02, 26};
        while (!BinaryStdIn.isEmpty()) {
            byte c = BinaryStdIn.readByte();
            int position = charMap.get(c);
            out.add((char) position);
            rebuildMap(position);
        }

        for (Character chr: out) {
            System.out.print(chr);
        }

        BinaryStdOut.close();
    }


    private static void printMap() {
        for (Byte j: charTable) {
            System.out.print(j + " ");
        }
        System.out.println("\n");
    }


    private static void rebuildMap(int pos) {
        byte found = charTable[pos];
        for (int i = pos-1; i >= 0; i--) {
            charTable[i+1] = charTable[i];
            charMap.put(charTable[i+1], i+1);
        }

        charTable[0] = found;
        charMap.put(found, 0);
    }


    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("-")) {
                encode();
            } else {
                decode();
            }
        }
    }
}
