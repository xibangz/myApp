package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.bean.User;
import com.rental.dao.OrderDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class OrderService implements Serializable {
    private static final long serialVersionUID = -1570515444734069053L;
    private final OrderDao orderDao = new OrderDao();

    public void insertOrder(Order order, Connection con) throws DBException {
        orderDao.insertOrder(order);
    }

    public List<Order> findOrdersByUser(User user) throws DBException {
        return orderDao.findOrdersByUser(user);
    }


    public Order findOrder(OrderTotal total) throws DBException {
        return orderDao.findOrderById(total.getOrder().getId());
    }

    public int countNotAvailableCars(Order order) throws DBException {
        return orderDao.countNotAvailableCars(order);
    }

    public int countNotAvailableDrivers(Order order) throws DBException {
        return orderDao.countNotAvailableDrivers(order);
    }

    public void setCarTotal(List<Order> list, CarTotalService service) throws DBException {
        for(Order item:list){
            item.setCarTotal(service.findCarTotalById(item.getCarTotal().getId()));
        }
    }
}
