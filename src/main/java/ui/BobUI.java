package ui;

import core.agents.Bob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Pierre on 10/02/2016.
 */
public class BobUI extends JFrame{
    private JComboBox cbQuestions;
    private JButton envoyerButton;
    private JLabel tAnswer;
    private JPanel mainPanel;

    private Bob bob;

    public BobUI(Bob bob) {
        super("Bob");

        this.bob = bob;

        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setMinimumSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        envoyerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // TODO se connecter, envoyer le message et attendre la réponse
            }
        });
    }
}
