package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.User;
import com.rental.dao.UserDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class UserService implements Serializable {
    private static final long serialVersionUID = -513920424098567388L;
    private final UserDao userDao = new UserDao();

    public User findUserByLogin(String login) throws DBException {
        return userDao.findUserByLogin(login);
    }
    public List<User> findAllClients() throws DBException {
        return userDao.findAllUsersClients();
    }

    public void insertUser(User user) throws DBException {
        userDao.insertUser(user);
    }

    public User findUser(Order order) throws DBException {
        return userDao.findUserById(order.getClient().getId());
    }

    public void updateUserPassport(User user) throws DBException {
        userDao.updateUserPassport(user);
    }

    public List<User> findAllUsers() throws DBException {
        return userDao.findAllUsers();
    }

    public void updateUserBlocked(User user) throws DBException {
        userDao.updateUserBlockedById(user);
    }

    public void updateUserAmount(User user, Connection con) throws DBException {
        userDao.updateUserAmount(user,con);
    }

    public List<User> findAllBannedUsers() {
        try {
            return userDao.findAllBannedUsers();
        } catch (DBException e) {
            return null;
        }
    }
}
