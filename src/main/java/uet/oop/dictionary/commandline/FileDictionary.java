package uet.oop.dictionary.commandline;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dictionary store data in file.
 * None of methods has error-handling.
 * This expects they are handle in DictionaryManagement.
 */
public class FileDictionary implements Dictionary {

    private final List<Word> wordList;
    public FileDictionary() {
        wordList = new ArrayList<>();
    }
    public List<Word> getWordList() {
        return wordList;
    }

    /**
     * Add a word to dictionary.
     * @param word - The word to add.
     * @return True if word added successfully.
     */
    @Override
    public boolean add(Word word) {
        Optional<Word> founded = get(word.getTarget());
        if (founded.isPresent()) {
            founded.get().addExplain(word.getExplain());
            return true;
        } else {
            return wordList.add(word);
        }
    }

    /**
     * Look up a word target in the word list.
     * @param target - The word target want to look.
     * @return Optional<data.Word> or empty Optional if not found.
     * Example: new Dictionary.lookup("book");
     */
    @Override
    public Optional<Word> get(String target) {
        return wordList.stream()
                .filter(word -> word.getTarget().equals(target))
                .findAny();
    }

    public boolean get(Word target) {
        return wordList.stream()
                .anyMatch(word -> word.equals(target));
    }

    @Override
    public boolean update(String target, String newExplain) {
        Optional<Word> founded = get(target);
        if (founded.isPresent()) {
            founded.get().setExplain(newExplain);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(String target) {
        Optional<Word> founded = get(target);
        if (founded.isPresent()) {
            wordList.remove(founded.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Word> lookupWord(String target) {
        return wordList.stream()
                .filter(word -> word.getTarget().equals(target))
                .collect(Collectors.toList());
    }

    @Override
    public List<Word> searchWord(String target) {
        return wordList.stream()
                .filter(word -> word.getTarget().startsWith(target))
                .collect(Collectors.toList());
    }

    @Override
    public List<Word> getAll() {
        return wordList;
    }

    public int totalWords() {
        return wordList.size();
    }
}