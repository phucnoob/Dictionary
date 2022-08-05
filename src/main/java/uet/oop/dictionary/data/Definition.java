package uet.oop.dictionary.data;

public class Definition {
    private int id;
    private String wordType;
    private String explain;
    private int wordId = -1;

    public Definition() {
    }

    public static boolean isInValidDefinition(Definition definition) {
        if (definition == null) return true;
        if (definition.explain == null) return true;
        if (definition.wordType == null) return true;
        return definition.wordId == -1;
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
}
