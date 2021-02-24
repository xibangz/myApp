package com.rental.service;

import com.rental.bean.CarTotal;
import com.rental.bean.ProductPageContent;
import com.rental.dao.CarTotalDao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.*;

public class CarTotalService implements Serializable {
    private static final long serialVersionUID = 7254865682017987266L;
    private final CarTotalDao carTotalDao = new CarTotalDao();
    private final DriverCategoryService driverCatService = new DriverCategoryService();

    public void insertCarTotal(CarTotal total) {
         carTotalDao.insertCarTotal(total);
    }

    public void deleteCarTotal(int id) {
         carTotalDao.deleteCarTotal(id);
    }

    public void updateCarTotal(CarTotal total) {
         carTotalDao.updateCarTotal(total);
    }

    public void updateQuantity(int id, boolean positiveNumb, Connection con) {
        carTotalDao.updateQuantity(id, positiveNumb,con);
    }

    public List<CarTotal> findAllCars() {
        return setDriverCategory(carTotalDao.findAllCars());
    }

    public Set<String> findAllBrands() {
        return carTotalDao.findAllBrands();
    }

    public List<CarTotal> findSelectedCars(ProductPageContent content) {
        return carTotalDao.findSelectedCars(content);
    }

    public int findMaxPrice() {
        return carTotalDao.findMaxPrice();
    }

    public int countAllCarsTotals() {
        return carTotalDao.countAllCarTotals();
    }

    public CarTotal findCarTotalById(int id) {
        return carTotalDao.findCarTotalById(id);
    }

    public List<CarTotal> setDriverCategory(List<CarTotal> list) {
        for (CarTotal carTotal : list) {
            int id = carTotal.getDriverCat().getId();
            carTotal.setDriverCat(driverCatService.findDriverCatById(id));
        }
        return list;
    }
}
