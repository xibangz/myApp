package com.rental.service;

import com.rental.bean.CarTotal;
import com.rental.bean.ProductPageContent;
import com.rental.dao.CarTotalDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.sql.Connection;
import java.util.*;

public class CarTotalService implements Serializable {
    private static final long serialVersionUID = 7254865682017987266L;
    private final CarTotalDao carTotalDao = new CarTotalDao();
    private final DriverCategoryService driverCatService = new DriverCategoryService();

    public void insertCarTotal(CarTotal total) throws DBException {
        carTotalDao.insertCarTotal(total);
    }

    public void deleteCarTotal(int id) throws DBException {
        carTotalDao.deleteCarTotal(id);
    }

    public void updateCarTotal(CarTotal total) throws DBException {
        carTotalDao.updateCarTotal(total);
    }

    public void updateQuantity(int id, boolean positiveNumb, Connection con) throws DBException {
        carTotalDao.updateQuantity(id, positiveNumb, con);
    }

    public List<CarTotal> findAllCars() throws DBException {
        return setDriverCategory(carTotalDao.findAllCars());
    }

    public Set<String> findAllBrands() throws DBException {
        return carTotalDao.findAllBrands();
    }

    public List<CarTotal> findSelectedCars(ProductPageContent content) throws DBException {
        return carTotalDao.findSelectedCars(content);
    }

    public int findMaxPrice() throws DBException {
        return carTotalDao.findMaxPrice();
    }

    public int countAllCarsTotals() throws DBException {
        return carTotalDao.countAllCarTotals();
    }

    public CarTotal findCarTotalById(int id) throws DBException {
        return carTotalDao.findCarTotalById(id);
    }

    public List<CarTotal> setDriverCategory(List<CarTotal> list) throws DBException {
        for (CarTotal carTotal : list) {
            int id = carTotal.getDriverCat().getId();
            carTotal.setDriverCat(driverCatService.findDriverCatById(id));
        }
        return list;
    }
}
