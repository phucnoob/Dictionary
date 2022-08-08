package uet.oop.dictionary.dao;

import uet.oop.dictionary.utils.Config;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<T> implements Closeable {
    protected Connection conn;

    public Connection getConn() {
        return conn;
    }

    protected BaseDao(String database) throws SQLException {
        conn = DriverManager.getConnection(database);
    }

    protected BaseDao(Connection conn) {
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

    public abstract boolean add(T data) throws SQLException;
    public abstract boolean update(int id, T data) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;
    public abstract Optional<T> get(int id) throws SQLException;
    public abstract List<T> getAll(int limit) throws SQLException;

    public abstract int total() throws SQLException;
}
