package uet.oop.dictionary.services;

import uet.oop.dictionary.dao.WordDAO;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.utils.Config;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseSearch implements SearchEngine {

    private WordDAO wordDAO;
    @Override
    public List<String> search(String prefix) {
        return wordDAO.prefixSearch(prefix).stream()
                .map(Word::getTarget)
                .collect(Collectors.toList());
    }

    public DataBaseSearch() throws SQLException {
        wordDAO = new WordDAO(Config.DATABASE_URL);
    }
}
