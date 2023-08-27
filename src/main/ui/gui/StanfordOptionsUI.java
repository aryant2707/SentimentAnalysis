package ui.gui;

import model.Analysis;
import model.Event;
import model.EventLog;
import model.SentimentAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Provides a graphical user interface for interacting with the SentimentAnalyzer class
// from the Stanford NLP Library.

public class StanfordOptionsUI extends JFrame {

    List<String> currentSentiment = new ArrayList<>();
    SentimentAnalyzer sentiment = new SentimentAnalyzer();
    List<String> result;

    // MODIFIES: currentSentiment, current, allSentiments.
    // EFFECTS: Constructs a StanfordOptionsUI object with a title "Stanford NLP Library Options".
    public StanfordOptionsUI() {
        setTitle("Stanford NLP Library Options");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10); // padding
        JButton button1 = new JButton("Run the bot on your own statement");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createStatementUI();
            }
        });
        panel.add(button1, constraints);

        constraints.gridy++;
        buttons(panel, constraints);
    }

    private void buttons(JPanel panel, GridBagConstraints constraints) {
        button2(panel, constraints);
        button3(panel, constraints);
        button4(panel, constraints);
        button5(panel, constraints);
        button6(panel, constraints);
        button7(panel, constraints);
    }

    // REQUIRES: panel is not null, constraints is not null.
    // EFFECTS: Creates a new JButton object called button2
    // with the label "Logs", calls the logs() method
    private void button7(JPanel panel, GridBagConstraints constraints) {
        JButton button2 = new JButton("Logs");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logs();
            }
        });
        panel.add(button2, constraints);

        constraints.gridy++;
    }

    // EFFECTS: Displays the Logs window to the user.
    public void logs() {
        JFrame logsFrame = new JFrame("Logs");
        JTextArea logsTextArea = new JTextArea();
        JScrollPane logsScrollPane = new JScrollPane(logsTextArea);
        logsFrame.getContentPane().add(logsScrollPane);
        logsFrame.setSize(400, 300);
        logsFrame.setLocationRelativeTo(null);
        logsTextArea.setEditable(false);
        for (Event e : EventLog.getInstance()) {
            logsTextArea.append(e.toString() + "\n");
        }
        logsFrame.setVisible(true);
    }

    private void consoleLogs() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());

        }
    }


    // REQUIRES: panel and constraints should not be null.
    // EFFECTS: Creates a "Previous statements" button, adds an action listener that
    // displays all previously analyzed statements, and adds the button to the panel.
    private void button2(JPanel panel, GridBagConstraints constraints) {
        JButton button2 = new JButton("Previous statements");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, allSentiments.getSentiments());
            }
        });
        panel.add(button2, constraints);

        constraints.gridy++;
    }

    // REQUIRES: panel and constraints should not be null.
    // EFFECTS: Creates an "Add input to list" button, adds an action listener that
    // adds the current statement and its sentiment analysis result to the analysis
    // list and clears the currentSentiment list, and adds the button to the panel.
    private void button3(JPanel panel, GridBagConstraints constraints) {
        JButton button3 = new JButton("Add input to list");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInput();
                JOptionPane.showMessageDialog(null, "Added: " + current + " to the list.");
            }
        });
        panel.add(button3, constraints);

        constraints.gridy++;
    }

    // REQUIRES: panel and constraints should not be null.
    // EFFECTS: Creates a "Correct Analysis" button, adds an action listener that
    // creates a UI to correct the sentiment analysis of a previously analyzed
    // statement, and adds the button to the panel.
    private void button4(JPanel panel, GridBagConstraints constraints) {
        JButton button4 = new JButton("Correct Analysis");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCorrectUI();
            }
        });
        panel.add(button4, constraints);

        constraints.gridy++;
    }

    // REQUIRES: panel and constraints should not be null.
    // EFFECTS: Creates a "Delete previous statement" button, adds an action listener that removes
    // the last analyzed statement from the analysis list, and adds the button to the panel.
    private void button5(JPanel panel, GridBagConstraints constraints) {
        JButton button6 = new JButton("Delete previous statement");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLast();
            }
        });
        panel.add(button6, constraints);
        constraints.gridy++;
    }

    // REQUIRES: panel and constraints should not be null.
    // EFFECTS: Creates a "Quit" button, adds an action listener that terminates the program,
    // and adds the button to the panel.
    private void button6(JPanel panel, GridBagConstraints constraints) {
        JButton button5 = new JButton("Quit");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleLogs();
                System.exit(0);
            }
        });
        panel.add(button5, constraints);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
        constraints.gridy++;
    }

    // MODIFIES: allSentiments
    // EFFECTS: Removes the last analyzed statement from the analysis list and
    // displays a message if the list is empty or an exception occurs.
    private void deleteLast() {
        try {
            allSentiments.removeSentiment();
            JOptionPane.showMessageDialog(null, "Action Completed.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "No Inputs Found!");
        }
    }


    // MODIFIES: currentSentiment, allSentiments.
    // EFFECTS: Creates a UI for the user to enter a statement to analyze, analyzes
    // the statement using sentiment analysis, and displays the analysis result.
    private void createStatementUI() {
        JFrame statementFrame = new JFrame("Run the bot on your own statement");
        statementFrame.setSize(400, 300);

        JPanel statementPanel = new JPanel(new BorderLayout());

        JTextArea statementText = new JTextArea();
        statementPanel.add(statementText, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statement = statementText.getText();
                analyze(statement);
                JOptionPane.showMessageDialog(null, "Outcome: " + result.get(1));
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statementFrame.dispose();
            }
        });

        lengthLimitforcreateStatementUI(statementFrame, statementPanel, submitButton, backButton);
    }

    private static void lengthLimitforcreateStatementUI(JFrame statementFrame, JPanel statementPanel,
                                                        JButton submitButton, JButton backButton) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        statementPanel.add(buttonPanel, BorderLayout.SOUTH);

        statementFrame.add(statementPanel);
        statementFrame.setVisible(true);
    }

    // MODIFIES: currentSentiment, result.
    // EFFECTS: Analyzes the given text using sentiment analysis and stores the result in the result list.
    // Stores the analyzed statement and its sentiment in the currentSentiment list.
    private void analyze(String text) {

        result = sentiment.analyzeSentiment(text);
        if (!currentSentiment.isEmpty()) {
            currentSentiment.remove(0);
            currentSentiment.remove(0);
        }
        currentSentiment.add(sentiment.getStatement());
        currentSentiment.add(String.valueOf(sentiment.getSentiment()));
    }


    List<String> current = new ArrayList<>(currentSentiment.size());
    Analysis allSentiments = new Analysis();

    // MODIFIES: current, allSentiments, currentSentiment.
    // EFFECTS: Clears the current list and copies the elements of currentSentiment into it.
    // Adds a new list with the same elements to the analysis list allSentiments. Clears the currentSentiment list.
    private void addInput() {
        current.clear();
        for (String s : currentSentiment) {
            current.add(s);
        }
        allSentiments.addSentiment(new ArrayList<>(current));
        currentSentiment.clear();
    }

    // EFFECTS: Creates a UI for the user to correct the sentiment analysis of a previously
    // analyzed statement. Allows the user to edit the statement and its sentiment, and
    // updates the analysis list accordingly.
    private void createCorrectUI() {
        JFrame statementFrame = new JFrame("Correct Analysis");
        statementFrame.setSize(400, 300);

        JPanel statementPanel = new JPanel(new BorderLayout());

        JTextArea statementText = new JTextArea();
        statementPanel.add(statementText, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correction = statementText.getText();
                currentSentiment.remove(1);
                currentSentiment.add(1, correction);
                JOptionPane.showMessageDialog(null, "Corrected: " + currentSentiment.get(1));
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statementFrame.dispose();
            }
        });

        lengthLimitforcreateStatementUI(statementFrame, statementPanel, submitButton, backButton);
    }

}


