package com.rental.dao;

import com.rental.bean.Car;
import com.rental.bean.DriverCategory;
import com.rental.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.rental.dao.Fields.*;

public class DriverCategoryDao {

    private static final String SQL_INSERT_DRIVER_CATEGORY =
            "insert into driver_category(name,price)values(?,?);";
    private static final String SQL_FIND_DRIVER_CATEGORY_BY_ID = "select * from driver_category where id=?;";
    private static final String SQL_FIND_ALL_DRIVER_CATEGORY_NAMES = "select name,id from driver_category;";
    private static final String SQL_FIND_ALL_DRIVER_CATEGORIES = "select * from driver_category;";
    private static final String SQL_UPDATE_DRIVER_CATEGORY_PRICE_BY_ID = "update driver_category set price=? where id=?;";
    private static final String SQL_DELETE_DRIVER_CATEGORY_BY_ID = "delete from driver_category where id=?";
    private static final String SQL_FIND_DRIVER_CATEGORY_BY_NAME = "select * from driver_category where name=?;";
    private final DBManager dbManager;

    public DriverCategoryDao() {
        dbManager = DBManager.getInstance();
    }

    public void insertDriverCategory(DriverCategory cat) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertDriverCategory(con, cat);
        } catch (SQLException e) {
            throw new DBException("Can't insert DriverCategory!",e);
        } finally {
            dbManager.close(con);
        }
    }

    public void insertDriverCategory(Connection con, DriverCategory cat) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_DRIVER_CATEGORY);
        int z = 1;
        prepSt.setString(z++, cat.getName());
        prepSt.setInt(z, cat.getPrice());
        prepSt.executeUpdate();
        prepSt.close();
    }

    public void deleteDriverCategory(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_DELETE_DRIVER_CATEGORY_BY_ID);
            prepSt.setInt(1, id);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't delete DriverCategory!",e);
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public DriverCategory findDriverCategoryById(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        DriverCategory category = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_DRIVER_CATEGORY_BY_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                category = mapDriverCat(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find DriverCategory by ID!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return category;
    }

    public DriverCategory findDriverCategoryByName(String name) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        DriverCategory category = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_DRIVER_CATEGORY_BY_NAME);
            prepSt.setString(1, name);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                category = mapDriverCat(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find DriverCategory by name!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return category;
    }

    public void updateDriverCategoryPrice(DriverCategory cat) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_DRIVER_CATEGORY_PRICE_BY_ID);
            int z = 1;
            prepSt.setInt(z++, cat.getPrice());
            prepSt.setInt(z, cat.getId());
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't update DriverCategory price!",e);
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public List<DriverCategory> findDriverCategories() throws DBException {
        List<DriverCategory> names = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_DRIVER_CATEGORY_NAMES);
            while (rs.next()) {
                DriverCategory drCat = new DriverCategory();
                drCat.setName(rs.getString(DRIVER_CATEGORY_NAME));
                drCat.setId(rs.getInt(ENTITY_ID));
                names.add(drCat);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find DriverCategory names!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return names;
    }

    public List<DriverCategory> findAllDriverCats() throws DBException {
        List<DriverCategory> names = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_DRIVER_CATEGORIES);
            while (rs.next()) {
                names.add(mapDriverCat(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Can't find all DriverCategories!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return names;
    }

    private DriverCategory mapDriverCat(ResultSet rs) throws SQLException {
        DriverCategory category = new DriverCategory();
        category.setId(rs.getInt(ENTITY_ID));
        category.setName(rs.getString(DRIVER_CATEGORY_NAME));
        category.setPrice(rs.getInt(DRIVER_CATEGORY_PRICE));
        category.setQuantity(rs.getInt(DRIVER_CATEGORY_QUANTITY));
        return category;
    }
}
