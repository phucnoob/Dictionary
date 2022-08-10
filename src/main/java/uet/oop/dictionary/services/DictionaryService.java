package uet.oop.dictionary.services;

import uet.oop.dictionary.dao.DefinitionDAO;
import uet.oop.dictionary.dao.WordDAO;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.utils.Config;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryService implements Dictionary {

    private final WordDAO wordDao;
    private final DefinitionDAO definitionDao;

    private final Connection conn;

    public static final DictionaryService DEFAULT;
    static {
        DEFAULT = new DictionaryService();
    }

    public DictionaryService() {
        try {
            conn = DriverManager.getConnection(Config.DATABASE_URL);
            setDataBase(conn);
            wordDao = new WordDAO(conn);
            definitionDao = new DefinitionDAO(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Application can not start." ,e);
        }
    }

    @Override
    public boolean add(Word word) {

        try {
            conn.setAutoCommit(false);
            wordDao.add(word);
            int id = word.getID();

            for (var def: word.getDefinitions()) {
                def.setWordId(id);
                definitionDao.add(def);
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "Add word failed.", ex);

            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName())
                        .log(Level.FINE, "Can't rollback, critical error!", e);
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName())
                    .log(Level.WARNING, ex.getMessage(), ex);
        }
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

    public boolean update(int id, Word word) {
        try {
            getConn().setAutoCommit(false);
            wordDao.update(id, word);
            definitionDao.deleteAllWordDefs(id);
            for (var definition: word.getDefinitions()) {
                definition.setId(id);
                definitionDao.add(definition);
            }
            getConn().commit();
            getConn().setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "Can't update word has id " + id, e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName())
                        .log(Level.SEVERE, "Database can't rollback. Critical situation.");
            }
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            conn.setAutoCommit(false);
            wordDao.delete(id);
            conn.commit();
            conn.setAutoCommit(true);

            return true;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "Can't delete word has id " + id, e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName())
                        .log(Level.SEVERE, "Database can't rollback. Critical situation.");
            }
        }
        return false;
    }

    @Override
    public List<Word> searchWord(String prefix) {
        return wordDao.prefixSearch(prefix);
    }

    @Override
    public List<Word> getAll(int limit) {
        try {
            List<Word> words = wordDao.getAll(limit);
            for (Word word: words) {
                word.setDefinitions(definitionDao.getWordDefs(word.getID()));
            }

            return words;

        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return Collections.emptyList();
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

    public Connection getConn() {
        return conn;
    }

    private void setDataBase(Connection connection) {
        try (PreparedStatement st = connection.prepareStatement("PRAGMA foreign_keys=ON;")) {
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "Failed to setup the database.", ex);
        }
    }
}
