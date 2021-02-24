package com.rental.service;

import com.rental.bean.CarTotal;
import com.rental.bean.Driver;
import com.rental.dao.DriverDao;

import java.io.Serializable;
import java.util.List;

public class DriverService implements Serializable {
    private static final long serialVersionUID = 6671524060703830970L;
    private final DriverDao driverDao = new DriverDao();
    private final DriverCategoryService driverCatService = new DriverCategoryService();

    public List<Driver> findAllDrivers() {
        return setDriverCategory(driverDao.findAllDrivers());
    }

    public boolean insertDriver(Driver driver) {
        return driverDao.insertDriver(driver);
    }

    public boolean deleteDriver(int id) {
        return driverDao.deleteDriver(id);
    }

    public void updateDriverOk(Driver driver) {
        driverDao.updateDriverOk(driver);
    }

    public List<Driver> setDriverCategory(List<Driver> list) {
        for (Driver driver : list) {
            int id = driver.getDriverCat().getId();
            driver.setDriverCat(driverCatService.findDriverCatById(id));
        }
        return list;
    }
}
