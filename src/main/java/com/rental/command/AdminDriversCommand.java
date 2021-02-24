package com.rental.command;

import com.rental.Path;
import com.rental.bean.Driver;
import com.rental.bean.DriverCategory;
import com.rental.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminDriversCommand extends Command {
    private static final long serialVersionUID = -2131754735702599293L;

    private DriverService driverServ;
    private DriverCategoryService driverCatServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        initServices(req);

        showUpdateItem(req.getParameter("showUpdate"), req);
        addDriver(req.getParameter("addDriver"), req);
        deleteDriver(req.getParameter("deleteDriver"));
        insertDriverCat(req.getParameter("addDriverCat"), req);
        updateDriverCat(req.getParameter("updateDriverCat"), req);
        deleteDriverCat(req.getParameter("deleteDriverCat"));

        return Path.ADMIN_DRIVERS_PAGE_REDIRECT;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        driverCatServ = (DriverCategoryService) context.getAttribute("driverCatServ");
        driverServ = (DriverService) context.getAttribute("driverServ");
    }

    private void showUpdateItem(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            int index = Integer.parseInt(value);
            List<DriverCategory> list = (List<DriverCategory>) req.getSession().getAttribute("driverCatsList");
            req.getSession().setAttribute("updateItem", list.get(index));
        }
    }

    private void addDriver(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            Driver driver = new Driver();
            List<DriverCategory> a = (List<DriverCategory>) req.getSession().getAttribute("driverCatsList");
            driver.setDriverCat(findDriverCategory(a, req.getParameter("driverCat")));
            driver.setName(req.getParameter("driverName"));
            driver.setDriverLicense(req.getParameter("driverLicense"));
            driverServ.insertDriver(driver);
        }
    }

    private DriverCategory findDriverCategory(List<DriverCategory> list, String value) {
        DriverCategory category = null;
        for (DriverCategory cat : list) {
            if (cat.getName().equalsIgnoreCase(value)) {
                category = cat;
            }
        }
        return category;
    }

    private void deleteDriver(String value) {
        if (value != null && !value.isEmpty()) {
            driverServ.deleteDriver(Integer.parseInt(value));
        }
    }

    private void insertDriverCat(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            DriverCategory category = new DriverCategory();
            category.setName(req.getParameter("driverCatName"));
            category.setPrice(Integer.parseInt(req.getParameter("driverCatPrice")));
            driverCatServ.insertDriverCat(category);
        }
    }

    private void deleteDriverCat(String value) {
        if (value != null && !value.isEmpty()) {
            driverCatServ.deleteDriverCat(Integer.parseInt(value));
        }
    }

    private void updateDriverCat(String value, HttpServletRequest req) {
        if (value != null && !value.isEmpty()) {
            DriverCategory category = new DriverCategory();
            category.setId(Integer.parseInt(req.getParameter("driverCatId")));
            category.setPrice(Integer.parseInt(req.getParameter("driverCatPrice")));
            driverCatServ.updateCatPrice(category);
            req.getSession().removeAttribute("updateItem");
        }
    }
}
