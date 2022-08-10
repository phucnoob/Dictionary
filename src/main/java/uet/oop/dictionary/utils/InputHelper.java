package uet.oop.dictionary.utils;


import java.util.Scanner;

public class InputHelper {
    private static InputHelper inner;
    private final Scanner scanner;
    private InputHelper() {
        scanner = new Scanner(System.in);
    }

    public static InputHelper getInstance() {
        if (inner == null) {
            inner = new InputHelper();
        }

        return inner;
    }

    public static String getString(String prompt) {
        InputHelper helper = getInstance();
        if (!prompt.isEmpty()) {
            System.out.print(prompt);
        }
        String str;
        try {
            str = helper.scanner.nextLine();
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
            str = "";
        }
        return str;
    }

    public static int getInt(String prompt) {
        String numStr = getString(prompt);
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage());
            return 0;
        }
    }
}