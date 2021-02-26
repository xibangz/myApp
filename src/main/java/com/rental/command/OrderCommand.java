package com.rental.command;

import com.rental.Path;
import com.rental.bean.*;
import com.rental.dao.DBManager;
import com.rental.dao.Fields;
import com.rental.exception.DBException;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class OrderCommand extends Command {
    private static final long serialVersionUID = -6621078481346239191L;

    private DriverCategoryService driverCatServ;
    private CarTotalService carTotalServ;
    private OrderTotalService orderTotalServ;
    private OrderService orderServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        OrderPageInfoContent content = new OrderPageInfoContent();
        OrderTotal total = (OrderTotal) session.getAttribute("orderTotal");

        if (req.getParameter("back") != null) {
            sessionRemove(session);
            return Path.CARS_LIST_PAGE;
        }
        try {
            if (total != null) {
                insertTotal(total, session);
                return Path.USER_ORDERS_PAGE_REDIRECT;
            }
            if (order == null) {
                order = createNewOrder(req, content);
                if (orderCheck(order)) {
                    total = createNewOrderTotal(order);
                }
            } else {
                if (orderCheck(order)) {
                    total = createNewOrderTotal(order);
                } else {
                    updateOrder(req, order, content);
                    total = orderCheck(order) ? createNewOrderTotal(order) : null;
                }
            }
        }catch (DBException e){
            req.getSession().setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }

        session.setAttribute("order", order);
        session.setAttribute("orderInfo", content);
        session.setAttribute("orderTotal", total);

        return Path.USER_ORDER_CONFIRM;
    }

    private void sessionRemove(HttpSession session) {
        session.removeAttribute("order");
        session.removeAttribute("orderTotal");
        session.removeAttribute("orderInfo");
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        orderServ = (OrderService) context.getAttribute("orderServ");
        driverCatServ = (DriverCategoryService) context.getAttribute("driverCatServ");
    }

    private void insertTotal(OrderTotal total, HttpSession session) throws DBException {
        Connection con=null;
        try {
            con = DBManager.getInstance().startTransaction();
            orderServ.insertOrder(total.getOrder(), con);
            orderTotalServ.insertOrderTotal(con, total);
            DBManager.getInstance().commitTransaction(con);
        } catch (SQLException e) {
            DBManager.getInstance().rollbackTransaction(con);
            throw new DBException(e.getMessage(),e);
        } finally {
            sessionRemove(session);
            DBManager.getInstance().close(con);
        }
    }

    private Order createNewOrder(HttpServletRequest req, OrderPageInfoContent content) throws DBException {
        Order order = new Order();
        int totalId = Integer.parseInt(req.getParameter("carTotal"));
        String rentFromValue = req.getParameter(totalId + "rentFrom");
        String rentToValue = req.getParameter(totalId + "rentTo");
        String numbOfCarsValue = req.getParameter(totalId + "numbOfCars");
        String numbOfDriversValue = req.getParameter(totalId + "numbOfDrivers");
        User clientValue = (User) req.getSession().getAttribute("user");

        rentFromCheck(rentFromValue, order, content);
        rentToCheck(rentToValue, order, content);
        numbOfCarsCheck(numbOfCarsValue, order, totalId, content);
        numbOfDriversCheck(numbOfDriversValue, order, content);
        userCheck(clientValue, order, content);

        return order;
    }

    private void updateOrder(HttpServletRequest req, Order order, OrderPageInfoContent content) throws DBException {
        String rentFromValue = req.getParameter("rentFrom");
        String rentToValue = req.getParameter("rentTo");
        String numbOfCarsValue = req.getParameter("numbOfCars");
        String numbOfDriversValue = req.getParameter("numbOfDrivers");
        User clientValue = (User) req.getSession().getAttribute("user");

        rentFromCheck(rentFromValue, order, content);
        rentToCheck(rentToValue, order, content);
        numbOfCarsCheck(numbOfCarsValue, order, order.getCarTotal().getId(), content);
        numbOfDriversCheck(numbOfDriversValue, order, content);
        userCheck(clientValue, order, content);
    }

    private boolean orderCheck(Order order) {
        return order.getNumbOfCars() != -1
                && order.getNumbOfDrivers() != -1
                && order.getRentFrom() != null
                && order.getRentTo() != null
                && order.getCarTotal() != null
                && order.getClient() != null;
    }

    private OrderTotal createNewOrderTotal(Order order) {
        return setOrderTotal(order);
    }

    private OrderTotal setOrderTotal(Order order) {
        OrderTotal total = new OrderTotal();
        total.setOrder(order);
        total.setSum(count(order));
        total.setOrderStatusId(Fields.ORDER_TOTAL_STATUS_DEFAULT_ID);
        return total;
    }

    private int count(Order order) {
        long diff = Math.abs(order.getRentTo().getTime() - order.getRentFrom().getTime());
        int countDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return (order.getNumbOfCars()
                * order.getCarTotal().getPrice()
                + order.getNumbOfDrivers()
                * order.getCarTotal().getDriverCat().getPrice())
                * countDays;

    }

    private void userCheck(User user, Order order, OrderPageInfoContent content) {
        if (user == null) {
            content.setUserPassportInfo("Don't play with me!!!");
            return;
        }
        if (user.getPassport() == null) {
            content.setUserPassportInfo("Passport can't be empty!");
            return;
        }
        order.setClient(user);
    }

    private void numbOfDriversCheck(String value, Order order, OrderPageInfoContent content) throws DBException {
        if (value == null || value.isEmpty()) {
            return;
        }
        int numb = Integer.parseInt(value);
        if (order.getNumbOfDrivers() != numb) {
            if (numb < 0) {
                content.setNumbOfDriversInfo("Number of drivers can't be less than zero!");
                order.setNumbOfDrivers(-1);
                return;
            }
            DriverCategory driverCat =
                    driverCatServ.findDriverCatById(
                            order.getCarTotal().getDriverCat().getId());
            if (numb > driverCat.getQuantity()) {
                content.setNumbOfDriversInfo("" +
                        "Not enough drivers! Max quantity of drivers - " + driverCat.getQuantity());
                order.setNumbOfDrivers(-1);
                return;
            }
            int notAvailableDrivers = orderServ.countNotAvailableDrivers(order);
            if (numb > (driverCat.getQuantity() - notAvailableDrivers)) {
                content.setNumbOfDriversInfo("Not enough drivers! Max available quantity of drivers at this dates - "
                        + (driverCat.getQuantity() - notAvailableDrivers));
                order.setNumbOfDrivers(-1);
                return;
            }
            order.setNumbOfDrivers(numb);
            order.getCarTotal().setDriverCat(driverCat);
        }
    }

    private void numbOfCarsCheck(String value, Order order, int id, OrderPageInfoContent content) throws DBException {
        if (value == null || value.isEmpty()) {
            content.setNumbOfDriversInfo("Number of cars can't be empty!");
            return;
        }
        int numb = Integer.parseInt(value);
        if (order.getNumbOfCars() != numb) {
            if (numb < 1) {
                content.setNumbOfCarsInfo("Number of cars can't be less than one!");
                order.setNumbOfCars(-1);
                return;
            }
            CarTotal total =
                    carTotalServ.findCarTotalById(id);
            order.setCarTotal(total);
            if (numb > total.getQuantity()) {
                content.setNumbOfCarsInfo("Not enough cars! Max quantity of cars - " + total.getQuantity());
                order.setNumbOfCars(-1);
                return;
            }
            int notAvailableCars = orderServ.countNotAvailableCars(order);
            if (numb > (total.getQuantity() - notAvailableCars)) {
                content.setNumbOfCarsInfo("Not enough cars! Max available quantity of cars at this dates - "
                        + (total.getQuantity() - notAvailableCars));
                order.setNumbOfCars(-1);
                return;
            }
            order.setNumbOfCars(numb);
        }
    }

    private void rentToCheck(String value, Order order, OrderPageInfoContent content) {
        if (value == null || value.isEmpty()) {
            content.setNumbOfDriversInfo("'Rent to' date can't be empty!");
            return;
        }
        Date date = Date.valueOf(value);
        if (!date.equals(order.getRentTo())) {
            if (!dateCheck(date)) {
                content.setRentToInfo("'End' date must be between current date and current date + 3 months!");
                return;
            }
            if (order.getRentFrom() != null && !dateCheck(order.getRentFrom(), date)) {
                content.setRentToInfo("'End' date can't be bigger than 'from' date!");
                return;
            }
            order.setRentTo(date);
        }
    }

    private void rentFromCheck(String value, Order order, OrderPageInfoContent content) {
        if (value == null || value.isEmpty()) {
            content.setNumbOfDriversInfo("'Rent from' date can't be empty!");
            return;
        }
        Date date = Date.valueOf(value);
        if (!date.equals(order.getRentFrom())) {
            if (!dateCheck(date)) {
                content.setRentFromInfo("'From' date must be between current date and current date + 3 months!");
                return;
            }
            order.setRentFrom(date);
        }
    }

    private boolean dateCheck(Date value) {
        Date currDate = Date.valueOf(LocalDate.now());
        Date upperDate = Date.valueOf(LocalDate.now().plusMonths(3));
        long diff = value.getTime() - currDate.getTime();
        long diff2 = upperDate.getTime() - value.getTime();
        return diff >= 0 && diff2 >= 0;
    }

    private boolean dateCheck(Date from, Date to) {
        long diff = to.getTime() - from.getTime();
        return diff > 0;
    }
}
