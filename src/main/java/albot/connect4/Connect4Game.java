package albot.connect4;

import albot.grid_based.GridBasedGame;
import albot.grid_based.GridBoard;

import java.util.function.Function;

import static albot.connect4.Connect4Constants.*;

/**
 * A high level Connect4 library which sets up the connection and provides basic logic.
 */
public class Connect4Game extends GridBasedGame {

    /**
     * Constructor, initializes library and connects to the client.
     */
    public Connect4Game() {
        super();
    }

    /**
     * Constructor, initializes library and connects to the client.
     * @param ip local ip to client
     * @param port local port to client
     */
    public Connect4Game(String ip, int port) {
        super(ip, port);
    }

    @Override
    protected void initGridDimensions() {
        this.width = Fields.BOARD_WIDTH;
        this.height = Fields.BOARD_HEIGHT;
    }

    /**
     * Blocking receive call for next board.
     * @return next board
     */
    public Connect4Board makeMoveGetNextBoard(int move) {
        makeMove(move);
        return getNextBoard();
    }

    /**
     * Receive the next board.
     * @return next board
     */
    @Override
    public Connect4Board getNextBoard() {
        GridBoard gb = super.getNextBoard();
        if (gameOver())
            return null;
        return new Connect4Board(gb);
    }

    /**
     * Simulates a move.
     * @param board board to simulate in
     * @param player player to make the move
     * @param move move to be played
     * @return the board which the move has been applied to.
     */
    public Connect4Board simulateMove(Connect4Board board, int player, int move) {
        GridBoard gridBoard = super.simulateMove(board, player, move);
        return new Connect4Board(gridBoard);
    }

    /**
     * Plays an entire game by making moves returned by the function provided.
     * @param decideMove a function which takes the current board as argument and returns a move to play
     * @param autoRestart whether you want the game to restart automatically after it has finished
     */
    public void playGame(Function<Connect4Board, Integer> decideMove, boolean autoRestart) {

        while (true) {
            Connect4Board newBoard = getNextBoard();
            if (gameOver()) {
                if (autoRestart) {
                    restartGame();
                    continue;
                } else
                    break;
            }
            int move = decideMove.apply(newBoard);
            makeMove(move);
        }

    }
}
