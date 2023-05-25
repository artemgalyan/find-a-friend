package by.fpmibsu.findafriend.dataaccesslayer.pool;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

final public class ConnectionPool implements AutoCloseable {
//	private static Logger logger = Logger.getLogger(ConnectionPool.class);

    private final String connectionString;
    private int maxSize;
    private int checkConnectionTimeout;

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    public ConnectionPool(String connectionString) {
        this.connectionString = connectionString;
    }

    public synchronized Connection getConnection() {
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {}
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
//                    logger.error("The limit of number of database connections is exceeded");
//                    throw new PersistentException();
                    throw new DaoException("The limit of number of db connections is exceeded");
                }
            } catch (InterruptedException | SQLException e) {
//                logger.error("It is impossible to connect to a database", e);
                throw new DaoException(e);
            }
        }
        usedConnections.add(connection);
//        logger.debug(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        return connection;
    }

    synchronized void freeConnection(PooledConnection connection) {
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
//                logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException | InterruptedException e1) {
//            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
            }
        }
    }

    public synchronized void init(int startSize, int maxSize, int checkConnectionTimeout) throws DaoException {
        try {
            destroy();
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException e) {
//            logger.fatal("It is impossible to initialize connection pool", e);
//            throw new PersistentException(e);
            throw new DaoException(e);
        }
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(this, DriverManager.getConnection(connectionString));
    }

    public synchronized void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
            }
        }
        usedConnections.clear();
    }
    @Override
    public void close() throws Exception {
        destroy();
    }
}
