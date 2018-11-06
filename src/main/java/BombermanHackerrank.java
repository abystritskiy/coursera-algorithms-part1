import java.util.*;
import java.io.*;

public class BombermanHackerrank {
    protected static final char BOMB = 'O';
    protected static final char EMPTY = '.';

    protected static char[][] gridChar;
    protected static int[][] timesGrid;

    protected static int time = 1;

    // Complete the bomberMan function below.
    static String[] bomberMan(int n, String[] grid) {

        gridChar = convertToCharArray(grid);

        setInitialEmpty(gridChar);
        setInitialBombs(gridChar);

        System.out.println("Grid after 1");
        drawGrid(gridChar);

        if (n%2 == 0) {
            for (int i = 0; i<gridChar.length; i++) {
                for (int j = 0; j<gridChar[i].length; j++) {
                    gridChar[i][j] = BOMB;
                }
            }
            return convertToStringArray(gridChar);
        }

        time++;
        while (time <= n) {
            List<ArrayList<Integer>> bombs = getCellsToExplode(time);

            for (ArrayList<Integer> cell: bombs) {
                int x0 = cell.get(0);
                int y0 = cell.get(1);
                if (gridChar[y0][x0] == BOMB) {
                    emptyCell(x0, y0);

                    if (y0 > 0) {
                        if (timesGrid[y0-1][x0] != time) {
                            emptyCell(x0, y0-1);
                        }
                    }

                    if (y0+1 < gridChar.length) {
                        if (timesGrid[y0+1][x0] != time) {
                            emptyCell(x0, y0+1);
                        }
                    }

                    if (x0 > 0) {
                        if (timesGrid[y0][x0-1] != time) {
                            emptyCell(x0-1, y0);
                        }
                    }

                    if (x0+1 < gridChar[y0].length) {
                        if (timesGrid[y0][x0+1] != time) {
                            emptyCell(x0+1, y0);
                        }
                    }
                }
            }

            if (time%2 == 0) {
                int explosionTime = time + 3;
                for (ArrayList<Integer> empty: emptyList) {
                    int y1 = empty.get(1);
                    int x1 = empty.get(0);
                    setCellsToExplode(explosionTime, x1, y1);
                    gridChar[y1][x1] = BOMB;
                }
                emptyList.clear();
            }

            System.out.println("Grid after " + time);
            drawGrid(gridChar);

            time++;
        }
        return convertToStringArray(gridChar);
    }

    private static void emptyCell(int x, int y) {

        ArrayList<Integer> emptyCell = new ArrayList<>();
        emptyCell.add(x);
        emptyCell.add(y);
        emptyList.add(emptyCell);
        gridChar[y][x] = EMPTY;
        timesGrid[y][x] = 0;
        drawGrid(gridChar);
    }

    private static void setInitialEmpty(char[][] gridChar) {
        timesGrid = new int[gridChar.length][];
        for (int i = 0; i < gridChar.length; i++) {
            timesGrid[i] = new int[gridChar[i].length];
            for (int j=0; j < gridChar[i].length; j++) {
                if (gridChar[i][j] == EMPTY) {
                    ArrayList<Integer> emptyCell = new ArrayList<Integer>();
                    emptyCell.add(j);
                    emptyCell.add(i);
                    emptyList.add(emptyCell);
                    timesGrid[i][j] = 0;
                }
            }
        }
    }

    private static void setInitialBombs(char[][] gridChar) {
        int explosionTime = 3;
        for (int i = 0; i < gridChar.length; i++) {
            for (int j=0; j < gridChar[i].length; j++) {
                if (gridChar[i][j] == BOMB) {
                    setCellsToExplode(explosionTime, j, i);
                }
            }
        }
    }

    public static char[][] convertToCharArray(String[] grid){
        char[][] gridChar = new char [grid.length][];
        for (int i = 0; i < grid.length; i++) {
            gridChar[i] = grid[i].toCharArray();
        }
        return gridChar;
    }

    public static String[] convertToStringArray(char[][] gridChar){
        String[] grid = new String[gridChar.length];
        for (int i = 0; i < gridChar.length; i++) {
            grid[i] = new String(gridChar[i]);
        }
        return grid;
    }

    private static List<ArrayList<Integer>> emptyList = new ArrayList<>();
    private static Map<Integer,List<ArrayList<Integer>>> bombsMap = new HashMap<>();

    private static List<ArrayList<Integer>> getCellsToExplode(int time) {
        List<ArrayList<Integer>> cells = bombsMap.getOrDefault(time, new ArrayList<>());
        bombsMap.remove(time);
        return cells;
    }

    private static void setCellsToExplode(int time, int x, int y) {
        List<ArrayList<Integer>> cells = bombsMap.getOrDefault(time,new ArrayList<>());

        ArrayList<Integer> cell = new ArrayList();
        cell.add(x);
        cell.add(y);

        cells.add(cell);
        bombsMap.put(time, cells);
        timesGrid[y][x] = time;
        drawGrid(gridChar);
    }

    public static void drawGrid(char[][] grid) {
        String[] gridString = convertToStringArray(grid);
        try {
            drawGrid(gridString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawGrid (String[] grid) throws IOException  {


        String fileName = "bomberman.log";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write("Time: " + time +"\n\n");
        for (int i = 0; i < grid.length; i++) {
            writer.write(grid[i] + "\n");
        }
        writer.write("\n\n");
        writer.close();
    }


    public static void main(String[] args) {

        /*
        int n = 3;

        String[] grid = new String[] {
                ".......",
                "....O..",
                ".......",
                "OO.....",
                "OO....."
        };
        */

        int n = 5;

        String[] grid = new String[] {
            ".......",
            "...O.O.",
            "....O..",
            "..O....",
            "OO...OO",
            "OO.O..."
        };

            /*

            .......
            ...O.O.
            ...OO..
            ..OOOO.
            OOOOOOO
            OOOOOOO

            */


        String[] result = bomberMan(n, grid);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
