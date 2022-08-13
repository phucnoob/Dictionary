package uet.oop.dictionary.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Trie {
    private boolean lastWord;

    public boolean isLastWord() {
        return lastWord;
    }

    public void setLastWord(boolean lastWord) {
        this.lastWord = lastWord;
    }

    private HashMap<Character, Trie> child;

    public HashMap<Character, Trie> getChild() {
        return child;
    }

    public void setChild(HashMap<Character, Trie> child) {
        this.child = child;
    }

    public Trie(boolean lastWord) {
        this();
        this.lastWord = lastWord;
    }

    public Trie() {
        child = new HashMap<>();
        lastWord = false;
    }

    public void insert(String word) {
        Trie current = this;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            if (current.child.get(word.charAt(i)) == null) {
                current.child.put(word.charAt(i), new Trie());
            }
            current = current.child.get(word.charAt(i));
        }
        current.setLastWord(true);
    }

    public boolean find(String word) {
        int length = word.length();
        Trie current = this;
        for (int i = 0; i < length; i++) {
            if (current.child.get(word.charAt(i)) == null) {
                return false;
            }

            current = current.child.get(word.charAt(i));
        }

        return current.lastWord;
    }

    private void autoCompleteHelper(Trie root, List<String> match, StringBuffer buff) {
        if (root.isLastWord()) {
            match.add(buff.toString());
        }

        if (root.getChild() == null || root.getChild().isEmpty()) {
            return;
        }

        for (Map.Entry<Character, Trie> ch: root.getChild().entrySet()) {
            autoCompleteHelper(ch.getValue(), match, buff.append(ch.getKey()));
            buff.setLength(buff.length() - 1);
        }
    }

    public List<String> autoComplete(String prefix) {
        List<String> words = new ArrayList<>();
        Trie current = this;
        StringBuffer buff = new StringBuffer();
        for (char ch : prefix.toCharArray()) {
            current = current.getChild().get(ch);
            if (current == null) {
                return Collections.emptyList();
            }
            buff.append(ch);
        }

        autoCompleteHelper(current, words, buff);

        return words;
    }
}

class TrieTestDrive {
    public static void main(String[] args) throws IOException {
        Trie trie = new Trie();
        long begin = System.currentTimeMillis();
        Files.lines(Path.of("/usr/share/dict/words"))
                        .forEach(trie::insert);
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        System.out.println(trie.autoComplete("book"));
        System.out.println(System.currentTimeMillis() - begin);
        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }
}
