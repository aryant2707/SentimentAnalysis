package ui.gui;

import model.Event;
import model.EventLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Provides the user interface for choosing between two libraries for
// sentiment analysis - Stanford NLP Library and Dictionary.

public class ChooseLibraryUI extends JFrame {

    public ChooseLibraryUI() {
        setTitle("Sentiment Analyzer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // 10px padding

        JLabel label = new JLabel("Welcome to the Sentiment Analyzer.", SwingConstants.CENTER);
        panel.add(label);

        JButton stanfordButton = new JButton("Stanford NLP Library");
        stanfordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StanfordOptionsUI();
            }
        });
        panel.add(stanfordButton);

        JButton dictionaryButton = new JButton("Dictionary");
        dictionaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DictionaryOptionsUI();
            }
        });
        extractedBecauseLength(panel, dictionaryButton);
    }



    private void extractedBecauseLength(JPanel panel, JButton dictionaryButton) {
        panel.add(dictionaryButton);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleLogs();
                System.exit(0);
            }
        });
        panel.add(quitButton);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px margin
        add(panel);
        setVisible(true);
    }

    private void consoleLogs() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());

        }
    }

    public static void main(String[] args) {
        new ChooseLibraryUI();
    }
}