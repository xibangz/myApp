package com.rental.command;

import com.rental.Path;
import com.rental.bean.Car;
import com.rental.bean.Driver;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManageDriversCarsCommand extends Command {
    private static final long serialVersionUID = -7695273283101657456L;

    private CarTotalService carTotalServ;
    private CarService carServ;
    private DriverService driverServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        carIsNotOk(req.getParameter("carNotOk"), req);
        carIsOk(req.getParameter("carOk"), req);
        driverIsNotOk(req.getParameter("driverNotOk"));
        driverIsOk(req.getParameter("driverOk"));

        return Path.MANAGE_DRIVERS_CARS_PAGE_REDIRECT;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        carServ = (CarService) context.getAttribute("carServ");
        driverServ = (DriverService) context.getAttribute("driverServ");
    }

    private void carIsOk(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<Car> list = (List<Car>) req.getSession().getAttribute("carsList");
            Car car = list.get(index);
            car.setOk(true);
            carServ.updateCarOk(car);
            carTotalServ.updateQuantity(car.getCarTotal(), true);
        }
    }

    private void carIsNotOk(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<Car> list = (List<Car>) req.getSession().getAttribute("carsList");
            Car car = list.get(index);
            car.setOk(false);
            carServ.updateCarOk(car);
            carTotalServ.updateQuantity(car.getCarTotal(), false);
        }
    }

    private void driverIsOk(String value) {
        if (value != null && !value.isEmpty()) {
            Driver driver = new Driver();
            driver.setId(Integer.parseInt(value));
            driver.setOk(true);
            driverServ.updateDriverOk(driver);
        }
    }

    private void driverIsNotOk(String value) {
        if (value != null && !value.isEmpty()) {
            Driver driver = new Driver();
            driver.setId(Integer.parseInt(value));
            driver.setOk(false);
            driverServ.updateDriverOk(driver);
        }
    }
}
