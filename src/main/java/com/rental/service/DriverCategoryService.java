package com.rental.service;

import com.rental.bean.DriverCategory;
import com.rental.dao.DriverCategoryDao;

import java.io.Serializable;
import java.util.List;

public class DriverCategoryService implements Serializable {
    private static final long serialVersionUID = 2665928613348280622L;
    private final DriverCategoryDao driveCatDao = new DriverCategoryDao();

    public DriverCategory findDriverCatById(int id) {
        return driveCatDao.findDriverCategoryById(id);
    }

    public List<DriverCategory> findDriverCats() {
        return driveCatDao.findDriverCategories();
    }

    public boolean updateCatPrice(DriverCategory category) {
        return driveCatDao.updateDriverCategoryPrice(category);
    }

    public boolean insertDriverCat(DriverCategory category) {
        return driveCatDao.insertDriverCategory(category);
    }

    public boolean deleteDriverCat(int id) {
        return driveCatDao.deleteDriverCategory(id);
    }

    public List<DriverCategory> findAllDriverCats() {
        return driveCatDao.findAllDriverCats();
    }

    public DriverCategory findDriverCatByName(String name) {
        return driveCatDao.findDriverCategoryByName(name);
    }
}
