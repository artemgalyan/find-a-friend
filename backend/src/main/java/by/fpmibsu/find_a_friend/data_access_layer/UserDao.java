package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.User;

import java.util.List;

public class UserDao implements Dao<Integer, User>{
    private static final String SQL_SELECT_ALL_USERS = """
            SELECT user_id, name, surname, email, phone_number, login, password, role.role_id, role.name" + "
            FROM user 
                LEFT JOIN role USING(role_id)""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT user_id, name, surname, email, phone_number, login, password, role.role_id, role.name" + " 
            FROM user 
                LEFT JOIN role USING(role_id)" + " 
                WHERE user_id=?""";
    private static final String SQL_INSERT_USER = """
            INSERT INTO user VALUES(?,?,?,?,?,?,?)""";
    private static final String SQL_DELETE_USER = """
            DELETE 
            FROM user 
            WHERE user_id=?""";
    public static final String SQL_DELETE_BY_ID = """
            DELETE 
            FROM advert 
            WHERE user_id=?""";

    @Override
    public List<User> getAll() throws DaoException {
        return null;
    }

    @Override
    public User getEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(User instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(User instance) throws DaoException {
        return false;
    }

    @Override
    public User update(User instance) throws DaoException {
        return null;
    }
}
