package uet.oop.dictionary.dao;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T> implements Closeable {
    protected Connection conn;

    public Connection getConn() {
        return conn;
    }

    protected BaseDAO(String database) throws SQLException {
        conn = DriverManager.getConnection(database);
    }

    protected BaseDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void add(T data) throws SQLException;
    public abstract void update(int id, T data) throws SQLException;
    public abstract void delete(int id) throws SQLException;
    public abstract Optional<T> get(int id) throws SQLException;
    public abstract List<T> getAll(int limit) throws SQLException;

    public abstract int total() throws SQLException;
}
