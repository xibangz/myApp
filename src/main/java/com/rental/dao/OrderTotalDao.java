package com.rental.dao;

import com.rental.bean.Order;
import com.rental.bean.OrderTotal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

public class OrderTotalDao {
    private static final String SQL_INSERT_ORDER_TOTAL =
            "insert into order_total(sum,is_penalty,orders_id,order_status_id,description)" +
                    "values(?,?,?,?,?);";
    private static final String SQL_FIND_ORDER_TOTAL_BY_ORDER_ID = "select * from order_total where orders_id=?;";
    private static final String SQL_UPDATE_ORDER_TOTAL =
            "update order_total set order_status_id=?,description=? where id=?;";
    private static final String SQL_FIND_ALL_ORDER_TOTALS = "select * from order_total order by order_date desc;";
    private static final String SQL_FIND_ORDER_TOTAL_BY_ID = "select * from order_total where id=?;";

    private final DBManager dbManager;

    public OrderTotalDao() {
        dbManager = DBManager.getInstance();
    }

    public void insertOrderTotal(OrderTotal total) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertOrderTotal(con, total);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(con);
        }
    }

    public void insertOrderTotal(Connection con, OrderTotal total) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_ORDER_TOTAL, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setInt(z++, total.getSum());
        prepSt.setBoolean(z++, total.isPenalty());
        prepSt.setInt(z++, total.getOrder().getId());
        prepSt.setInt(z++, total.getOrderStatusId());
        prepSt.setString(z, total.getDescription());
        if (prepSt.executeUpdate() > 0) {
            getGeneratedId(prepSt, total);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, OrderTotal total) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            total.setId(rs.getInt(1));
        }
        rs.close();
    }

    public List<OrderTotal> findOrderTotalByOrder(Order order) {
        List<OrderTotal> totals = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_ORDER_TOTAL_BY_ORDER_ID);
            prepSt.setInt(1, order.getId());
            rs = prepSt.executeQuery();
            while (rs.next()) {
                OrderTotal total = mapOrderTotal(rs);
                total.setOrder(order);
                totals.add(total);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return totals;
    }

    public OrderTotal findOrderTotalByOrder(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        OrderTotal total = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_ORDER_TOTAL_BY_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                total = mapOrderTotal(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return total;
    }

    public List<OrderTotal> findAllOrderTotals() {
        List<OrderTotal> totals = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_ALL_ORDER_TOTALS);
            while (rs.next()) {
                totals.add(mapOrderTotal(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return totals;
    }

    public void updateOrderTotal(OrderTotal total) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            updateOrderTotal(con, total);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(con);
        }
    }

    public void updateOrderTotal(Connection con, OrderTotal total) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_UPDATE_ORDER_TOTAL);
        int z = 1;
        prepSt.setInt(z++, total.getOrderStatusId());
        prepSt.setString(z++, total.getDescription());
        prepSt.setInt(z, total.getId());
        prepSt.executeUpdate();
        prepSt.close();
    }

    private OrderTotal mapOrderTotal(ResultSet rs) throws SQLException {
        OrderTotal total = new OrderTotal();
        total.setId(rs.getInt(ENTITY_ID));
        total.setDescription(rs.getString(ORDER_TOTAL_DESCRIPTION));
        total.setSum(rs.getInt(ORDER_TOTAL_SUM));
        total.setPenalty(rs.getBoolean(ORDER_TOTAL_IS_PENALTY));
        total.setOrderStatusId(rs.getInt(ORDER_TOTAL_STATUS_ID));
        total.setOrder(new Order());
        total.getOrder().setId(rs.getInt(ORDER_TOTAL_ORDER_ID));
        total.setOrderDate(rs.getTimestamp(ORDER_TOTAL_ORDER_DATE));
        return total;
    }
}
