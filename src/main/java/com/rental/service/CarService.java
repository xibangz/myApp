package com.rental.service;

import com.rental.bean.Car;
import com.rental.dao.CarDao;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class CarService implements Serializable {
    private static final long serialVersionUID = -2027902856419273873L;
    private final CarDao carDao = new CarDao();

    public List<Car> findAllCars() throws DBException {
        return carDao.findAllCars();
    }

    public void insertCar(Car car, Connection con) throws DBException {
        carDao.insertCar(car);
    }

    public void deleteCar(int id, Connection con) throws DBException {
        carDao.deleteCar(id, con);
    }

    public int findCarTotalId(int id) throws DBException {
        return carDao.findCarTotalId(id);
    }

    public void updateCarOk(Car car, Connection con) throws DBException {
        carDao.updateCarOk(car, con);
    }
}
