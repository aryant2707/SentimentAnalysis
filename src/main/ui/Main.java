package ui;

import model.Analysis;
import model.SentimentAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main() {
        Scanner input = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        Scanner correct = new Scanner(System.in);
        boolean running = true;
        Analysis allSentiments = new Analysis();
        List<String> currentSentiment = new ArrayList<>();
        List<String> current = new ArrayList<>(currentSentiment.size());

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Run the bot on your own statement");
            System.out.println("2. Previous statements");
            System.out.println("3. Add input to list");
            System.out.println("4. Correct Analysis");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    // Execute function for option 1
                    System.out.print("Enter your statement: ");
                    // read the user's input and store it in a String variable
                    String text = scanner.nextLine();
                    SentimentAnalyzer sentiment = new SentimentAnalyzer();
                    List<String> result = sentiment.analyzeSentiment(text);
                    System.out.println("============================="
                            + "\n" + "This statement is: "
                            + result.get(1)
                            + "\n"
                            + "============================="
                            + "\n");
                    if (!currentSentiment.isEmpty()) {
                        currentSentiment.remove(0);
                        currentSentiment.remove(0);
                    }
                    currentSentiment.add(sentiment.getStatement());
                    currentSentiment.add(String.valueOf(sentiment.getSentiment()));
                    break;

                case "2":
                    // Execute function for option 2
                    System.out.println(allSentiments.getSentiments());
                    break;

                case "3":
                    // Execute function for option 3
                    if (currentSentiment.isEmpty()) {
                        System.out.println("There's no current sentiment");
                        break;
                    }
                    current.clear();
                    for (String s : currentSentiment) {

                        current.add(s);
                    }
                    allSentiments.addSentiment(new ArrayList<>(current));
                    System.out.println("Added '" + current + "' to the list.");
                    currentSentiment.clear();
                    break;

                case "4":
                    // Execute function for option 5
                    if (currentSentiment.isEmpty()) {
                        System.out.println("There's no current sentiment");
                        break;
                    }
                    String correction = correct.nextLine();
                    currentSentiment.remove(1);
                    currentSentiment.add(1, correction);

                    break;
                case "5":
                    // Quit the program
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    // Invalid input
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}