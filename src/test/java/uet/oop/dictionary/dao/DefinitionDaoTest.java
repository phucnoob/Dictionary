package uet.oop.dictionary.dao;

import javafx.scene.Parent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefinitionDaoTest {

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
        DefinitionDao dao = new DefinitionDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        boolean added = dao.add(d);
        assertTrue(added);
        Definition found = dao.get(1).get();
        assertEquals("xin chao", found.getExplain());
    }

    @Test
    void update() {
        DefinitionDao dao = new DefinitionDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        boolean added = dao.add(d);
        assertTrue(added);
        d.setWordType("tinh tu");

        dao.update(1, d);

        assertEquals("tinh tu", d.getWordType());
    }

    @Test
    void delete() {
        DefinitionDao dao = new DefinitionDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        boolean added = dao.add(d);
        assertTrue(added);

        dao.delete(1);

        assertEquals(Optional.empty(), dao.get(1));
    }

    @Test
    void get() {
        DefinitionDao dao = new DefinitionDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        boolean added = dao.add(d);
        assertTrue(added);
        Definition found = dao.get(1).get();
        assertEquals("xin chao", found.getExplain());
    }

    @Test
    void getWordDefs() throws SQLException {
        DefinitionDao definitionDao = new DefinitionDao(conn);
        WordDao wordDao = new WordDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        Definition d1 = new Definition(1, "danh tu", "chao xin", 1);
        Definition d2 = new Definition(1, "danh tu", "xin tien", 1);
        Definition d3 = new Definition(1, "danh tu", "xin c~hao", 1);
        Definition d4 = new Definition(1, "danh tu", "xin ch-ao", 1);
        Definition d5 = new Definition(1, "danh tu", "xin ch0-ao", 1);
        w.setDefinitions(List.of(d, d1, d2, d3, d4, d5));

        boolean added = wordDao.add(w);
        for (var def: w.getDefinitions()) {
            definitionDao.add(def);
        }

        assert added;

        List<Definition> definitions = definitionDao.getWordDefs(1);

        assertEquals(6, definitions.size());
    }

    @Test
    void getAll() {
        DefinitionDao dao = new DefinitionDao(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        Definition d1 = new Definition(1, "danh tu", "chao xin", 1);
        Definition d2 = new Definition(1, "danh tu", "xin tien", 1);
        Definition d3 = new Definition(1, "danh tu", "xin c~hao", 1);
        Definition d4 = new Definition(1, "danh tu", "xin ch-ao", 1);
        Definition d5 = new Definition(1, "danh tu", "xin ch0-ao", 1);
        w.setDefinitions(List.of(d));

        boolean added = dao.add(d);
        dao.add(d1);
        dao.add(d2);
        dao.add(d3);
        dao.add(d4);
        dao.add(d5);
        assertTrue(added);

        List<Definition> defs = dao.getAll(3);
        assertEquals(3, defs.size());

        defs = dao.getAll(100);
        assertEquals(6, defs.size());
    }

    @AfterEach
    void clean() throws SQLException {
        conn.rollback();
        conn.close();
    }
}