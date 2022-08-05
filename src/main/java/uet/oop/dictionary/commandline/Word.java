package uet.oop.dictionary.commandline;

public class Word {
    private String target;
    private String explain;

    protected Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }

    public Word validate() {
        if (stringValidate(getTarget()) && stringValidate(getExplain())) {
            return this;
        }
        return null;
    }

    protected static boolean stringValidate(String str) {
        if (str == null) {
            return false;
        } else
            if (str.isEmpty()) {
            return false;
        } else return !str.isBlank();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static Word from(String target, String explain) {
        return new Word(target, explain).validate();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        if (!stringValidate(target))
            return;
        this.target = target.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        if (!stringValidate(explain))
            return;
        this.explain = explain.trim();
    }

    public void addExplain(String added) {
        if (!stringValidate(added))
            return;
        setExplain(getExplain() + "\n" + added.trim());
    }

    @Override
    public String toString() {
        return this.target + ": " + this.explain;
    }
}
