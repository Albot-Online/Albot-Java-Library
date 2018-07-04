package albot.snake;

import albot.AlbotConnection;
import albot.Constants;

import java.util.function.Function;

import static albot.snake.SnakeBeans.*;

public class SnakeGame extends AlbotConnection {

    SnakeBoard currentBoard;

    public SnakeGame(String ip, int port) {
        super(ip, port);
        System.out.println("Connected, waiting for game to start...");
    }

    public SnakeGame() {
        super();
        System.out.println("Connected, waiting for game to start...");
    }

    public SnakeBoard getNextBoard() {

        String state = receiveState(); // Receive before check for game over

        if (gameOver())
            return null;
        BoardBean response = SnakeJsonHandler.parseResponseState(state);
        currentBoard = new SnakeBoard(currentBoard, response);
        return currentBoard;
    }

    public void makeMove(String direction) {
        sendCommand(direction);
    }

    public SnakeBeans.PossibleMoves getPossibleMoves(SnakeBoard board) {
        String request = SnakeJsonHandler.createCommandPossibleMoves(board);
        String response = sendCommandReceiveState(request);
        return SnakeJsonHandler.parseResponsePossibleMoves(response);
    }

    public SnakeBoard simulatePlayerMove(SnakeBoard board, String move) {
        MovesToSimulate simMoves = new MovesToSimulate(move, true);
        return handleSimulateMove(board, simMoves);
    }

    public SnakeBoard simulateEnemyMove(SnakeBoard board, String move) {
        MovesToSimulate simMoves = new MovesToSimulate(move, false);
        return handleSimulateMove(board, simMoves);
    }

    public SnakeBoard simulateMoves(SnakeBoard board, String playerMove, String enemyMove) {
        MovesToSimulate simMoves = new MovesToSimulate(playerMove, enemyMove);
        return handleSimulateMove(board, simMoves);
    }

    private SnakeBoard handleSimulateMove(SnakeBoard board, MovesToSimulate simMoves) {
        String request = SnakeJsonHandler.createCommandSimulate(board, simMoves);
        String response = sendCommandReceiveState(request);
        return SnakeJsonHandler.parseResponseSimulate(response);
    }

    public Constants.BoardState evaluateBoard(SnakeBoard board) {
        String request = SnakeJsonHandler.createCommandEvaluate(board);
        String response = sendCommandReceiveState(request);

        return SnakeJsonHandler.parseResponseEvaluate(response);
    }

    public void playGame(Function<SnakeBoard, String> decideMove, boolean autoRestart) {

        while (true) {
            SnakeBoard newBoard = getNextBoard();
            if (gameOver()) {
                if (autoRestart) {
                    restartGame();
                    continue;
                } else
                    break;
            }
            String move = decideMove.apply(newBoard);
            makeMove(move);
        }

    }

}

