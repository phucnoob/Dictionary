package uet.oop.dictionary.services;

import uet.oop.dictionary.dao.DefinitionDao;
import uet.oop.dictionary.dao.WordDao;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryService implements Dictionary {

    private final WordDao wordDao;
    private final DefinitionDao definitionDao;

    private final Connection conn;

    public static final DictionaryService DEFAULT;
    static {
        DEFAULT = new DictionaryService();
    }

    public DictionaryService() {
        try {
            conn = DriverManager.getConnection(Config.DATABASE_URL);
            conn.setAutoCommit(false);
            wordDao = new WordDao(conn);
            definitionDao = new DefinitionDao(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Application can not start." ,e);
        }
    }

    @Override
    public boolean add(Word word) {
        return false;
    }

    @Override
    public Optional<Word> lookup(String target) {
        try {
            Optional<Word> word = wordDao.lookup(target);
            if (word.isPresent()) {
                List<Definition> wordDefs = definitionDao.getWordDefs(word.get().getID());
                word.get().setDefinitions(wordDefs);
                return word;
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public boolean update(String target, Word word) {
        return false;
    }

    @Override
    public boolean remove(String target) {
        System.out.println("delete...");
        return false;
    }

    @Override
    public List<Word> searchWord(String prefix) {
        return wordDao.prefixSearch(prefix);
    }

    @Override
    public List<Word> getAll() {
        return null;
    }

    @Override
    public int totalWords() {
        try {
            return wordDao.total();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "database operations failed.", e);
        }

        return 0;
    }
}
