package uet.oop.dictionary.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uet.oop.dictionary.data.Word;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WordDaoTest {

    private Connection conn;
    public static String URL;

    @BeforeEach
    void init() throws SQLException {
        Path path = Path.of("src/original/resources").toAbsolutePath();
        URL = String.format("jdbc:sqlite:%s", path.resolve("testdata.db"));

        conn = DriverManager.getConnection(URL);
        conn.setAutoCommit(false);
    }

    @Test
    void add() {
        try {
            var dao = new WordDao(conn);
            Word word = new Word("book", "/buk/", Collections.emptyList());
            dao.add(word);
            List<Word> words = dao.getAll(1);

            Word ans = words.get(0);

            assertEquals(word.getTarget(), ans.getTarget());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            var dao = new WordDao(conn);
            Word word = new Word("book", "/buk/", Collections.emptyList());
            dao.add(word);
            List<Word> words = dao.getAll(1);
            Word founded = words.get(0);
            int id = founded.getID();
            word.setTarget("hello");
            word.setPhonetics("/hello/");

            dao.update(id, word);

            Optional<Word> afterUpdate = dao.get(id);

            assert afterUpdate.isPresent();

            assertEquals("hello", afterUpdate.get().getTarget());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            var dao = new WordDao(conn);
            Word word = new Word("book", "/buk/", Collections.emptyList());
            dao.add(word);
            List<Word> words = dao.getAll(1);
            Word addedWord = words.get(0);

            dao.delete(addedWord.getID());
            assertEquals(dao.getAll(1), Collections.emptyList());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void get() throws SQLException {
        var dao = new WordDao(conn);
        Word word = new Word("book", "/buk/", Collections.emptyList());
        dao.add(word);

        Optional<Word> gotWord = dao.get(1);
        assert gotWord.isPresent();
        assertEquals("book", gotWord.get().getTarget());
    }

    @Test
    void getAll() throws SQLException {
        var dao = new WordDao(conn);
        Word word = new Word("book", "/buk/", Collections.emptyList());
        Word word1 = new Word("book1", "/buk/", Collections.emptyList());
        Word word2 = new Word("book2", "/buk/", Collections.emptyList());
        Word word3 = new Word("book3", "/buk/", Collections.emptyList());
        Word word4 = new Word("book4", "/buk/", Collections.emptyList());
        dao.add(word);
        dao.add(word1);
        dao.add(word2);
        dao.add(word3);
        dao.add(word4);

        List<Word> getWords = dao.getAll(3);
        assertEquals(3, getWords.size());

        getWords = dao.getAll(10);
        assertEquals(5, getWords.size());
    }

    @Test
    void prefixSearch() throws SQLException {
        var dao = new WordDao(conn);
        Word word = new Word("book", "/buk/", Collections.emptyList());
        Word word1 = new Word("book1", "/buk/", Collections.emptyList());
        Word word2 = new Word("book2", "/buk/", Collections.emptyList());
        Word word3 = new Word("book3", "/buk/", Collections.emptyList());
        Word word4 = new Word("book4", "/buk/", Collections.emptyList());
        dao.add(word);
        dao.add(word1);
        dao.add(word2);
        dao.add(word3);
        dao.add(word4);

        List<Word> searched = dao.prefixSearch("book");

        assertEquals(5, searched.size());
    }

    @AfterEach
    void clean() throws SQLException {
        conn.rollback();
        conn.close();
    }
}