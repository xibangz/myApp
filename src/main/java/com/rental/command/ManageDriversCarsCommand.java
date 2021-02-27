package com.rental.command;

import com.rental.Path;
import com.rental.bean.Car;
import com.rental.bean.Driver;
import com.rental.dao.DBManager;
import com.rental.exception.DBException;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManageDriversCarsCommand extends Command {
    private static final long serialVersionUID = -7695273283101657456L;

    private CarTotalService carTotalServ;
    private CarService carServ;
    private DriverService driverServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);
        try {
            carIsNotOk(req.getParameter("carNotOk"), req);
            carIsOk(req.getParameter("carOk"), req);
            driverIsNotOk(req.getParameter("driverNotOk"));
            driverIsOk(req.getParameter("driverOk"));
        }catch (DBException e){
            req.getSession().setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }

        return Path.MANAGE_DRIVERS_CARS_PAGE_REDIRECT;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        carServ = (CarService) context.getAttribute("carServ");
        driverServ = (DriverService) context.getAttribute("driverServ");
    }

    private void carIsOk(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<Car> list = (List<Car>) req.getSession().getAttribute("carsList");
            Car car = list.get(index);
            car.setOk(true);
            updateCarTransaction(car);
        }
    }

    private void carIsNotOk(String value, HttpServletRequest req) throws DBException {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<Car> list = (List<Car>) req.getSession().getAttribute("carsList");
            Car car = list.get(index);
            car.setOk(false);
            updateCarTransaction(car);
        }
    }

    private void updateCarTransaction(Car car) throws DBException {
        Connection con = null;
        try {
            con= DBManager.getInstance().startTransaction();
            carServ.updateCarOk(car,con);
            carTotalServ.updateQuantity(car.getCarTotal(), car.isOk(),con);
            DBManager.getInstance().commitTransaction(con);
        } catch (SQLException e) {
            DBManager.getInstance().rollbackTransaction(con);
            throw new DBException(e.getMessage(),e);
        }finally {
            DBManager.getInstance().close(con);
        }
    }

    private void driverIsOk(String value) throws DBException {
        if (value != null && !value.isEmpty()) {
            Driver driver = new Driver();
            driver.setId(Integer.parseInt(value));
            driver.setOk(true);
            driverServ.updateDriverOk(driver);
        }
    }

    private void driverIsNotOk(String value) throws DBException {
        if (value != null && !value.isEmpty()) {
            Driver driver = new Driver();
            driver.setId(Integer.parseInt(value));
            driver.setOk(false);
            driverServ.updateDriverOk(driver);
        }
    }
}
