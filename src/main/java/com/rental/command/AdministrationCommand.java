package com.rental.command;

import com.rental.Path;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdministrationCommand extends Command {
    private static final long serialVersionUID = -4384287738391237105L;

    private DriverCategoryService driverCatServ;
    private CarTotalService carTotalServ;
    private CarService carServ;
    private DriverService driverServ;
    private UserService userServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String page = null;
        HttpSession session = req.getSession();
        initServices(req);

        switch (req.getParameter("admin")) {
            case "Cars":
                session.setAttribute("carTotalsList", new CarTotalService().findAllCars());
                session.setAttribute("carsList", new CarService().findAllCars());
                page = Path.ADMIN_CARS_PAGE;
                break;
            case "Users":
                session.setAttribute("usersList", new UserService().findAllUsers());
                page = Path.ADMIN_USERS_PAGE;
                break;
            case "Drivers":
                session.setAttribute("driversList", new DriverService().findAllDrivers());
                session.setAttribute("driverCatsList", new DriverCategoryService().findAllDriverCats());
                page = Path.ADMIN_DRIVERS_PAGE;
                break;
            default:
        }
        return page;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        driverCatServ = (DriverCategoryService) context.getAttribute("driverCatServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        carServ = (CarService) context.getAttribute("carServ");
        driverServ = (DriverService) context.getAttribute("driverServ");
        userServ = (UserService) context.getAttribute("usrServ");
    }
}
