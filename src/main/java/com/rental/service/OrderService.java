package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.bean.User;
import com.rental.dao.OrderDao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class OrderService implements Serializable {
    private static final long serialVersionUID = -1570515444734069053L;
    private final OrderDao orderDao = new OrderDao();

    public void insertOrder(Order order, Connection con) {
        orderDao.insertOrder(order);
    }

    public List<Order> findOrdersByUserId(User user) {
        return orderDao.findOrdersByUserId(user);
    }

    public Order findOrder(OrderTotal total) {
        return orderDao.findOrderById(total.getOrder().getId());
    }

    public int countNotAvailableCars(Order order) {
        return orderDao.countNotAvailableCars(order);
    }

    public int countNotAvailableDrivers(Order order) {
        return orderDao.countNotAvailableDrivers(order);
    }

    public void setCarTotal(List<Order> list, CarTotalService service){
        for(Order item:list){
            item.setCarTotal(service.findCarTotalById(item.getCarTotal().getId()));
        }
    }
}
