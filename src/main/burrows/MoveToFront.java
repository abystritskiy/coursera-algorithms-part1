/*
https://theasciicode.com.ar/

windows
java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" MoveToFront - < W:\stuff\Java\percolation\input\burrows\abra.txt | java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" edu.princeton.cs.algs4.HexDump 16
java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" MoveToFront - < W:\stuff\Java\percolation\input\burrows\abra.txt | java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" MoveToFront +


MAC:
cd /Users/o9834/work/Java/coursera-algorithms-part1/src/main/burrows
javac -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar MoveToFront.java
java -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar  MoveToFront  - < /Users/o9834/work/Java/coursera-algorithms-part1/input/burrows/abra.txt | java -cp "/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar" edu.princeton.cs.algs4.HexDump 16

java -classpath /Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar  MoveToFront  - < /Users/o9834/work/Java/coursera-algorithms-part1/input/burrows/abra.txt | java -cp "/Users/o9834/work/Java/coursera-algorithms-part1/out/production/coursera-algorithms-part1:/Users/o9834/work/Java/coursera-algorithms-part1/lib/algs4.jar" MoveToFront +
*/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.HashMap;


public class MoveToFront {
    private final static int RADIX = 256;
    private static int[] charTable;
    private static HashMap<Integer, Integer> charMap;

    public static void encode() {
        ArrayList<Integer> out = new ArrayList<>();

        charTable = new int[RADIX];
        charMap = new HashMap<>();

        for (int i = 0; i < RADIX; i++) {
            charTable[i] = i;
            charMap.put(i, i);
        }

        while (!BinaryStdIn.isEmpty()) {
            int c = (int) BinaryStdIn.readByte();
            int position = charMap.get(c);
            out.add(position);
            rebuildMapFor(position);
        }

        for (Integer chr : out) {
            BinaryStdOut.write(chr, Byte.SIZE);
        }

        BinaryStdOut.close();
    }

    public static void decode() {
        ArrayList<Character> out = new ArrayList<>();

        charTable = new int[RADIX];
        charMap = new HashMap<>();

        for (int i = 0; i < RADIX; i++) {
            charTable[i] = i;
            charMap.put(i, i);
        }

        while (!BinaryStdIn.isEmpty()) {
            byte b = BinaryStdIn.readByte();
            int c = (int) b;
            out.add((char) charTable[c]);
            rebuildMapFor(c);
        }

        for (Character chr : out) {
            BinaryStdOut.write(chr);
        }
        BinaryStdOut.close();
    }

    private static void printMap(int position) {
        for (int j : charTable) {
            System.out.print(j + " ");
        }
        System.out.println("\n");
    }

    private static void rebuildMapFor(int pos) {
        int found = charTable[pos];
        for (int i = pos - 1; i >= 0; i--) {
            charTable[i + 1] = charTable[i];
            charMap.put(charTable[i + 1], i + 1);
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
