package com.rental.service;

import com.rental.bean.Car;
import com.rental.dao.CarDao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class CarService implements Serializable {
    private static final long serialVersionUID = -2027902856419273873L;
    private final CarDao carDao = new CarDao();

    public List<Car> findAllCars() {
        return carDao.findAllCars();
    }

    public void insertCar(Car car,Connection con) {
        carDao.insertCar(car);
    }

    public void deleteCar(int id,Connection con) {
        carDao.deleteCar(id,con);
    }

    public int findCarTotalId(int id) {
        return carDao.findCarTotalId(id);
    }

    public void updateCarOk(Car car, Connection con) {
        carDao.updateCarOk(car,con);
    }
}
