import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.HashMap;

/*
java -classpath "W:\stuff\Java\percolation\out\production\percolation;W:\stuff\Java\percolation\lib\algs4.jar" MoveToFront - < W:\stuff\Java\percolation\input\burrows\abra.txt
https://theasciicode.com.ar/
 */

public class MoveToFront {
    private static char[] charTable;
    private static HashMap<Character, Integer> charMap = new HashMap<>();
    private final static int RADIX = 26;

    public static void encode() {
        ArrayList<Integer> out = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            byte c = BinaryStdIn.readByte();
            int position = charMap.get((char) c);
            out.add(position);
            rebuildMap(position);
            printMap();
//            HexDump.main(new String[] {Byte.toString(c)});
        }
//        System.out.println(out);
//        printMap();
    }

    private static void printMap() {
        for (Character j: charTable) {
            System.out.print(j);
        }
        System.out.println("\n");
    }
    private static void rebuildMap(int pos) {
        char found = charTable[pos];
        for (int i = pos-1; i >= 0; i--) {
            charTable[i+1] = charTable[i];
            charMap.put(charTable[i+1], i+1);
        }

        charTable[0] = found;
        charMap.put(found, 0);
    }

    public static void decode() {
        // apply move-to-front decoding, reading from standard input and writing to standard output
    }

    public static void mainOld(String[] args) {
        for (int i=0; i<256; i++) {
            charTable[i] = (char) i;
            charMap.put((char) i, i) ;
        }

        if (args.length > 0) {
            if (args[0].equals("-")) {
                encode();
            } else {
                decode();
            }
        }
    }

    public static void main(String[] args) {
        charTable = new char[27];

        charTable[0] = (char) 33;
        charMap.put((char) 33, 0) ;

//        for (int i=0; i<RADIX; i++) {
        for (int i=1; i<27; i++) {
            charTable[i] = (char) (i+64);
            charMap.put((char) (i+64), i) ;
        }
        if (args.length > 0) {
            if (args[0].equals("-")) {
                encode();
            } else {
                decode();
            }
        }
    }
}
