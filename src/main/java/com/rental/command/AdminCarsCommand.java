package com.rental.command;

import com.rental.Path;
import com.rental.bean.Car;
import com.rental.bean.CarTotal;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminCarsCommand extends Command {
    private static final long serialVersionUID = 707589706349736617L;

    private DriverCategoryService driverCatServ;
    private CarTotalService carTotalServ;
    private CarService carServ;


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);


        showUpdateItem(req.getParameter("showUpdate"), req);
        addCarTotal(req.getParameter("addCarTotal"), req);
        addCar(req.getParameter("addCar"), req);
        updateCarTotal(req.getParameter("updateCarTotal"), req);
        deleteCarTotal(req.getParameter("deleteCarTotal"));
        deleteCar(req.getParameter("deleteCar"));

        return Path.ADMIN_CARS_PAGE_REDIRECT;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        driverCatServ = (DriverCategoryService) context.getAttribute("driverCatServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
        carServ = (CarService) context.getAttribute("carServ");
    }

    private void showUpdateItem(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<CarTotal> list = (List<CarTotal>) req.getSession().getAttribute("carTotalsList");
            req.getSession().setAttribute("updateItemTotal", list.get(index));
        }
    }

    private void addCarTotal(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            CarTotal total = new CarTotal();
            total.setBrand(req.getParameter("totalBrand"));
            total.setModel(req.getParameter("totalModel"));
            total.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
            total.setPhoto(req.getParameter("totalPhoto"));
            total.setDriverCat(
                    driverCatServ.findDriverCatByName(
                            req.getParameter("totalDriver")));
            carTotalServ.insertCarTotal(total);
        }
    }

    private void addCar(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            Car car = new Car();
            car.setBrand(req.getParameter("carBrand"));
            car.setModel(req.getParameter("carModel"));
            car.setNumbers(req.getParameter("carNumbers"));
            car.setCarTotal(Integer.parseInt(req.getParameter("carTotalId")));
            carServ.insertCar(car);
            carTotalServ.updateQuantity(car.getCarTotal(), true);
        }
    }

    private void deleteCarTotal(String value) {
        if (value != null && !value.isEmpty()) {
            carTotalServ.deleteCarTotal(Integer.parseInt(value));
        }
    }

    private void deleteCar(String value) {
        if (value != null && !value.isEmpty()) {
            int id = Integer.parseInt(value);
            int carTotalId = carServ.findCarTotalId(id);
            carServ.deleteCar(id);
            carTotalServ.updateQuantity(carTotalId, false);
        }
    }

    private void updateCarTotal(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            CarTotal total = new CarTotal();
            total.setId(Integer.parseInt(req.getParameter("totalId")));
            total.setBrand(req.getParameter("totalBrand"));
            total.setModel(req.getParameter("totalModel"));
            total.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
            total.setPhoto(req.getParameter("totalPhoto"));
            total.setDriverCat(
                    driverCatServ.findDriverCatByName(
                            req.getParameter("totalDriver")));
            carTotalServ.updateCarTotal(total);
            req.getSession().removeAttribute("updateItemTotal");
        }
    }
}
