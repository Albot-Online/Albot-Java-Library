package albot.grid_based;

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
        this.grid = gridBoard.grid.clone();
    }


    public String serialize() {
        StringBuilder boardString = new StringBuilder();

        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++)
                boardString.append(grid[x][y]).append(" ");

        return boardString.substring(0, boardString.length() - 1);
    }

    // Util
    public void iterateBoard(BiConsumer<Integer, Integer> cellFunc) {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++)
                cellFunc.accept(x, y);
    }

    public void iterateBoard(BiConsumer<Integer, Integer> cellFunc, Consumer<Integer> rowFunc) {
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

    public void printBoard() {
        System.out.println(this.toString());
    }

}
