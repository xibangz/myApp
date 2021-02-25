package com.rental.command;

import com.rental.Path;
import com.rental.bean.*;
import com.rental.dao.Fields;
import com.rental.exception.DBException;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManageOrdersCommand extends Command {
    private static final long serialVersionUID = 9209666089368240727L;

    private CarTotalService carTotalServ;
    private UserService userServ;
    private OrderTotalService orderTotalServ;
    private OrderService orderServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        showRejectDesc(req.getParameter("showReject"), req);
        showPenalty(req.getParameter("showPenalty"), req);
        try {
            showUpdateItem(req.getParameter("showUpdate"), req);
            rejectOrder(req.getParameter("rejectStatus"), req);
            confirmOrder(req.getParameter("confirmStatus"));
            addPenalty(req.getParameter("addPenalty"), req);
        }catch (DBException e){
            req.getSession().setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }


        return Path.MANAGE_ORDERS_PAGE_REDIRECT;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        userServ = (UserService) context.getAttribute("userServ");
        orderServ = (OrderService) context.getAttribute("orderServ");
    }

    private void showPenalty(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            req.getSession().setAttribute("showPenalty", value);
        }
    }

    private void addPenalty(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            OrderTotal total = (OrderTotal) req.getSession().getAttribute("updateItem");
            OrderTotal penalty = new OrderTotal();
            penalty.setOrder(total.getOrder());
            penalty.setSum(Integer.parseInt(req.getParameter("orderPenaltySum")));
            penalty.setDescription(req.getParameter("orderPenaltyDesc"));
            penalty.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_CONFIRMED_ID);
            penalty.setPenalty(true);
            orderTotalServ.insertOrderTotal(penalty);
        }
    }

    private void confirmOrder(String value) throws DBException {
        if (value != null && !value.isEmpty()) {
            OrderTotal total = new OrderTotal();
            total.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_CONFIRMED_ID);
            total.setId(Integer.parseInt(value));
            total.setDescription("");
            orderTotalServ.updateOrderTotalStatus(total);
        }
    }

    private void rejectOrder(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            OrderTotal total = new OrderTotal();
            total.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_REJECTED_ID);
            total.setId(Integer.parseInt(value));
            total.setDescription(req.getParameter("orderTotalDesc"));
            orderTotalServ.updateOrderTotalStatus(total);
            req.getSession().removeAttribute("updateReject");
        }
    }

    private void showUpdateItem(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            OrderTotal total = orderTotalServ.findOrderTotalById(
                    Integer.parseInt(value));
            Order order = orderServ.findOrder(total);
            CarTotal car = carTotalServ.findCarTotalById(order.getCarTotal().getId());
            User user = userServ.findUser(order);
            order.setCarTotal(car);
            order.setClient(user);
            total.setOrder(order);
            req.getSession().setAttribute("updateItem", total);
        }
    }

    private void showRejectDesc(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<OrderTotal> list = (List<OrderTotal>) req.getSession().getAttribute("orderTotalsList");
            req.getSession().setAttribute("updateReject", list.get(index));
        }
    }
}
