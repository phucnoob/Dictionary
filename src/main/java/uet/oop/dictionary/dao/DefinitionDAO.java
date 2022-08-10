package uet.oop.dictionary.dao;

import uet.oop.dictionary.data.Definition;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefinitionDao extends BaseDao<Definition> {

    public DefinitionDao(String database) throws SQLException {
        super(database);
    }

    public DefinitionDao(Connection conn) {
        super(conn);
    }

    @Override
    public void add(Definition definition) throws SQLException {
        if (Definition.isInValidDefinition(definition)) {
            throw new IllegalArgumentException("Definition is not valid.");
        }

        String addDefinition = "INSERT INTO definitions(explain, type, word_id) VALUES (?, ?, ?);";
        try (var statement = getConn()
                .prepareStatement(addDefinition, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, definition.getExplain());
            statement.setString(2, definition.getWordType());
            statement.setInt(3, definition.getWordId());

            int rowAffect = statement.executeUpdate();

            if (rowAffect > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                definition.setId(resultSet.getInt(1));
            } else {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Add definition operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Add definition failed.", e);
        }
    }

    @Override
    public void update(int id, Definition definition) throws SQLException {
        if (Definition.isInValidDefinition(definition)) {
            throw new IllegalArgumentException("Definition is not valid.");
        }

        String updateDefinition = "UPDATE definitions SET explain = ?, type = ?, word_id = ? WHERE definition_id = ?;";
        try (var statement = getConn().prepareStatement(updateDefinition)) {
            statement.setString(1, definition.getExplain());
            statement.setString(2, definition.getWordType());
            statement.setInt(3, id);
            statement.setInt(4, definition.getId());

            int rowAffect = statement.executeUpdate();

            if (rowAffect < 0) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Update definition operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Update definition failed.", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String deleteDefinition = "DELETE FROM definitions WHERE definition_id = ?;";
        try (var statement = getConn().prepareStatement(deleteDefinition)) {
            statement.setInt(1, id);
            int rowAffect = statement.executeUpdate();

            if (rowAffect < 0) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Update definition operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Delete definition failed.", e);
        }
    }

    public void deleteAllWordDefs(int wordID) throws SQLException {
        String deleteDefinition = "DELETE FROM main.definitions WHERE main.definitions.word_id = ?;";
        try (var st = getConn().prepareStatement(deleteDefinition)) {
            st.setInt(1, wordID);
            int rowAffect = st.executeUpdate();

            if (rowAffect < 0) {
                Logger.getLogger(getClass().getName())
                        .log(Level.WARNING, "Delete definition operation failed. But no errors was found");
            }
        } catch (SQLException e) {
            throw new SQLException("Delete definition failed.", e);
        }
    }

    @Override
    public Optional<Definition> get(int id) {
        String getDefSQL = "SELECT * FROM definitions WHERE definition_id = ?;";

        try (var statement = getConn().prepareStatement(getDefSQL);) {
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Definition def = new Definition();
                def.setId(id);
                def.setExplain(result.getString("explain"));
                def.setWordType(result.getString("type"));
                def.setWordId(result.getInt("word_id"));

                return Optional.of(def);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }

    public List<Definition> getWordDefs(int word_id) throws SQLException {
        String getAllWordDefsSQL = "SELECT * FROM definitions WHERE word_id = ?;";

        try (var statement = getConn()
                .prepareStatement(getAllWordDefsSQL);) {

            statement.setInt(1, word_id);

            ResultSet result = statement.executeQuery();
            return getListFromResultSet(result);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);

            throw new SQLException("get word definitions failed.", e);
        }
    }

    @Override
    public List<Definition> getAll(int limit) {
        String getAllDefsSQL = "SELECT * FROM definitions LIMIT ?;";

        try (var statement = getConn()
                .prepareStatement(getAllDefsSQL);) {
            statement.setInt(1, limit);
            ResultSet result = statement.executeQuery();
            return getListFromResultSet(result);

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    private List<Definition> getListFromResultSet(ResultSet result) throws SQLException {
        List<Definition> defs = new ArrayList<>();
        while (result.next()) {
            Definition def = new Definition();
            def.setId(result.getInt("definition_id"));
            def.setExplain(result.getString("explain"));
            def.setWordType(result.getString("type"));
            def.setWordId(result.getInt("word_id"));

            defs.add(def);
        }
        return defs;
    }

    @Override
    public int total() throws SQLException {
        String countSQL = "SELECT COUNT(*) from definitions;";
        try (PreparedStatement statement = conn.prepareStatement(countSQL)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Can't get the definitions count", ex);
        }

        return 0;
    }
}
