package by.fpmibsu.findafriend.dataaccesslayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class StatementBuilder {
    private final Connection connection;
    private final Logger logger = LogManager.getLogger();

    public StatementBuilder(Connection connection) {
        this.connection = connection;
    }


    public PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
            statement.setString(index, b ? "T" : "F");
            return;
        }
        if (o instanceof Character ch) {
            statement.setString(index, Character.toString(ch));
            return;
        }
        if (o instanceof byte[] bytes) {
            statement.setBytes(index, bytes);
            return;
        }
        logger.error("No conversion for type " + o.getClass().getName());
        throw new UnsupportedOperationException("No conversion for type " + o.getClass().getName());
    }
}
