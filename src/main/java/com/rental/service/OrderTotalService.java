package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.dao.OrderTotalDao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderTotalService implements Serializable {
    private static final long serialVersionUID = -1253774906759436242L;
    private final OrderTotalDao orderTotalDao = new OrderTotalDao();

    public void insertOrderTotal(Connection con,OrderTotal total) throws SQLException {
        orderTotalDao.insertOrderTotal(con,total);
    }
    public void insertOrderTotal(OrderTotal total) {
        orderTotalDao.insertOrderTotal(total);
    }

    public void updateOrderTotalStatus(OrderTotal total) {
        orderTotalDao.updateOrderTotal(total);
    }

    public void updateOrderTotalStatus(OrderTotal total, Connection con) {
        orderTotalDao.updateOrderTotal(total);
    }

    public List<OrderTotal> findAllUserTotals(List<Order> orders) {
        List<OrderTotal> totals = new ArrayList<>();
        for (Order order : orders) {
            totals.addAll(orderTotalDao.findOrderTotalByOrder(order));
        }
        return totals;
    }

    public List<OrderTotal> findAllOrderTotals() {
        return orderTotalDao.findAllOrderTotals();
    }

    public OrderTotal findOrderTotalById(int id) {
        return orderTotalDao.findOrderTotalByOrder(id);
    }
}
