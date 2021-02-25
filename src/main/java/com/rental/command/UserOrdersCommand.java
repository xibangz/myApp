package com.rental.command;

import com.rental.Path;
import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.bean.User;
import com.rental.dao.DBManager;
import com.rental.dao.Fields;
import com.rental.exception.DBException;
import com.rental.service.CarTotalService;
import com.rental.service.OrderService;
import com.rental.service.OrderTotalService;
import com.rental.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserOrdersCommand extends Command {
    private static final long serialVersionUID = -5262573657826192359L;

    private OrderTotalService orderTotalServ;
    private OrderService orderServ;
    private UserService userServ;
    private CarTotalService carTotalServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        try {
            req.getSession().setAttribute("orderTotalList", getUserTotals(req));
            if (checkStatus(req.getParameter("status"), req)) {
                return Path.USER_ORDERS_PAGE_REDIRECT;
            }
        }catch (DBException e){
            req.getSession().setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }

        return Path.USER_ORDERS_PAGE;
    }

    private boolean checkStatus(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<OrderTotal> list = (List<OrderTotal>) req.getSession().getAttribute("orderTotalList");
            OrderTotal total = list.get(index);
            total.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_COMPLETED_ID);
            if (!total.isPenalty()) {
                updateTotalAndUserTransaction(total,req);
                return true;
            }
            orderTotalServ.updateOrderTotalStatus(total);
            return true;
        }
        return false;
    }

    private void updateTotalAndUserTransaction(OrderTotal total,HttpServletRequest req) throws DBException {
        Connection con = null;
        try {
            con = DBManager.getInstance().startTransaction();
            orderTotalServ.updateOrderTotalStatus(total, con);
            updateUserAmount(total, req, con);
            DBManager.getInstance().commitTransaction(con);
        } catch (SQLException e) {
            DBManager.getInstance().rollbackTransaction(con);
            throw new DBException(e.getMessage(),e);
        }finally {
            DBManager.getInstance().close(con);
        }
    }

    private void updateUserAmount(OrderTotal total, HttpServletRequest req, Connection con) throws DBException {
        User user = (User) req.getSession().getAttribute("user");
        user.setAmount(user.getAmount() + total.getSum());
        userServ.updateUserAmount(user, con);
    }

    private List<OrderTotal> getUserTotals(HttpServletRequest req) throws DBException {
        List<Order> orders =
                orderServ.findOrdersByUser((User) req.getSession().getAttribute("user"));
        orderServ.setCarTotal(orders, carTotalServ);
        return orderTotalServ.findAllUserTotals(orders);
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        orderServ = (OrderService) context.getAttribute("orderServ");
        userServ = (UserService) context.getAttribute("userServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
    }
}
