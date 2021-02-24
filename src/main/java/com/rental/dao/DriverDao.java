package com.rental.dao;

import com.rental.bean.Car;
import com.rental.bean.Driver;
import com.rental.bean.DriverCategory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

public class DriverDao {

    private static final String SQL_INSERT_DRIVER =
            "insert into driver(name,driver_license,driver_category_id) values(?,?,?);";
    private static final String SQL_FIND_ALL_DRIVERS = "select * from driver;";
    private static final String SQL_DELETE_DRIVER = "delete from driver where id=?;";
    private static final String SQL_UPDATE_DRIVER_OK = "update driver set is_ok=? where id=?;";
    private final DBManager dbManager;

    public DriverDao() {
        dbManager = DBManager.getInstance();
    }

    public void updateDriverOk(Driver driver) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_DRIVER_OK);
            int z = 1;
            prepSt.setBoolean(z++, driver.isOk());
            prepSt.setInt(z, driver.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public boolean insertDriver(Driver driver) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertDriver(con, driver);
            return true;
        } catch (SQLException e) {
            dbManager.close(con);
            e.printStackTrace();
            return false;
        } finally {
            dbManager.close(con);
        }
    }

    public void insertDriver(Connection con, Driver driver) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_DRIVER, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setString(z++, driver.getName());
        prepSt.setString(z++, driver.getDriverLicense());
        prepSt.setInt(z, driver.getDriverCat().getId());
        if (prepSt.executeUpdate() > 1) {
            getGeneratedId(prepSt, driver);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, Driver driver) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            driver.setId(rs.getInt(1));
        }
        rs.close();
    }

    public List<Driver> findAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_DRIVERS);
            while (rs.next()) {
                drivers.add(mapDriver(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return drivers;
    }

    public Driver mapDriver(ResultSet rs) throws SQLException {
        Driver driver = new Driver();
        driver.setId(rs.getInt(ENTITY_ID));
        driver.setName(rs.getString(DRIVER_NAME));
        driver.setDriverLicense(rs.getString(DRIVER_LICENSE));
        driver.setOk(rs.getBoolean(DRIVER_OK));
        driver.setDriverCat(new DriverCategory());
        driver.getDriverCat().setId(rs.getInt(DRIVER_CATEGORY_ID));
        return driver;
    }


    public boolean deleteDriver(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_DELETE_DRIVER);
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

}
