package com.rental.dao;

import com.rental.bean.Car;
import com.rental.bean.DriverCategory;

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

    public boolean insertDriverCategory(DriverCategory cat) {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertDriverCategory(con, cat);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public boolean deleteDriverCategory(int id) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_DELETE_DRIVER_CATEGORY_BY_ID);
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

    public DriverCategory findDriverCategoryById(int id) {
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
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return category;
    }

    public DriverCategory findDriverCategoryByName(String name) {
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
            e.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return category;
    }

    public boolean updateDriverCategoryPrice(DriverCategory cat) {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_UPDATE_DRIVER_CATEGORY_PRICE_BY_ID);
            int z = 1;
            prepSt.setInt(z++, cat.getPrice());
            prepSt.setInt(z, cat.getId());
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

    public List<DriverCategory> findDriverCategories() {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return names;
    }

    public List<DriverCategory> findAllDriverCats() {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
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
