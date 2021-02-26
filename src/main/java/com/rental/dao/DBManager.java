package com.rental.dao;

import com.rental.exception.DBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBManager {

    private DBManager() {
    }

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, DBException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            con = ds.getConnection();
        } catch (NamingException e) {
            throw new DBException("Can't get Connection from pool!",e);
        }
        return con;
    }

    public Connection startTransaction() throws SQLException, DBException {
        Connection con = getInstance().getConnection();
        con.setAutoCommit(false);
        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return con;
    }

    public void commitTransaction(Connection con) throws DBException {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new DBException("Can't commit Transaction!",e);
        }
    }

    public void rollbackTransaction(Connection con) throws DBException {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new DBException("Can't rollback Transaction!",e);
        }
    }

    public void close(AutoCloseable closeable) throws DBException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new DBException("Can't close Connection!O_o",e);
            }
        }
    }
}
