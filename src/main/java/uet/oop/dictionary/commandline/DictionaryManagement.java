package uet.oop.dictionary.commandline;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class DictionaryManagement {

    private final Dictionary dictionary;
    public DictionaryManagement() {
        dictionary = new FileDictionary();
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }
    /**
     * Allow to insert a number of words from commandline.
     * This assumes user input correctly.
     */
    public void insertFromCommandline() {
        int numOfWord = InputHelper.getInt("Number of words to add: ");
        for (int i = 0; i < numOfWord; i++) {
            System.out.println("Word #" + i);
            String target = InputHelper.getString("Word target: ");
            String explain = InputHelper.getString("Word explain: ");
            this.add(Word.from(target, explain));
        }
    }

    /**
     * Insert words list from file.
     * @param src - The src file to load.
     * @return number of word that successfully loaded.
     * The file must be in format: <word>\t<word> to be load successfully.
     */
    public int insertFromFile(Path src) {

        if (src == null) {
            throw new NullPointerException("The file to insert can't not be null");
        }

        try(BufferedReader reader = Files.newBufferedReader(src)) {
            int records = dictionary.totalWords();
            reader.lines()
                    .map(line -> line.split("\t"))
                    .filter(parsedToken -> parsedToken.length == 2)
                    .map(parsedToken -> Word.from(parsedToken[0], parsedToken[1]))
                    .forEach(dictionary::add);

            return dictionary.totalWords() - records;

        } catch (IOException e) {
            System.err.println("Can't open " + src.toAbsolutePath());
            System.err.println(e.getMessage());
        }

        return -1;
    }

    public boolean add(Word word) {
        if (word == null) {
            System.err.println("Can't add null word to dictionary.");
            return false;
        }
        return dictionary.add(word);
    }

    public boolean update(Word updated) {
        if (updated == null) {
            System.err.println("Can't translate null word to dictionary.");
            return false;
        }
        return dictionary.update(updated.getTarget(), updated.getExplain());
    }

    public boolean remove(String target) {
        if (target == null) {
            System.err.println("Can't remove null word to dictionary.");
            return false;
        }
        return dictionary.remove(target);
    }

    /**
     * Look for a word target in the dictionary.
     * @param target - The target to look up.
     * @return A list of words that match the target.
     */
    public List<Word> dictionaryLookup(String target) {
        if (target == null || target.isEmpty()) {
            return Collections.emptyList();
        }

        return dictionary.lookupWord(target);
    }

    public List<Word> dictionarySearch(String target) {
        if (target == null || target.isEmpty()) {
            return Collections.emptyList();
        }

        return dictionary.searchWord(target);
    }

    public List<Word> getAllWords() {
        return dictionary.getAll();
    }

    public void dictionaryExportToFile(Path exportPath) {
        List<Word> allWords = getAllWords();
        try (var writer = Files.newBufferedWriter(
                exportPath, StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (var word: allWords) {
                writer.write(String.format("%s\t%s\n", word.getTarget(), word.getExplain()));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}