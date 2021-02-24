package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.User;
import com.rental.dao.UserDao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class UserService implements Serializable {
    private static final long serialVersionUID = -513920424098567388L;
    private final UserDao userDao = new UserDao();

    public User findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public User findUser(Order order) {
        return userDao.findUserById(order.getClient().getId());
    }

    public void updateUserPassport(User user) {
        userDao.updateUserPassport(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public void updateUserBlocked(User user) {
        userDao.updateUserBlockedById(user);
    }

    public void updateUserAmount(User user, Connection con) {
        userDao.updateUserAmount(user,con);
    }
}
