package by.fpmibsu.findafriend.dataaccesslayer;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface UserShelterDao {
    class Pair {
        private int key;
        private int value;

        public Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    List<Pair> getAll() throws SQLException, DaoException;
    void add(int shelterId, int userId) throws DaoException;
    void remove(int shelterId, int userId) throws DaoException;
    void removeAll(int shelterId) throws DaoException;
    void removeUser(int userId) throws DaoException;

    List<Integer> getUsersId(int shelter_id) throws DaoException;

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
}
