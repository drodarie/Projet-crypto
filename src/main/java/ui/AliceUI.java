package ui;

import core.agents.Alice;
import core.connection.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Pierre on 10/02/2016.
 */
public class AliceUI extends JFrame{
    private JTextField textField1;
    private JButton enAttenteDeBobButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JPanel mainPanel;

    private Alice alice;

    public AliceUI(final Alice alice) {
        super("Alice");

        this.alice = alice;
        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setMinimumSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        enAttenteDeBobButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ArrayList<BigInteger> responses = new ArrayList<BigInteger>();
                    responses.add(new BigInteger(textField1.getText().getBytes()));
                    responses.add(new BigInteger(textField2.getText().getBytes()));
                    responses.add(new BigInteger(textField3.getText().getBytes()));
                    responses.add(new BigInteger(textField4.getText().getBytes()));
                    responses.add(new BigInteger(textField5.getText().getBytes()));
                    responses.add(new BigInteger(textField6.getText().getBytes()));
                    responses.add(new BigInteger(textField7.getText().getBytes()));
                    responses.add(new BigInteger(textField8.getText().getBytes()));
                    responses.add(new BigInteger(textField9.getText().getBytes()));
                    responses.add(new BigInteger(textField10.getText().getBytes()));

                    System.out.println(textField1.getText().getBytes());

                    alice.setResponses(responses);

                    Connection connection = new Connection(Inet4Address.getByName("localhost"), 1500);
                    connection.createServer();
                    connection.waitAndSend(alice);
                    connection.closeConnection();
                    connection.closeServer();

                } catch (UnknownHostException e1) {
                    System.out.println("Error trying to connect : " + e1.getMessage());
                } catch (IOException e1) {
                    System.out.println("Error trying to connect : " + e1.getMessage());
                }
            }
        });
    }
}
