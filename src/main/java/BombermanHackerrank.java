import java.util.*;

public class BombermanHackerrank {
    protected static final char BOMB = 'O';
    protected static final char EMPTY = '.';

    // Complete the bomberMan function below.
    static String[] bomberMan(int n, String[] grid) {
        int time = 1;
        char gridChar[][] = convertToCharArray(grid);
        setInitialEmpty(gridChar);
        setInitailBombs(gridChar);

        while (time < n) {
            List<ArrayList<Integer>> bombs = getCellsToExplode(time);

            for (ArrayList<Integer> cell: bombs) {
                int x0 = cell.get(0);
                int y0 = cell.get(1);
                if (gridChar[y0][x0] == BOMB) {
                    ArrayList<Integer> emptyCell0 = new ArrayList<Integer>();
                    emptyCell0.add(x0);
                    emptyCell0.add(y0);
                    emptyList.add(emptyCell0);
                    gridChar[y0][x0] = EMPTY;

                    if (y0 != 0) {
                        //top
                        ArrayList<Integer> emptyCellTop = new ArrayList<Integer>();
                        emptyCellTop.add(x0);
                        emptyCellTop.add(y0+1);
                        emptyList.add(emptyCellTop);
                        gridChar[y0-1][x0] = EMPTY;
                    }

                    if (y0 != gridChar.length - 1) {
                        //bottom
                        ArrayList<Integer> emptyCellBottom = new ArrayList<Integer>();
                        emptyCellBottom.add(x0);
                        emptyCellBottom.add(y0+1);
                        emptyList.add(emptyCellBottom);
                        gridChar[y0+1][x0] = EMPTY;
                    }

                    if (x0 != 0) {
                        //left
                        ArrayList<Integer> emptyCellLeft = new ArrayList<Integer>();
                        emptyCellLeft.add(x0-1);
                        emptyCellLeft.add(y0);
                        emptyList.add(emptyCellLeft);
                        gridChar[y0][x0-1] = EMPTY;
                    }

                    if (x0 != gridChar[y0].length - 1) {
                        //right
                        ArrayList<Integer> emptyCellRight = new ArrayList<Integer>();
                        emptyCellRight.add(x0+1);
                        emptyCellRight.add(y0);
                        emptyList.add(emptyCellRight);
                        gridChar[y0][x0+1] = EMPTY;
                    }
                }
            }

            if (time%2 == 0) {
                int explosionTime = time + 3;
                for (ArrayList<Integer> empty: emptyList) {
                    setCellsToExplode(explosionTime, empty.get(0), empty.get(1));
                    gridChar[empty.get(1)][empty.get(0)] = BOMB;
                }
                emptyList.clear();
            }
            time++;
        }

        return convertToStringArray(gridChar);
    }

    private static void setInitialEmpty(char[][] gridChar) {
        for (int i = 0; i < gridChar.length; i++) {
            for (int j=0; j < gridChar[i].length; j++) {
                if (gridChar[i][j] == EMPTY) {
                    ArrayList<Integer> emptyCell = new ArrayList<Integer>();
                    emptyCell.add(j);
                    emptyCell.add(i);
                    emptyList.add(emptyCell);
                }
            }
        }
    }

    private static void setInitailBombs(char[][] gridChar) {
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
        List<ArrayList<Integer>> cells = bombsMap.getOrDefault(
                time,
                new ArrayList<ArrayList<Integer>>()
        );
        bombsMap.remove(time);
        return cells;
    }

    private static void setCellsToExplode(int time, int x, int y) {
        List<ArrayList<Integer>> cells = bombsMap.getOrDefault(
                time,
                new ArrayList<ArrayList<Integer>>()
        );

        ArrayList<Integer> cell = new ArrayList();
        cell.add(x);
        cell.add(y);

        cells.add(cell);
        bombsMap.put(time, cells);
    }


    public static void main0(String[] args) {

        int n = 3;

        String[] grid = new String[] {
            ".......",
            "....O..",
            ".......",
            "OO.....",
            "OO....."
        };



        String[] result = bomberMan(n, grid);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    public static void main(String[] args) {

        int n = 5;



        String[] grid = new String[] {
            ".......",
            "...O.O.",
            "....O..",
            "..O....",
            "OO...OO",
            "OO.O..."
        };



        String[] result = bomberMan(n, grid);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
