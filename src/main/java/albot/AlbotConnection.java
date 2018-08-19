package albot;

import java.io.*;
import java.net.Socket;

/**
 * Handles connection and transporting data between client and bot.
 */
public class AlbotConnection {

    private BufferedReader in;
    private PrintWriter out;
    private final int bufferSize = 2048;

    private boolean gameOver = false;
    private int winner;

    /**
     * Constructor for connection
     * @param ip local ip to client
     * @param port local port to client
     */
    public AlbotConnection(String ip, int port) {
        initConnection(ip, port);
        printConnectedMessage();
    }

    public AlbotConnection() {
        initConnection("127.0.0.1", 4000);
        printConnectedMessage();
    }

    private void initConnection(String ip, int port) {
        try {
            Socket s = new Socket(ip, port);
            s.setReceiveBufferSize(bufferSize);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Might have to fetch data another way if there are line breaks in data.

    /**
     * Blocking receive call for new TCP message as raw string.
     * @return response as raw string
     */
    public String receiveMessage() {
        String incomingData = null;
        try {
            do {
                incomingData = in.readLine();
            } while(incomingData == null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleGameOverCheck(incomingData);
        return incomingData;
    }

    /**
     * Sends the string to the client as a raw string.
     * @param command command to send as raw string
     */
    public void sendCommand(String command) {
        out.write(command);
        out.flush();
    }

    /**
     * Sends the string to the client as a raw string, then makes a blocking receive call for new TCP message.
     * @param command command to send as raw string
     * @return response as raw string
     */
    public String sendCommandReceiveMessage(String command) {
        sendCommand(command);
        return receiveMessage();
    }

    /**
     * Restarts the current finished game.
     */
    public void restartGame() {
        System.out.println("Restarting game...");
        sendCommand(Constants.Actions.restartGame);
        gameOver = false;
    }

    /**
     * Checks for game over, make sure to check this after receiving the state.
     * @return true if game is over, else false
     */
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Returns winner of the game, call this when game is over.
     * @return 1 if you won, -1 if you lost, 0 if draw.
     */
    public int getWinner() {
        return winner;
    }

    private void handleGameOverCheck(String incomingData) {
        incomingData = incomingData.trim();
        if (incomingData.length() >= 8 && incomingData.substring(0, 8).equals(Constants.Fields.gameOver)) {
            if (incomingData.endsWith("-1")) {
                winner = -1;
                System.out.println("You lost!");
            } else if (incomingData.endsWith("1")) {
                winner = 1;
                System.out.println("You won!");
            } else if (incomingData.endsWith("0")) {
                winner = 0;
                System.out.println("Draw!");
            } else
                System.out.println("Game Over!");

            gameOver = true;
        }
    }

    /**
     * Kills the TCP connection properly.
     */
    public void killConnection() {
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printConnectedMessage() {
        System.out.println("Connected, waiting for game to start...");
    }

}
