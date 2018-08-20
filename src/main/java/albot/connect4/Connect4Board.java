package albot.connect4;

import albot.grid_based.GridBoard;
import static albot.connect4.Connect4Constants.*;

public class Connect4Board extends GridBoard {

    public Connect4Board(String input) {
        super(Fields.BOARD_WIDTH, Fields.BOARD_HEIGHT, input);
    }

    public Connect4Board(int[][] grid) {
        super(Fields.BOARD_WIDTH, Fields.BOARD_HEIGHT, grid);
    }

    public Connect4Board(Connect4Board b) {
        super(Fields.BOARD_WIDTH, Fields.BOARD_HEIGHT, b.grid);
    }

    public Connect4Board(GridBoard gb) {
        super(Fields.BOARD_WIDTH, Fields.BOARD_HEIGHT, gb.grid);
    }
}
