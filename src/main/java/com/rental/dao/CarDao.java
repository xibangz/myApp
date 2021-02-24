package com.rental.dao;

import com.rental.bean.Car;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

@Log4j
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

    public void updateCarOk(Car car) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_CAR_OK);
            int z = 1;
            prepSt.setBoolean(z++, car.isOk());
            prepSt.setInt(z, car.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);

        }
    }

    public List<Car> findAllCars() {
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
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return cars;
    }

    public boolean insertCar(Car car) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertCar(con, car);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public boolean deleteCar(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_DELETE_CAR);
            prepSt.setInt(1, id);
            prepSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public int findCarTotalId(int id) {
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
            e.printStackTrace();
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