package com.rental.service;

import com.rental.bean.Driver;
import com.rental.dao.DriverDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.util.List;

public class DriverService implements Serializable {
    private static final long serialVersionUID = 6671524060703830970L;
    private final DriverDao driverDao = new DriverDao();
    private final DriverCategoryService driverCatService = new DriverCategoryService();

    public List<Driver> findAllDrivers() throws DBException {
        return setDriverCategory(driverDao.findAllDrivers());
    }

    public void insertDriver(Driver driver) throws DBException {
        driverDao.insertDriver(driver);
    }

    public void deleteDriver(int id) throws DBException {
        driverDao.deleteDriver(id);
    }

    public void updateDriverOk(Driver driver) throws DBException {
        driverDao.updateDriverOk(driver);
    }

    public List<Driver> setDriverCategory(List<Driver> list) throws DBException {
        for (Driver driver : list) {
            int id = driver.getDriverCat().getId();
            driver.setDriverCat(driverCatService.findDriverCatById(id));
        }
        return list;
    }
}
