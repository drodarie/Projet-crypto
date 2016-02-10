package core.connection;

import core.agents.ProcessResponse;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Connection {
    private static final int TIMEOUT = 5;

    private SSLSocket socket;
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

    public Connection(InetAddress address, int port){
        this.serverDestAddress=address;
        this.port=port;
    }

    public void createConnection() throws IOException {
        System.out.println("OPENING CONNECTION  to "+serverDestAddress+" ... ...");
        SocketFactory factory = SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(serverDestAddress, port);
        this.socket.setSoTimeout(this.TIMEOUT * 1000);
        String[] suites = {"SSL_DH_anon_WITH_RC4_128_MD5"};
        this.socket.setEnabledCipherSuites(suites);
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
        closeConnection();
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
        closeConnection();
    }

    private void closeConnection(){
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
}