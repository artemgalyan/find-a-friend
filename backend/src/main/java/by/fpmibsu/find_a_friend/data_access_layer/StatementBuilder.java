package by.fpmibsu.find_a_friend.data_access_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class StatementBuilder {
    private final Connection connection;

    public StatementBuilder(Connection connection) {
        this.connection = connection;
    }


    public PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        var statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; ++i) {
            set(statement, i + 1, params[i]);
        }
        return statement;
    }

    private void set(PreparedStatement statement, int index, Object o) throws SQLException {
        if (o instanceof Integer integer) {
            statement.setInt(index, integer);
            return;
        }
        if (o instanceof String string) {
            statement.setString(index, string);
            return;
        }
        if (o instanceof Double d) {
            statement.setDouble(index, d);
            return;
        }
        if (o instanceof Date date) {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            statement.setDate(index, sqlDate);
            return;
        }
        if (o instanceof Boolean b) {
            statement.setInt(index, b ? 'T' : 'F');
            return;
        }

        throw new UnsupportedOperationException("No conversion for type " + o.getClass().getName());
    }
}
