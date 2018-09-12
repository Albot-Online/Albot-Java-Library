package albot.snake;

import albot.Constants;
import albot.Game;
import com.google.gson.JsonObject;

import java.util.function.Function;

import static albot.snake.SnakeBeans.*;
import static albot.Constants.BoardState;

/**
 * A high level Snake library which sets up the connection and provides basic logic.
 */
public class SnakeGame extends Game {

    public SnakeBoard currentBoard;

    /**
     * Constructor, initializes library and connects to the client.
     * @param ip local ip to client
     * @param port local port to client
     */
    public SnakeGame(String ip, int port) {
        super(ip, port);
    }

    /**
     * Constructor, initializes library and connects to the client.
     */
    public SnakeGame() {
        super();
    }

    @Override
    protected void extractState(JsonObject jState) {
        BoardBean boardBean = SnakeJsonHandler.parseResponseState(jState);
        currentBoard = new SnakeBoard(currentBoard, boardBean);
    }

    /**
     * Plays your move, sets the direction of your snake.
     * @param direction desired direction of your snake.
     */
    public void makeMove(String direction) {
        sendCommand(direction);
    }

    /**
     * Returns the possible moves for both the player and the enemy, based off directions only.
     * @param board board to get possible moves from
     * @return class containing a list of possible moves for the player and the enemy
     */
    public PossibleMoves getPossibleMoves(SnakeBoard board) {
        String request = SnakeJsonHandler.createCommandPossibleMoves(board);
        String response = sendCommandReceiveMessage(request);
        return SnakeJsonHandler.parseResponsePossibleMoves(response);
    }

    /**
     * Simulate a move where only the player moves.
     * @param board board to simulate in
     * @param move move to simulate
     * @return the board where the move has been applied
     */
    public SnakeBoard simulatePlayerMove(SnakeBoard board, String move) {
        MovesToSimulate simMoves = new MovesToSimulate(move, true);
        String request = SnakeJsonHandler.createCommandSimulatePlayer(board, simMoves);
        return handleSimulateMove(board, simMoves, request);
    }

    /**
     * Simulate a move where only the enemy moves.
     * @param board board to simulate in
     * @param move move to simulate
     * @return the board where the move has been applied
     */
    public SnakeBoard simulateEnemyMove(SnakeBoard board, String move) {
        MovesToSimulate simMoves = new MovesToSimulate(move, false);
        String request = SnakeJsonHandler.createCommandSimulateEnemy(board, simMoves);
        return handleSimulateMove(board, simMoves, request);
    }

    /**
     * Simulate a timestep in the game, where both players have moved.
     * @param board board to simulate in
     * @param playerMove player move to simulate
     * @param enemyMove enemy move to simulate
     * @return the board the moves has been applied
     */
    public SnakeBoard simulateMoves(SnakeBoard board, String playerMove, String enemyMove) {
        MovesToSimulate simMoves = new MovesToSimulate(playerMove, enemyMove);
        String request = SnakeJsonHandler.createCommandSimulateBoth(board, simMoves);
        return handleSimulateMove(board, simMoves, request);
    }

    private SnakeBoard handleSimulateMove(SnakeBoard board, MovesToSimulate simMoves, String request) {
        String response = sendCommandReceiveMessage(request);
        return SnakeJsonHandler.parseResponseSimulate(response);
    }

    /**
     * Returns the state of the board
     * @param board board to evaluate
     * @return Enum BoardState expressing information about board state.
     */
    public BoardState evaluateBoard(SnakeBoard board) {
        String request = SnakeJsonHandler.createCommandEvaluate(board);
        String response = sendCommandReceiveMessage(request);

        return SnakeJsonHandler.parseResponseEvaluate(response);
    }

    /**
     * Sends a command to restart the game, resets local game state.
     */
    @Override
    public void restartGame() {
        currentBoard = null;
        super.restartGame();
    }

    /**
     * Plays an entire game by making moves returned by the function provided.
     * @param decideMove a function which takes the current board as argument and returns a move to play
     * @param autoRestart whether you want the game to restart automatically after it has finished
     */
    public void playGame(Function<SnakeBoard, String> decideMove, boolean autoRestart) {

        while (true) {
            if (waitForNextGameState() != BoardState.ongoing) { // GameOver
                if (autoRestart) {
                    restartGame();
                    continue;
                } else
                    break;
            }
            String move = decideMove.apply(currentBoard);
            makeMove(move);
        }

    }
}

