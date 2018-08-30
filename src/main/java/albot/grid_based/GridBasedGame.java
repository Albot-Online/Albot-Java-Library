package albot.grid_based;

import albot.AlbotConnection;
import albot.Game;
import com.google.gson.JsonObject;

import static albot.Constants.*;

import java.util.List;

/**
 * An abstract class that handles shared logic and structures used in grid- and turn based games.
 */
public abstract class GridBasedGame extends Game {

    protected int width, height;

    private GridBoard currentBoard;

    public GridBasedGame(String ip, int port) {
        super(ip, port);
        initGridDimensions();
    }

    public GridBasedGame() {
        super();
        initGridDimensions();
    }

    protected abstract void initGridDimensions();

    @Override
    protected void extractState(JsonObject jState) {
        currentBoard = GridBasedJsonHandler.parseResponseState(jState, width, height);
        UpdateCurrentBoard(currentBoard);
    }

    protected abstract void UpdateCurrentBoard(GridBoard board);

    /**
     * Plays a move
     * @param move move to make
     */
    public void makeMove(int move) {
        sendCommand(Integer.toString(move));
    }


    /**
     * Gets the legal moves that can be done
     * @param board board to make move in
     * @return List of integers which correspond to a legal move each
     */
    public List<Integer> getPossibleMoves(GridBoard board) {
        String jCommand = GridBasedJsonHandler.createCommandPossibleMoves(board);

        String response = sendCommandReceiveMessage(jCommand);
        return GridBasedJsonHandler.parseResponsePossibleMoves(response);
    }

    /**
     * Simulates a move.
     * @param board board to simulate in
     * @param player player to make the move
     * @param move move to be played
     * @return the board which the move has been applied to.
     */
    public GridBoard simulateMove(GridBoard board, int player, int move) {
        String jCommand = GridBasedJsonHandler.createCommandSimulateMove(board, player, move);

        String response = sendCommandReceiveMessage(jCommand);
        return GridBasedJsonHandler.parseResponseSimulateMove(response, width, height);
    }

    /**
     * Returns the state of the board
     * @param board board to evaluate
     * @return Enum BoardState expressing information about board state.
     */
    public BoardState evaluateBoard(GridBoard board) {
        String jCommand = GridBasedJsonHandler.createCommandEvaluate(board);

        String response = sendCommandReceiveMessage(jCommand);
        return GridBasedJsonHandler.parseResponseEvaluate(response);
    }

}
