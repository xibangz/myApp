package com.rental.service;

import com.rental.bean.CarTotal;
import com.rental.bean.ProductPageContent;
import com.rental.dao.CarTotalDao;

import java.io.Serializable;
import java.util.*;

public class CarTotalService implements Serializable {
    private static final long serialVersionUID = 7254865682017987266L;
    private final CarTotalDao carTotalDao = new CarTotalDao();
    private final DriverCategoryService driverCatService = new DriverCategoryService();

    public boolean insertCarTotal(CarTotal total) {
        return carTotalDao.insertCarTotal(total);
    }

    public boolean deleteCarTotal(int id) {
        return carTotalDao.deleteCarTotal(id);
    }

    public boolean updateCarTotal(CarTotal total) {
        return carTotalDao.updateCarTotal(total);
    }

    public void updateQuantity(int id, boolean positiveNumb) {
        carTotalDao.updateQuantity(id, positiveNumb);
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

    public Map<Integer, CarTotal> getCarTotalMap(List<CarTotal> list) {
        Map<Integer, CarTotal> map = new TreeMap<>();
        for (CarTotal total : list) {
            map.put(total.getId(), total);
        }
        return map;
    }
}
