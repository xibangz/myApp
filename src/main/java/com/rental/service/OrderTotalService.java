package com.rental.service;

import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.dao.Fields;
import com.rental.dao.OrderTotalDao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderTotalService implements Serializable {
    private static final long serialVersionUID = -1253774906759436242L;
    private final OrderTotalDao orderTotalDao = new OrderTotalDao();

    public void insertOrderTotal(OrderTotal total) {
        orderTotalDao.insertOrderTotal(total);
    }

    public void updateOrderTotalStatus(OrderTotal total) {
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
