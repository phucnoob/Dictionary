package uet.oop.dictionary.commandline;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {

    private final DictionaryManagement management;
    public DictionaryCommandline() {
        management = new DictionaryManagement();
        Path importPath = Path.of("dictionaries.txt");
        if (!Files.exists(importPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(importPath);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        management.insertFromFile(importPath);
    }
    public static void main(String[] args) {
        var commandline = new DictionaryCommandline();
        commandline.parseArgs(args);
        commandline.dispose();
    }

    public void dispose() {
        Path path = Path.of("dictionaries.txt");
        management.dictionaryExportToFile(path);
    }

    public void parseArgs(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }
        String option = args[0];
        switch (option) {
            case "add":
                commandlineAddView();
                break;
            case "update":
                commandlineUpdateView();
                break;
            case "delete":
                commandlineRemoveView();
                break;
            case "file-insert":
                commandlineInsertFromFileView();
                break;
            case "command-insert":
                management.insertFromCommandline();
                break;
            case "file-export":
                commandlineExportToFileView();
                break;
            case "show":
                showWords(management.getAllWords());
                break;
            case "lookup":
                lookUpView();
                break;
            case "search":
                dictionarySearcher();
                break;
            default:
                printUsage();
        }
    }

    public void showWords(List<Word> words) {
        String format = " %-3s| %-16s| %-10s\n";
        System.out.printf(format, "No", "English", "Vietnamese");
        int no = 1;
        for (var word : words) {
            System.out.printf(format, no++, word.getTarget(), word.getExplain());
        }
    }

    public void printUsage() {
        String builder = "Usage: java -jar <jarfile> <command>\n" +
                "add - Add a word.\n" +
                "update - Update a word.\n" +
                "delete - Delete a word by target.\n" +
                "file-insert - Insert wordlist from file.\n" +
                "command-insert - Insert word from commandline.\n" +
                "file-export - Export current word to command.\n" +
                "show - Show all words.\n" +
                "lookup - Look up a word by target.\n" +
                "search - Prefix search.\n";
        System.out.println(builder);
        System.out.println("Application auto load and write to dictionaries.txt every time it runs.");
    }
    public void dictionaryBasic() {
        management.insertFromCommandline();
        showWords(management.getAllWords());
    }

    public void dictionaryAdvanced() {
        Path src = Path.of("dictionaries.txt");
        management.insertFromFile(src);
        showWords(management.getAllWords());
    }

    public void lookUpView() {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Look for: ");
            String target = scanner.nextLine();
            System.out.println("Match: ");
            management.dictionaryLookup(target)
                    .forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void commandlineAddView() {
        System.out.println("Add a new word.");
        String target = InputHelper.getString("Word target: ");
        String explain = InputHelper.getString("Word explain: ");
        boolean added = management.add(Word.from(target, explain));
        info(added, "Add new word successfully.", "Add word failed.");
    }

    public void commandlineUpdateView() {
        System.out.println("Update a word.");
        String target = InputHelper.getString("Word target: ");
        String explain = InputHelper.getString("Word explain: ");
        boolean updated = management.update(Word.from(target, explain));
        info(updated, "Update word successfully.", "Update word failed.");
    }

    public void commandlineRemoveView() {
        System.out.println("Remove a word.");
        String target = InputHelper.getString("Word target: ");
        boolean deleted = management.remove(target);
        info(deleted, "Remove word successfully.", "Update word failed.");
    }

    public void commandlineInsertFromFileView() {
        System.out.println("Insert words from file.");
        String pathStr = InputHelper.getString("Path: ");
        Path path = Path.of(pathStr);
        management.insertFromFile(path);
    }

    public void commandlineExportToFileView() {
        System.out.println("Export words from file.");
        String pathStr = InputHelper.getString("Path: ");
        Path path = Path.of(pathStr);
        management.dictionaryExportToFile(path);
    }
    public void dictionarySearcher() {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Searching for ");
            String target = scanner.nextLine();
            System.out.println("Match: ");
            management.dictionarySearch(target)
                    .forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void info(boolean result, String success, String failure) {
        if (result) {
            System.out.println(success);
        } else {
            System.out.println(failure);
        }
    }
}