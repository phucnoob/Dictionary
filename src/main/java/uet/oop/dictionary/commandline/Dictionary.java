package uet.oop.dictionary.commandline;


import java.util.List;
import java.util.Optional;

public interface Dictionary {
    boolean add(Word word);

    Optional<Word> get(String target);

    boolean update(String target, String newExplain);

    boolean remove(String target);

    List<Word> lookupWord(String target);

    List<Word> searchWord(String target);

    List<Word> getAll();

    int totalWords();
}
