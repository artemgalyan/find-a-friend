package by.fpmibsu.findafriend.dataaccesslayer.usershelter;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface UserShelterDao {
    class Pair {
        private int shelterId;
        private int userId;

        public Pair(int key, int value) {
            this.shelterId = key;
            this.userId = value;
        }

        public int getShelterId() {
            return shelterId;
        }

        public void setShelterId(int shelterId) {
            this.shelterId = shelterId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    List<Pair> getAll() throws SQLException, DaoException;

    void add(int shelterId, int userId) throws DaoException;

    void remove(int shelterId, int userId) throws DaoException;

    void removeAll(int shelterId) throws DaoException;

    void removeUser(int userId) throws DaoException;

    List<Integer> getUsersId(int shelterId) throws DaoException;

    int getShelterId(int userId) throws DaoException;

    default void close(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
