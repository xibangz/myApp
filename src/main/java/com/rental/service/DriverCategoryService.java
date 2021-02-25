package com.rental.service;

import com.rental.bean.DriverCategory;
import com.rental.dao.DriverCategoryDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.util.List;

public class DriverCategoryService implements Serializable {
    private static final long serialVersionUID = 2665928613348280622L;
    private final DriverCategoryDao driveCatDao = new DriverCategoryDao();

    public DriverCategory findDriverCatById(int id) throws DBException {
        return driveCatDao.findDriverCategoryById(id);
    }

    public List<DriverCategory> findDriverCats() throws DBException {
        return driveCatDao.findDriverCategories();
    }

    public void updateCatPrice(DriverCategory category) throws DBException {
        driveCatDao.updateDriverCategoryPrice(category);
    }

    public void insertDriverCat(DriverCategory category) throws DBException {
        driveCatDao.insertDriverCategory(category);
    }

    public void deleteDriverCat(int id) throws DBException {
        driveCatDao.deleteDriverCategory(id);
    }

    public List<DriverCategory> findAllDriverCats() throws DBException {
        return driveCatDao.findAllDriverCats();
    }

    public DriverCategory findDriverCatByName(String name) throws DBException {
        return driveCatDao.findDriverCategoryByName(name);
    }
}
