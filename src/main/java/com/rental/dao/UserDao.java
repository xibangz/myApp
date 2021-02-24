package com.rental.dao;


import com.rental.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.rental.dao.Fields.*;

public class UserDao {
    private static final String SQL_FIND_USER_BY_ID = "select * from `accounts` where id=?;";
    private static final String SQL_INSERT_USER = "insert into `accounts`(login,password,passport,role_id) values(?,?,?,?);";
    private static final String SQL_FIND_USER_BY_LOGIN = "select * from `accounts` where login = ?;";
    private static final String SQL_UPDATE_USER_PASSPORT_BY_ID = "update `accounts` set passport=? where id=?;";
    private static final String SQL_FIND_ALL_USERS = "select * from `accounts`;";
    private static final String SQL_UPDATE_USER_BLOCKED_BY_ID = "update accounts set is_blocked=? where id=?;";
    private static final String SQL_UPDATE_USER_AMOUNT = "update accounts set amount=? where id=?;";
    private static final String SQL_FIND_ALL_USERS_CLIENTS = "select * from accounts where role_id=" + USER_CLIENT_ROLE + ";";
    private final DBManager dbManager;

    public UserDao() {
        dbManager = DBManager.getInstance();
    }

    public void updateUserAmount(User user) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_USER_AMOUNT);
            int z = 1;
            prepSt.setInt(z++, user.getAmount());
            prepSt.setInt(z, user.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public void updateUserAmount(User user, Connection con) {
        PreparedStatement prepSt = null;
        try {
            prepSt = con.prepareStatement(SQL_UPDATE_USER_AMOUNT);
            int z = 1;
            prepSt.setInt(z++, user.getAmount());
            prepSt.setInt(z, user.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);
        }
    }

    public void insertUser(User user) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertUser(con, user);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(con);
        }
    }

    public void insertUser(Connection con, User user) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setString(z++, user.getLogin());
        prepSt.setString(z++, user.getPassword());
        prepSt.setString(z++, user.getPassport());
        prepSt.setInt(z, user.getRoleId());
        if (prepSt.executeUpdate() > 1) {
            getGeneratedId(prepSt, user);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, User user) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }
        rs.close();
    }

    public User findUserByLogin(String login) {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            prepSt.setString(1, login);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_ALL_USERS);
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return users;
    }

    public List<User> findAllUsersClients() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_ALL_USERS_CLIENTS);
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return users;
    }

    public User findUserById(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return user;
    }

    public void updateUserPassport(User user) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_USER_PASSPORT_BY_ID);
            int z = 1;
            prepSt.setString(z++, user.getPassport());
            prepSt.setInt(z, user.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public void updateUserBlockedById(User user) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_USER_BLOCKED_BY_ID);
            int z = 1;
            prepSt.setBoolean(z++, user.isBlocked());
            prepSt.setInt(z, user.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(ENTITY_ID));
        user.setLogin(rs.getString(USER_LOGIN));
        user.setPassword(rs.getString(USER_PASSWORD));
        user.setPassport(rs.getString(USER_PASSPORT));
        user.setRoleId(rs.getInt(USER_ROLE_ID));
        user.setAmount(rs.getInt(USER_AMOUNT));
        user.setBlocked(rs.getBoolean(USER_IS_BLOCKED));
        return user;
    }
}
