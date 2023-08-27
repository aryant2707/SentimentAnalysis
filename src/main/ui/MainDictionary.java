package ui;

import model.Dictionary;
import persistence.JsonRead;
import persistence.JsonSave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Console-based user interface for a sentiment analysis tool that uses the Dictionary class to analyze user input.
public class MainDictionary {
    private static ArrayList<String> inputs = new ArrayList<>();
    private static ArrayList<String> outcomes = new ArrayList<>();
    private static ArrayList<String> currentInput = new ArrayList<>();
    private static ArrayList<String> currentOutput = new ArrayList<>();
    private static String path = "data/saved-data.json";


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Dictionary dict;
        try {
            dict = new Dictionary("data/AFINN-111.txt");
        } catch (IOException e) {
            System.out.println("Could not load dictionary file.");
            return;
        }

        boolean running = true;
        System.out.println("Welcome to the Sentiment Analyzer.");
        while (running) {
            optionMenu();

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    analyzeInput(scanner, dict);
                    break;
                case "2":
                    previousInputs();
                    break;
                case "3":
                    addInput();
                    break;
                case "4":
                    correctOutcome(scanner);
                    break;
                case "5":
                    JsonSave.saveToJson(inputs, outcomes, path);
                    break;
                case "6":
                    JsonRead.loadFromJson(inputs, outcomes, path);
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    running = false;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private static void optionMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Enter a sentence to analyze.");
        System.out.println("2. View previous inputs and outcomes.");
        System.out.println("3. Add the current input to the list.");
        System.out.println("4. Correct a previous outcome.");
        System.out.println("5. Save list of previous results.");
        System.out.println("6. Load previous results.");
        System.out.println("7. Quit.");
    }


    private static void correctOutcome(Scanner scanner) {
        System.out.println("Correct a previous outcome:");
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println((i + 1) + ". Input: " + inputs.get(i) + " | Outcome: " + outcomes.get(i));
        }
        System.out.println("Select the number of the input to correct:");
        int index = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the corrected outcome:");
        String correctedOutcome = scanner.nextLine();
        outcomes.set(index - 1, correctedOutcome);
    }

    private static void addInput() {
        System.out.println("Add the current input to the list:");
        inputs.add(currentInput.get(0));
        outcomes.add(currentOutput.get(0));
    }

    private static void previousInputs() {
        System.out.println("Previous inputs:");
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println((i + 1) + ". Input: " + inputs.get(i) + " | Outcome: " + outcomes.get(i));
        }
    }

    private static void analyzeInput(Scanner scanner, Dictionary dict) {
        System.out.println("Enter a sentence:");
        String sentence = scanner.nextLine();
        String outcome = dict.analyzeSentence(sentence);
        System.out.println("Sentiment: " + outcome);
        if (!currentInput.isEmpty() && !currentOutput.isEmpty()) {
            currentInput.remove(0);
            currentOutput.remove(0);
        }
        currentInput.add(sentence);
        currentOutput.add(outcome);
    }


}
