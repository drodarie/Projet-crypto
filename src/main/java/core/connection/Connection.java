package core.connection;

import core.agents.ProcessResponse;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;

public class Connection {
    private static final int TIMEOUT = 60;

    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedOutputStream out;
    private BufferedInputStream in;

    private InetAddress serverDestAddress;
    private int port;


    public int getPort() {
        return port;
    }

    public InetAddress getServerDestAddress() {
        return serverDestAddress;
    }

    public Connection() {
    }

    public Connection(InetAddress address, int port) {
        this.serverDestAddress = address;
        this.port = port;
    }

    public void createServer() throws IOException {
        System.out.println("OPENING CONNECTION  to " + serverDestAddress + " ... ...");
        ServerSocket serverSocket = new ServerSocket(1500);
        serverSocket.setSoTimeout(this.TIMEOUT * 1000);
        this.serverSocket = serverSocket;
        this.socket = serverSocket.accept();
        this.out = new BufferedOutputStream(this.socket.getOutputStream());
        this.in = new BufferedInputStream(this.socket.getInputStream());
        System.out.println("Sever opened ... ...");
    }

    public void createConnection() throws IOException {
        System.out.println("OPENING CONNECTION  to " + serverDestAddress + " ... ...");
        Socket socket = new Socket(serverDestAddress, 1500);
        socket.setSoTimeout(this.TIMEOUT * 1000);
        this.socket = socket;
        this.out = new BufferedOutputStream(this.socket.getOutputStream());
        this.in = new BufferedInputStream(this.socket.getInputStream());
        System.out.println("Connection opened ... ...");
    }

    private void sendMessage(String messageToSend) throws IOException {
        this.out.write(messageToSend.getBytes());
        this.out.flush();
    }

    private String waitForResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        response.append(((char) in.read()));
        while (in.available() != 0) {
            response.append(((char) in.read()));
        }
        return response.toString();
    }

    public String sendAndWait(String messageToSend) {
        boolean runConnection = true;
        String response = null;
        while (runConnection) {
            try {
                sendMessage(messageToSend);
                response = waitForResponse();
                runConnection = false;
            } catch (IOException e) {
                System.out.println("Can't read incoming input from client.\n" + e.getMessage());
                runConnection = false;
            }
        }
        return response;
    }

    public void waitAndSend(ProcessResponse inResponse) {
        boolean runConnection = true;
        String response = null;
        while (runConnection) {
            try {
                response = waitForResponse();
                String messageToSend = inResponse.process(response);
                sendMessage(messageToSend);
                runConnection = false;
            } catch (IOException e) {
                System.out.println("Can't read incoming input from client.\n" + e.getMessage());
                runConnection = false;
            }
        }
    }

    public void closeConnection() {
        try {
            System.out.println("CLOSING CONNECTION ... ...");
            in.close();
            out.close();
            this.socket.close();
            System.out.println("CONNECTION CLOSED");
        } catch (IOException e) {
            System.out.println("Can't close streams\n" + e.getMessage());
        }
    }

    public void closeServer(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("Can't close server");
        }
    }
}