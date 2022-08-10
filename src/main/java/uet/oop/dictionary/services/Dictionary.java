package uet.oop.dictionary.services;


import uet.oop.dictionary.data.Word;

import java.util.List;
import java.util.Optional;

public interface Dictionary {
    boolean add(Word word);

    Optional<Word> lookup(String target);

    boolean update(String target, Word word);

    boolean delete(int id);

    List<Word> searchWord(String prefix);

    List<Word> getAll(int limit);

    int totalWords();
}
