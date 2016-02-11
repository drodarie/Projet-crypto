package ui;

import core.agents.Alice;
import core.connection.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Pierre on 10/02/2016.
 */
public class AliceUI extends JFrame{
    private JTextField textField1;
    private JButton enAttenteDeBobButton;
    private JTextField a195cmTextField;
    private JTextField a22AnsTextField;
    private JTextField brunTextField;
    private JTextField etudiantTextField;
    private JTextField capricorneTextField;
    private JTextField a0TextField;
    private JTextField ouiTextField;
    private JTextField aucunDesDeuxViveTextField;
    private JTextField bananaTextField;
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
