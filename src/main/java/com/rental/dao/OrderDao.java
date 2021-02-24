package com.rental.dao;


import com.rental.bean.CarTotal;
import com.rental.bean.Order;
import com.rental.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

public class OrderDao {
    private static final String SQL_INSERT_USER =
            "insert into orders(rent_from,rent_to,car_total_id,account_id,numb_of_drivers,numb_of_cars)" +
                    "values(?,?,?,?,?,?);";
    private static final String SQL_FIND_ORDER_BY_USER_ID = "select * from orders where account_id=?;";
    private static final String SQL_FIND_ALL_ORDERS = "select * from orders;";
    private static final String SQL_FIND_ORDER_BY_ID = "select * from orders where id=?;";
    private static final String SQL_COUNT_NOT_AVAILABLE_CARS =
            "select sum(numb_of_cars) from orders where(rent_from=? or rent_to=?) and car_total_id=?;";
    private static final String SQL_COUNT_NOT_AVAILABLE_DRIVERS =
            "select sum(numb_of_drivers) from orders where(rent_from=? or rent_to=?) and car_total_id=?;";

    private final DBManager dbManager;

    public OrderDao() {
        dbManager = DBManager.getInstance();
    }

    public void insertOrder(Order order) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertOrder(con, order);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(con);
        }
    }

    public void insertOrder(Connection con, Order order) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setDate(z++, order.getRentFrom());
        prepSt.setDate(z++, order.getRentTo());
        prepSt.setInt(z++, order.getCarTotal().getId());
        prepSt.setInt(z++, order.getClient().getId());
        prepSt.setInt(z++, order.getNumbOfDrivers());
        prepSt.setInt(z, order.getNumbOfCars());
        if (prepSt.executeUpdate() > 0) {
            getGeneratedId(prepSt, order);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, Order order) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            order.setId(rs.getInt(1));
        }
        rs.close();
    }

    public int countNotAvailableDrivers(Order order) {
        int sum = 0;
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_COUNT_NOT_AVAILABLE_DRIVERS);
            int z = 1;
            prepSt.setDate(z++, order.getRentFrom());
            prepSt.setDate(z++, order.getRentTo());
            prepSt.setInt(z, order.getCarTotal().getId());
            rs = prepSt.executeQuery();
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return sum;
    }

    public int countNotAvailableCars(Order order) {
        int sum = 0;
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_COUNT_NOT_AVAILABLE_CARS);
            int z = 1;
            prepSt.setDate(z++, order.getRentFrom());
            prepSt.setDate(z++, order.getRentTo());
            prepSt.setInt(z, order.getCarTotal().getId());
            rs = prepSt.executeQuery();
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return sum;
    }

    public List<Order> findOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_ORDER_BY_USER_ID);
            prepSt.setInt(1, user.getId());
            rs = prepSt.executeQuery();
            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setClient(user);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return orders;
    }

    public Order findOrderById(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        Order order = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_ORDER_BY_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                order = mapOrder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return order;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt(ENTITY_ID));
        order.setRentFrom(rs.getDate(ORDER_RENT_FROM));
        order.setRentTo(rs.getDate(ORDER_RENT_TO));
        order.setCarTotal(new CarTotal());
        order.getCarTotal().setId(rs.getInt(ORDER_CAR_TOTAL_ID));
        order.setClient(new User());
        order.getClient().setId(rs.getInt(ORDER_ACCOUNT_ID));
        order.setNumbOfDrivers(rs.getInt(ORDER_NUMB_OF_DRIVERS));
        order.setNumbOfCars(rs.getInt(ORDER_NUMB_OF_CARS));
        return order;
    }
}

