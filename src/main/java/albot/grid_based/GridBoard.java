package albot.grid_based;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GridBoard {

    protected int WIDTH, HEIGHT;
    public int[][] grid;

    public GridBoard(int width, int height, String serializedGrid) {
        WIDTH = width;
        HEIGHT = height;
        String[] cells = serializedGrid.trim().split(" ");
        grid = new int[width][height];
        iterateBoard(
                (x, y) -> grid[x][y] = Integer.parseInt(cells[x + y * 7])
            );
    }

    // Deep copy
    public GridBoard(int width, int height, int[][] grid) {
        WIDTH = width;
        HEIGHT = height;
        this.grid = grid.clone();
    }

    public GridBoard(GridBoard gridBoard) {
        WIDTH = gridBoard.WIDTH;
        HEIGHT = gridBoard.HEIGHT;
        //this.grid = gridBoard.grid.clone();
        this.grid = deepCopy(gridBoard.grid);
    }

    private int[][] deepCopy(int[][] original) {
        if (original == null)
            return null;

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++)
            result[i] = Arrays.copyOf(original[i], original[i].length);

        return result;
    }

    /**
     * Gives the value of a cell. (0,0) is in the top left corner.
     * @param x Zero based x index
     * @param y Zero based y index
     * @return 1 if player, -1 if enemy, 0 if empty
     */
    public int getCell(int x, int y) {
        return grid[x][y];
    }

    static int[][] transpose(int[][] grid, int widthOriginal, int heightOriginal) {
        int[][] gridNew = new int[heightOriginal][widthOriginal];
        for (int y = 0; y < heightOriginal; y++)
            for (int x = 0; x < widthOriginal; x++)
                gridNew[y][x] = grid[x][y];
        return gridNew;
    }

    // Util
    private void iterateBoard(BiConsumer<Integer, Integer> cellFunc) {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++)
                cellFunc.accept(x, y);
    }

    private void iterateBoard(BiConsumer<Integer, Integer> cellFunc, Consumer<Integer> rowFunc) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++)
                cellFunc.accept(x, y);

            rowFunc.accept(y);
        }
    }

    // Debugging
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        iterateBoard(
            (Integer x, Integer y) -> boardString.append(grid[x][y]).append("\t"),
            (Integer y) ->  boardString.append("\n")
        );

        return boardString.toString();
    }

    /**
     * Prints the board to the console.
     * @param boardTitle Optional title for the printed board.
     */
    public void printBoard(String boardTitle) {
        System.out.println("* * * * * * " + boardTitle + "* * * * * *");
        printBoardInfo();
        System.out.println("* * * * * * * * * * * *");
    }

    /**
     * Prints the board to the console.
     */
    public void printBoard() {
        System.out.println("* * * * * * * * * * * *");
        printBoardInfo();
        System.out.println("* * * * * * * * * * * *");
    }
    private void printBoardInfo() {
        System.out.print(toString());
    }

}
