package com.rental.command;

import com.rental.Path;

import com.rental.exception.DBException;
import com.rental.service.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class ManagementCommand extends Command {
    private static final long serialVersionUID = -7379460165573952666L;

    private OrderTotalService orderTotalServ;
    private CarService carServ;
    private DriverService driverServ;
    private UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        String page = null;
        HttpSession session = req.getSession();
        try {
            switch (req.getParameter("manage")) {
                case "Orders":
                    session.setAttribute("orderTotalsList", orderTotalServ.findAllOrderTotals());
                    page = Path.MANAGE_ORDERS_PAGE;
                    break;
                case "Drivers&Cars":
                    session.setAttribute("driversList", driverServ.findAllDrivers());
                    session.setAttribute("carsList", carServ.findAllCars());
                    page = Path.MANAGE_DRIVERS_CARS_PAGE;
                    break;
                case "Users":
                    session.setAttribute("usersList", userServ.findAllClients());
                    page = Path.MANAGE_USERS_PAGE;
                    break;
                default:
            }
        }catch (DBException e){
            session.setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }
        return page;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        orderTotalServ = (OrderTotalService) context.getAttribute("orderTotalServ");
        carServ = (CarService) context.getAttribute("carServ");
        driverServ = (DriverService) context.getAttribute("driverServ");
        userServ=(UserService)context.getAttribute("userServ");
    }
}
