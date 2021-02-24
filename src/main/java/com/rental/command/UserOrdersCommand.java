package com.rental.command;

import com.rental.Path;
import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.bean.User;
import com.rental.service.CarTotalService;
import com.rental.service.OrderService;
import com.rental.service.OrderTotalService;
import com.rental.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserOrdersCommand extends Command {
    private static final long serialVersionUID = -5262573657826192359L;

    private OrderTotalService orderTotalServ;
    private OrderService orderServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        req.getSession().setAttribute("orderTotalList", getUserTotals(req));

        return Path.USER_ORDERS_PAGE;
    }

    private List<OrderTotal> getUserTotals(HttpServletRequest req) {
        List<Order> orders =
                orderServ.findOrdersByUserId((User) req.getSession().getAttribute("user"));
        return orderTotalServ.findAllUserTotals(orders);
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        orderServ = (OrderService) context.getAttribute("orderServ");
    }
}
