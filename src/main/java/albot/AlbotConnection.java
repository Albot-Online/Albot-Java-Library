package albot;

import java.io.*;
import java.net.Socket;

public class AlbotConnection {

    private BufferedReader in;
    private PrintWriter out;
    private final int bufferSize = 1024;

    private boolean gameOver = false;
    private int winner;

    public AlbotConnection(String ip, int port) {
        initConnection(ip, port);
    }

    public AlbotConnection() {
        initConnection("127.0.0.1", 4000);
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
    public String receiveState() {
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

    public void sendCommand(String command) {
        out.write(command);
        out.flush();
    }

    public String sendCommandReceiveState(String command) {
        sendCommand(command);
        return receiveState();
    }

    public void restartGame() {
        System.out.println("Restarting game...");
        sendCommand(Constants.Actions.restartGame);
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }

    private void handleGameOverCheck(String incomingData) {
        incomingData = incomingData.trim();
        if (incomingData.length() >= 8 && incomingData.substring(0, 8) == Constants.Fields.gameOver) {
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

    public void killConnection() {
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
