package uet.oop.dictionary.services;

import uet.oop.dictionary.dao.WordDAO;
import uet.oop.dictionary.data.Trie;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrieSearch implements SearchEngine {

    private Trie inner;

    public TrieSearch() {
        inner = new Trie();

        Runnable load = new Runnable() {
            @Override
            public void run() {
                try (Connection conn = DriverManager.getConnection(Config.DATABASE_URL)) {
                    WordDAO dao = new WordDAO(conn);
                    dao.getAll(Integer.MAX_VALUE)
                            .stream().map(Word::getTarget)
                            .forEach( w -> inner.insert(w));

                    dao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Fails to load trie search", ex);
                }
            }
        };

        new Thread(load).start();
    }

    @Override
    public List<String> search(String prefix) {
        return inner.autoComplete(prefix);
    }
}
