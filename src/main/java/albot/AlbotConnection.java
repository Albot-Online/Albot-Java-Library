package albot;

import java.io.*;
import java.net.Socket;

/**
 * Handles connection and transporting data between client and bot.
 */
public class AlbotConnection {

    private static BufferedReader in;
    private static PrintWriter out;
    private final int bufferSize = 2048;

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
            s.setSendBufferSize(bufferSize);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Could not establish TCP connection with Albot.");
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
            System.out.println("Could not read message from TCP connection with Albot.");
            e.printStackTrace();
        }
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
     * Kills the TCP connection properly.
     */
    static void terminate() {
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("Could not close BufferedReader. (TCP)");
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void printConnectedMessage() {
        System.out.println("Connected, waiting for game to start...");
    }

}
