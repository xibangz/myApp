package com.rental.dao;

import com.rental.bean.Car;
import com.rental.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

public class CarDao {
    private static final String SQL_FIND_ALL_CARS = "select * from car;";
    private static final String SQL_INSERT_CAR = "insert into car(brand,model,numbers,car_total_id) values(?,?,?,?)";
    private static final String SQL_DELETE_CAR = "delete from car where id=?;";
    private static final String SQL_FIND_CAR_TOTAL_ID = "select car_total_id from car where id=?;";
    private static final String SQL_UPDATE_CAR_OK = "update car set is_ok=? where id=?;";
    private final DBManager dbManager;

    public CarDao() {
        dbManager = DBManager.getInstance();
    }

    public void updateCarOk(Car car, Connection con) throws DBException {
        PreparedStatement prepSt = null;
        try {
            prepSt = con.prepareStatement(SQL_UPDATE_CAR_OK);
            int z = 1;
            prepSt.setBoolean(z++, car.isOk());
            prepSt.setInt(z, car.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't update Car.Ok!",e);
        } finally {
            dbManager.close(prepSt);
        }
    }

    public List<Car> findAllCars() throws DBException {
        List<Car> cars = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_ALL_CARS);
            while (rs.next()) {
                cars.add(mapCar(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Can't find Cars!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return cars;
    }

    public void insertCar(Car car) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertCar(con, car);
        } catch (SQLException e) {
            throw new DBException("Can't insert Car!",e);
        } finally {
            dbManager.close(con);
        }
    }

    public void insertCar(Connection con, Car car) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_CAR, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setString(z++, car.getBrand());
        prepSt.setString(z++, car.getModel());
        prepSt.setString(z++, car.getNumbers());
        prepSt.setInt(z, car.getCarTotal());
        if (prepSt.executeUpdate() > 1) {
            getGeneratedId(prepSt, car);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, Car car) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            car.setId(rs.getInt(1));
        }
        rs.close();
    }

    public void deleteCar(int id,Connection con) throws DBException {
        PreparedStatement prepSt = null;
        try {
            prepSt = con.prepareStatement(SQL_DELETE_CAR);
            prepSt.setInt(1, id);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't delete Car!",e);
        } finally {
            dbManager.close(prepSt);
        }
    }

    public int findCarTotalId(int id) throws DBException {
        int carTotalId = -1;
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_CAR_TOTAL_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                carTotalId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find CarTotalId!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return carTotalId;
    }

    public Car mapCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt(ENTITY_ID));
        car.setModel(rs.getString(CAR_MODEL));
        car.setBrand(rs.getString(CAR_BRAND));
        car.setNumbers(rs.getString(CAR_NUMBERS));
        car.setOk(rs.getBoolean(CAR_OK));
        car.setCarTotal(rs.getInt(CAR_TOTAL_ID));
        return car;
    }
}
