package uet.oop.dictionary.data;

import javafx.css.Match;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            // Neglect pattern.
            Pattern validWord = Pattern.compile("[^-_$a-zA-Z0-9]*");
            Matcher targetMatch = validWord.matcher(word.getTarget());
            Matcher phoneticsMatch = validWord.matcher(word.getPhonetics());

            return targetMatch.matches() || phoneticsMatch.matches();
        } catch (NullPointerException ex) {
            return false;
        }
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

    @Override
    public String toString() {
        return "Word{" +
                "target='" + target + '\'' +
                '}';
    }
}
