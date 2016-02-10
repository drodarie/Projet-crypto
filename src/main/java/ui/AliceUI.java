package ui;

import core.agents.Alice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public AliceUI(Alice alice) {
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
                //TODO : Ouvrir un socket, attendre une demande, y répondre et se refermer
            }
        });
    }
}
