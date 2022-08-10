package uet.oop.dictionary.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefinitionDaoTest {

    private Connection conn;
    public static String URL;

    @BeforeEach
    void init() throws SQLException {
        Path path = Path.of("src/test/resources").toAbsolutePath();
        URL = String.format("jdbc:sqlite:%s", path.resolve("testdata.db"));

        conn = DriverManager.getConnection(URL);
        conn.setAutoCommit(false);
    }

    @Test
    void add() throws SQLException {
        DefinitionDAO dao = new DefinitionDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        dao.add(d);
        Definition found = dao.get(1).get();
        assertEquals("xin chao", found.getExplain());
    }

    @Test
    void update() throws SQLException {
        DefinitionDAO dao = new DefinitionDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        dao.add(d);
        d.setWordType("tinh tu");

        dao.update(1, d);

        assertEquals("tinh tu", d.getWordType());
    }

    @Test
    void delete() throws SQLException {
        DefinitionDAO dao = new DefinitionDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        dao.add(d);

        dao.delete(1);

        assertEquals(Optional.empty(), dao.get(1));
    }

    @Test
    void get() throws SQLException {
        DefinitionDAO dao = new DefinitionDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        w.setDefinitions(List.of(d));

        dao.add(d);
        Definition found = dao.get(1).get();
        assertEquals("xin chao", found.getExplain());
    }

    @Test
    void getWordDefs() throws SQLException {
        DefinitionDAO definitionDao = new DefinitionDAO(conn);
        WordDAO wordDao = new WordDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        Definition d1 = new Definition(1, "danh tu", "chao xin", 1);
        Definition d2 = new Definition(1, "danh tu", "xin tien", 1);
        Definition d3 = new Definition(1, "danh tu", "xin c~hao", 1);
        Definition d4 = new Definition(1, "danh tu", "xin ch-ao", 1);
        Definition d5 = new Definition(1, "danh tu", "xin ch0-ao", 1);
        w.setDefinitions(List.of(d, d1, d2, d3, d4, d5));

        wordDao.add(w);
        for (var def: w.getDefinitions()) {
            definitionDao.add(def);
        }

        List<Definition> definitions = definitionDao.getWordDefs(1);

        assertEquals(6, definitions.size());
    }

    @Test
    void getAll() throws SQLException {
        DefinitionDAO dao = new DefinitionDAO(conn);

        Word w = new Word("hello", "/hello/");
        Definition d = new Definition(1, "danh tu", "xin chao", 1);
        Definition d1 = new Definition(1, "danh tu", "chao xin", 1);
        Definition d2 = new Definition(1, "danh tu", "xin tien", 1);
        Definition d3 = new Definition(1, "danh tu", "xin c~hao", 1);
        Definition d4 = new Definition(1, "danh tu", "xin ch-ao", 1);
        Definition d5 = new Definition(1, "danh tu", "xin ch0-ao", 1);
        w.setDefinitions(List.of(d));

        dao.add(d);
        dao.add(d1);
        dao.add(d2);
        dao.add(d3);
        dao.add(d4);
        dao.add(d5);

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