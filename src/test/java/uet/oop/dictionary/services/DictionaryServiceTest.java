package uet.oop.dictionary.services;

import org.junit.jupiter.api.Test;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryServiceTest {

    @Test
    void add() {
        DictionaryService service = new DictionaryService();
        List<Definition> definitions = new ArrayList<>();
        Definition definition = new Definition();
        definition.setExplain("hahaha");
        definition.setWordType("hshshs");
        definitions.add(definition);
        Word word = new Word("AAB", "eiei", definitions);

        service.add(word);
    }
}