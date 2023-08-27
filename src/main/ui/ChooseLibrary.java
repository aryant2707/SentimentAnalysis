package ui;

import java.io.IOException;
import java.util.Scanner;

public class ChooseLibrary {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Sentiment Analyzer.");
        Boolean running = true;
        while (running) {
            analyzerChoose();

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    Main.main();
                    break;
                case "2":
                    MainDictionary.main();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    running = false;
            }
        }
    }

    private static void analyzerChoose() {
        System.out.println("Select an option:");
        System.out.println("1. Stanford NLP Library");
        System.out.println("2. Dictionary");
        System.out.println("3. Quit");
    }
}