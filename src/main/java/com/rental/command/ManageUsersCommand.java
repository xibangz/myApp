package com.rental.command;

import com.rental.Path;
import com.rental.bean.Order;
import com.rental.bean.OrderTotal;
import com.rental.bean.User;
import com.rental.dao.Fields;
import com.rental.service.CarTotalService;
import com.rental.service.OrderService;
import com.rental.service.OrderTotalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManageUsersCommand extends Command{
    private static final long serialVersionUID = 1188563696551247727L;

    private OrderService orderServ;
    private OrderTotalService orderTotalServ;
    private CarTotalService carTotalServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        showUserOrders(req.getParameter("userOrders"),req);
        showPenalty(req.getParameter("showOrderPenalty"),req);
        addPenalty(req.getParameter("addOrderPenalty"),req);
        return Path.MANAGE_USERS_PAGE_REDIRECT;
    }

    private void showUserOrders(String value,HttpServletRequest req){
        if(value!=null &&!value.isEmpty()){
            int index = Integer.parseInt(value);
            List<User> list = (List<User>) req.getSession().getAttribute("usersList");
            User user = list.get(index);
            List<Order> orders = orderServ.findOrdersByUser(user);
            orderServ.setCarTotal(orders,carTotalServ);
            req.getSession().setAttribute("ordersList",orders);
        }
    }

    private void showPenalty(String value,HttpServletRequest req){
        if(value!=null &&!value.isEmpty()){
            req.setAttribute("orderId",value);
        }
    }
    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        orderServ = (OrderService) context.getAttribute("orderServ");
        carTotalServ=(CarTotalService) context.getAttribute("carTotalServ");
    }
    private void addPenalty(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            Order order = new Order();
            order.setId(Integer.parseInt(value));
            OrderTotal penalty = new OrderTotal();
            penalty.setOrder(order);
            penalty.setSum(Integer.parseInt(req.getParameter("orderPenaltySumSec")));
            penalty.setDescription(req.getParameter("orderPenaltyDescSec"));
            penalty.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_CONFIRMED_ID);
            penalty.setPenalty(true);
            orderTotalServ.insertOrderTotal(penalty);
            req.getSession().removeAttribute("orderId");
        }
    }
}
