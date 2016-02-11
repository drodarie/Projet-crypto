package ui;

import core.agents.Bob;
import core.connection.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Inet4Address;

/**
 * Created by Pierre on 10/02/2016.
 */
public class BobUI extends JFrame{
    private JComboBox cbQuestions;
    private JButton envoyerButton;
    private JLabel tAnswer;
    private JPanel mainPanel;

    private Bob bob;

    public BobUI(final Bob bob) {
        super("Bob");

        this.bob = bob;

        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setMinimumSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        cbQuestions.addItem("Quelle est votre couleur préférée?");
        cbQuestions.addItem("Quelle est votre taille?");
        cbQuestions.addItem("Quelle âge avez-vous?");
        cbQuestions.addItem("Quelle est votre couleur de cheveux?");
        cbQuestions.addItem("Quel est votre profession?");
        cbQuestions.addItem("Quel est votre signe astrologique?");
        cbQuestions.addItem("Combien d'enfants avez-vous?");
        cbQuestions.addItem("Aimez-vous les carottes?");
        cbQuestions.addItem("Qui est le premier: l'oeuf ou la poule?");
        cbQuestions.addItem("Être ou ne pas être?");

        envoyerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Connection connection = new Connection(Inet4Address.getByName("localhost"), 1500);
                    connection.createConnection();
                    String messageToSend = bob.createMessageToSend(cbQuestions.getSelectedIndex() + 1);
                    System.out.println("Message to send : " + messageToSend);
                    String response = connection.sendAndWait(messageToSend);

                    tAnswer.setText(bob.decodeResponse(response));
                } catch (IOException e1) {
                    System.out.println("Error trying to connect");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
