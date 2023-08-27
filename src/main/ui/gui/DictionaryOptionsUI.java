package ui.gui;

import model.*;

import javax.swing.*;

import model.Event;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import persistence.JsonRead;
import persistence.JsonSave;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The DictionaryOptionsUI class provides a graphical user interface to interact with the Dictionary model.

public class DictionaryOptionsUI extends JFrame {

    List<String> currentSentiment = new ArrayList<>();

    private static ArrayList<String> inputs = new ArrayList<>();
    private static ArrayList<String> outcomes = new ArrayList<>();
    private static ArrayList<String> currentInput = new ArrayList<>();
    private static ArrayList<String> currentOutput = new ArrayList<>();
    private static String path = "data/saved-data.json";


    // MODIFIES: This
    // EFFECTS: Constructs a new instance of DictionaryOptionsUI with a title "Dictionary Options".
    public DictionaryOptionsUI() {
        setTitle("Dictionary Options");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10); // padding

        button1(panel, constraints);
        button2(panel, constraints);
        button3(panel, constraints);
        button4(panel, constraints);
        button5(panel, constraints);
        button6(panel, constraints);
        sentimentButton(panel, constraints);
        button8(panel, constraints);
    }

    private void button8(JPanel panel, GridBagConstraints constraints) {
        JButton button8 = new JButton("Quit");
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleLogs();
                System.exit(0);
            }
        });
        panel.add(button8, constraints);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void consoleLogs() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());

        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private static void sentimentButton(JPanel panel, GridBagConstraints constraints) {
        JButton sentimentButton = new JButton("View Sentiment Analysis Results");
        sentimentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the sentiment analysis results
                int positiveCount = 0;
                int negativeCount = 0;
                int unknownCount = 0;
                for (String output : outcomes) {
                    if (output.equals("Positive")) {
                        positiveCount++;
                    } else if (output.equals("Negative")) {
                        negativeCount++;
                    } else {
                        unknownCount++;
                    }
                }

                // Create a new dataset for the bar graph
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                dataset.setValue(positiveCount, "Count", "Positive");
                dataset.setValue(negativeCount, "Count", "Negative");
                dataset.setValue(unknownCount, "Count", "Unknown");

                // Create a new chart object and add the dataset to the chart
                JFreeChart chart = ChartFactory.createBarChart(
                        "Sentiment Analysis Results", // Title
                        "Sentiment", // X-axis label
                        "Count", // Y-axis label
                        dataset, // Dataset
                        PlotOrientation.VERTICAL, // Orientation
                        true, // Show legend
                        true, // Show tooltips
                        false // Show URLs
                );

                // Add the chart to a new panel and display the panel in a new window
                ChartPanel chartPanel = new ChartPanel(chart);
                JFrame chartFrame = new JFrame("Sentiment Analysis Results");
                chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                chartFrame.setSize(500, 400);
                chartFrame.add(chartPanel);
                chartFrame.setVisible(true);
            }
        });
        panel.add(sentimentButton, constraints);
        constraints.gridy++;
    }

    private void button6(JPanel panel, GridBagConstraints constraints) {
        JButton button6 = new JButton("Load previous results");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromJson();
            }
        });
        panel.add(button6, constraints);

        constraints.gridy++;
    }

    private void button5(JPanel panel, GridBagConstraints constraints) {
        JButton button5 = new JButton("Save list of previous results");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToJson();
            }
        });
        panel.add(button5, constraints);

        constraints.gridy++;
    }

    private void button4(JPanel panel, GridBagConstraints constraints) {
        JButton button4 = new JButton("Correct a previous outcome");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCorrectUI();
            }
        });
        panel.add(button4, constraints);

        constraints.gridy++;
    }

    private static void button3(JPanel panel, GridBagConstraints constraints) {
        JButton button3 = new JButton("Add the current input to the list");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInput();
            }
        });
        panel.add(button3, constraints);

        constraints.gridy++;
    }

    private static void button2(JPanel panel, GridBagConstraints constraints) {
        JButton button2 = new JButton("View previous inputs and outcomes");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, previousInputs());
            }
        });
        panel.add(button2, constraints);

        constraints.gridy++;
    }

    private void button1(JPanel panel, GridBagConstraints constraints) {
        JButton button1 = new JButton("Enter a sentence to analyze");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createStatementUI();
            }
        });
        panel.add(button1, constraints);

        constraints.gridy++;
    }


    // EFFECTS: Creates a new JFrame object to allow the user to input a statement and
    // runs the "createStatementUI()" method when the button is clicked.
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
                try {
                    analyze(statement, new Dictionary("data/AFINN-111.txt"));
                } catch (IOException ex) {
                    System.out.println("Error accessing Dictionary.");
                }
                JOptionPane.showMessageDialog(null, "Outcome: " + currentOutput);
            }
        });
        extracted(statementFrame, statementPanel, submitButton);
    }

    private static void extracted(JFrame statementFrame, JPanel statementPanel, JButton submitButton) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statementFrame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        statementPanel.add(buttonPanel, BorderLayout.SOUTH);

        statementFrame.add(statementPanel);
        statementFrame.setVisible(true);
    }


    // EFFECTS: Analyzes a given statement using the "Dictionary" class and returns
    // the statement's overall sentiment as a String.
    private void analyze(String text, Dictionary dict) {

        String outcome = dict.analyzeSentence(text);
        if (!currentInput.isEmpty() && !currentOutput.isEmpty()) {
            currentInput.remove(0);
            currentOutput.remove(0);
        }
        currentInput.add(text);
        currentOutput.add(outcome);

    }

    // MODIFIES: This
    // EFFECTS: Creates a new JFrame object to allow the user to select a previous outcome to correct and
    // runs the "createCorrectUI()" method when the button is clicked.
    private void createCorrectUI() {
        JFrame statementFrame = new JFrame("Correct Analysis`");
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

        extracted(statementFrame, statementPanel, submitButton);
    }

    // MODIFIES: This
    // EFFECTS: Adds the current input to the "inputs" ArrayList and
    // adds the current output to the "outcomes" ArrayList.
    private static void addInput() {
        try {
            inputs.add(currentInput.get(0));
            outcomes.add(currentOutput.get(0));
            JOptionPane.showMessageDialog(null, "Added to the list.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "No input found");
        }
    }

    // MODIFIES: This
    // EFFECTS: Displays a message dialog box containing the list of previous inputs and outcomes.
    private static List<List<String>> previousInputs() {
        List<String> inout = new ArrayList<>();
        List<List<String>> combined = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            inout.add(inputs.get(i));
            inout.add(outcomes.get(i));
            combined.add(new ArrayList<>(inout));
            inout.clear();
        }
        return combined;
    }

    // EFFECTS: Saves the "inputs" and "outcomes" ArrayLists to a JSON file located at "path".
    private void saveToJson() {
        try {
            JsonSave.saveToJson(inputs, outcomes, path);
            JOptionPane.showMessageDialog(null, "Saved to: " + path);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOException was thrown");
        }
    }

    // EFFECTS: Loads the "inputs" and "outcomes" ArrayLists from a JSON file located at "path".
    private void loadFromJson() {
        JsonRead.loadFromJson(inputs, outcomes, path);
        JOptionPane.showMessageDialog(null, "Loaded from: " + path);
    }
}




