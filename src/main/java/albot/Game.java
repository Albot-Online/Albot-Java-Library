package albot;

import com.google.gson.JsonObject;

import static albot.Constants.BoardState;

/**
 * Handles connection to the Albot client and provides general game functionality.
 */
public abstract class Game extends AlbotConnection {

    /**
     * Whether game is over and if so, who the winner is.
     */
    public BoardState boardState;

    public Game(String ip, int port) {
        super(ip, port);
        printWaitingMessage();
    }

    public Game() {
        super();
        printWaitingMessage();
    }

    JsonObject receiveNextGameState() {
        String response = receiveMessage();
        JsonObject jObject = JsonHandler.tryParse(response, JsonObject.class);
        boardState = JsonHandler.extractBoardState(jObject);
        if(boardState != BoardState.ongoing)
            printGameOverMessage(boardState);
        return jObject;
    }

    /**
     * Blocking receive call for next board and its state, both are stored locally as public variables.
     * @return The state of the board/game, check for ongoing if you want to see if game is over or not.
     */
    public BoardState waitForNextGameState() {
        JsonObject jState = receiveNextGameState();

        extractState(jState);

        return boardState;
    }

    protected abstract void extractState(JsonObject jState);

    /**
     * Restarts the current finished game.
     */
    public void restartGame() {
        System.out.println("Restarting game...");
        sendCommand(Constants.Actions.restartGame);
    }

    private void printWaitingMessage() {
        System.out.println("Waiting for game to start...");
    }

    private void printGameOverMessage(BoardState boardState) {
        if (boardState == BoardState.playerWon)
            System.out.println("You won!");
        else if (boardState == BoardState.enemyWon)
            System.out.println("You lost!");
        else
            System.out.println("Draw!");

    }
}
