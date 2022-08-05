package uet.oop.dictionary.dao;

import uet.oop.dictionary.data.Word;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordDao extends BaseDao<Word> {

    protected WordDao(String database) throws SQLException {
        super(database);
    }

    public WordDao(Connection conn) {
        super(conn);
    }

    @Override
    public boolean add(Word word) {
        if (Word.isInValidWord(word)) return false;

        String addWordSQL = "INSERT INTO words(target, phonetics) VALUES (?, ?);";
        try (var statement = getConn()
                .prepareStatement(addWordSQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, word.getTarget());
            statement.setString(2, word.getPhonetics());
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                word.setID(resultSet.getInt(1));
            }
            return rowAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean update(int id, Word word) {
        if (Word.isInValidWord(word)) return false;

        String updateWordSQL = "UPDATE words SET target = ?, phonetics = ? WHERE word_id = ?;";

        try (var statement = getConn().prepareStatement(updateWordSQL);) {
            statement.setString(1, word.getTarget());
            statement.setString(2, word.getPhonetics());
            statement.setInt(3, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String deleteWordSQL = "DELETE FROM words WHERE word_id = ?;";

        try (var statement = getConn().prepareStatement(deleteWordSQL);) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    @Override
    public Optional<Word> get(int id) {
        String getWordSQL = "SELECT * FROM words WHERE word_id = ?;";

        try (var statement = getConn().prepareStatement(getWordSQL);) {
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Word word = new Word();
                word.setID(id);
                word.setPhonetics(result.getString("phonetics"));
                word.setTarget(result.getString("target"));

                return Optional.of(word);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public List<Word> getAll(int limit) throws SQLException {
        String getAllWordSQL = "SELECT * FROM words LIMIT ?;";

        try (var statement = getConn().prepareStatement(getAllWordSQL);) {

            statement.setInt(1, limit);
            ResultSet result = statement.executeQuery();
            List<Word> words = new ArrayList<>();
            while (result.next()) {
                Word word = new Word();
                word.setID(result.getInt("word_id"));
                word.setPhonetics(result.getString("phonetics"));
                word.setTarget(result.getString("target"));

                words.add(word);
            }

            return words;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.emptyList();
    }

}