package uet.oop.dictionary.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Definition {

    public static final int ID_UNSET = -1;
    private int id = ID_UNSET;
    private String wordType;
    private String explain;
    private int wordId = ID_UNSET;


    public Definition() {
    }

    public static boolean isInValidDefinition(Definition definition) {
        try {
            // Neglect pattern.
            Pattern validWord = Pattern.compile("[^-_$a-zA-Z0-9]*");
            Matcher explainMatch = validWord.matcher(definition.getExplain());
            Matcher wordTypeMatch = validWord.matcher(definition.getWordType());

            return explainMatch.matches()
                    || wordTypeMatch.matches()
                    || definition.getWordId() == ID_UNSET;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public Definition(int id, String wordType, String explain, int wordId) {
        this.id = id;
        this.wordType = wordType;
        this.explain = explain;
        this.wordId = wordId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "wordType='" + wordType + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
