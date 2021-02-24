package com.rental.service;

import com.rental.bean.Car;
import com.rental.dao.CarDao;

import java.io.Serializable;
import java.util.List;

public class CarService implements Serializable {
    private static final long serialVersionUID = -2027902856419273873L;
    private final CarDao carDao = new CarDao();

    public List<Car> findAllCars() {
        return carDao.findAllCars();
    }

    public boolean insertCar(Car car) {
        return carDao.insertCar(car);
    }

    public boolean deleteCar(int id) {
        return carDao.deleteCar(id);
    }

    public int findCarTotalId(int id) {
        return carDao.findCarTotalId(id);
    }

    public void updateCarOk(Car car) {
        carDao.updateCarOk(car);
    }
}
