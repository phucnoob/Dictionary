package uet.oop.dictionary.dao;

import uet.oop.dictionary.data.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordDAO extends BaseDAO<Word> {

    public WordDAO(String database) throws SQLException {
        super(database);
    }

    public WordDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void add(Word word) throws SQLException {
        if (Word.isInValidWord(word)) {
            throw new IllegalArgumentException("Word is not valid to add.");
        }
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
            } else {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Add word operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Add word failed.", e);
        }
    }

    @Override
    public void update(int id, Word word) throws SQLException {
        if (Word.isInValidWord(word)) {
            throw new IllegalArgumentException("Word is not valid to update.");
        }

        String updateWordSQL = "UPDATE words SET target = ?, phonetics = ? WHERE word_id = ?;";

        try (var statement = getConn().prepareStatement(updateWordSQL)) {
            statement.setString(1, word.getTarget());
            statement.setString(2, word.getPhonetics());
            statement.setInt(3, id);
            int updated = statement.executeUpdate();

            if (updated <= 0) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Update word operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Word update failed.", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String deleteWordSQL = "DELETE FROM words WHERE word_id = ?;";

        try (var statement = getConn().prepareStatement(deleteWordSQL);) {
            statement.setInt(1, id);
            int deleted = statement.executeUpdate();

            if (deleted < 0) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Delete operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Delete word failed.", e);
        }
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

    public Optional<Word> lookup(String target) throws SQLException {
        String getWordSQL = "SELECT * FROM words WHERE target like ?;";

        try (var statement = getConn().prepareStatement(getWordSQL);) {
            statement.setString(1, target);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Word word = new Word();
                word.setID(result.getInt("word_id"));
                word.setPhonetics(result.getString("phonetics"));
                word.setTarget(result.getString("target"));

                return Optional.of(word);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);

            throw new SQLException(e);
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

    @Override
    public int total() throws SQLException {
        String countSQL = "SELECT COUNT(*) from words;";
        try (PreparedStatement statement = conn.prepareStatement(countSQL)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Can't get the word count", ex);
        }

        return 0;
    }

    public List<Word> prefixSearch(String prefix) {
        String preFixSearch = "SELECT * FROM words WHERE target like ?";
        try (PreparedStatement st = conn.prepareStatement(preFixSearch)){
            st.setString(1, prefix + "%");
            ResultSet wordResult = st.executeQuery();
            List<Word> words = new ArrayList<>();
            while (wordResult.next()) {
                int word_id = wordResult.getInt("word_id");
                String target = wordResult.getString("target");
                String phonetics = wordResult.getString("phonetics");
                Word word = new Word(target, phonetics);
                word.setID(word_id);
                words.add(word);
            }

            return words;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
