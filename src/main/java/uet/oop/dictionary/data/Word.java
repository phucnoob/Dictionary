package uet.oop.dictionary.data;

import java.util.Collections;
import java.util.List;

public class Word {
    private int word_id;

    public void setID(int word_id) {
        this.word_id = word_id;
    }

    public int getID() {
        return word_id;
    }

    private String target;
    private String phonetics;
    private List<Definition> definitions;

    public Word() {

    }

    public static boolean isInValidWord(Word word) {
        if (word == null) return true;
        if (word.target == null) return true;
        return word.phonetics == null;
    }

    public Word(String target, String phonetics, List<Definition> definitions) {
        this.target = target;
        this.phonetics = phonetics;
        this.definitions = definitions;
    }

    public Word(String target, String phonetics) {
        this.target = target;
        this.phonetics = phonetics;
        this.definitions = Collections.emptyList();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }
}
