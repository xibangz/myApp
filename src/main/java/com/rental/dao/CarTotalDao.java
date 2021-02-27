package com.rental.dao;

import com.rental.WebPageConstants;
import com.rental.bean.CarTotal;
import com.rental.bean.DriverCategory;
import com.rental.bean.ProductPageContent;
import com.rental.exception.DBException;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rental.dao.Fields.*;

public class CarTotalDao {
    private static final String SQL_FIND_ALL_CARS_TOTAL = "select * from car_total;";
    private static final String SQL_UPDATE_CAR_TOTAL =
            "update car_total set price=?,brand=?,model=?,driver_category_id=?,photo=? where id=?;";
    private static final String SQL_INSERT_CAR_TOTAL =
            "insert into car_total(price,quantity,brand,model,driver_category_id)" +
                    "values (?,?,?,?,?);";
    private static final String SQL_FIND_CAR_TOTAL_BY_ID = "select * from car_total where id=?;";
    private static final String SQL_FIND_ALL_CAR_TOTAL_BRANDS = "select brand from car_total;";
    private static final String SQL_FIND_MAX_PRICE = "select max(price) from car_total;";
    private static final String SQL_COUNT_OF_ALL_CARS = "select count(id) from car_total;";
    private static final String SQL_DELETE_CAR_TOTAL = "delete from car_total where id=?;";
    private static final String SQL_UPDATE_QUANTITY_ADD = "update car_total set quantity=quantity+1 where id=?;";
    private static final String SQL_UPDATE_QUANTITY_SUBTRACT = "update car_total set quantity=quantity-1 where id=?;";

    private final DBManager dbManager;

    public CarTotalDao() {
        dbManager = DBManager.getInstance();
    }


    public int findMaxPrice() throws DBException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        int max = 0;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_MAX_PRICE);
            if (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find maxPrice!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return max;
    }

    public int countAllCarTotals() throws DBException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_COUNT_OF_ALL_CARS);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBException("Can't count all CarTotals!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return count;
    }

    public int countAllCarTotals(String query) throws DBException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBException("Can't count all CarTotals!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return count;
    }


    public void insertCarTotal(CarTotal car) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            insertCarTotal(con, car);
        } catch (SQLException e) {
            throw new DBException("Can't insert CarTotal!",e);
        } finally {
            dbManager.close(con);
        }
    }

    public void insertCarTotal(Connection con, CarTotal car) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_INSERT_CAR_TOTAL, Statement.RETURN_GENERATED_KEYS);
        int z = 1;
        prepSt.setInt(z++, car.getPrice());
        prepSt.setInt(z++, car.getQuantity());
        prepSt.setString(z++, car.getBrand());
        prepSt.setString(z++, car.getModel());
        prepSt.setInt(z, car.getDriverCat().getId());
        if (prepSt.executeUpdate() > 0) {
            getGeneratedId(prepSt, car);
        }
        prepSt.close();
    }

    private void getGeneratedId(Statement st, CarTotal car) throws SQLException {
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            car.setId(rs.getInt(1));
        }
        rs.close();
    }

    public void updateQuantity(int id, boolean positiveNumb,Connection con) throws DBException {
        PreparedStatement prepSt = null;
        try {
            prepSt = positiveNumb
                    ? con.prepareStatement(SQL_UPDATE_QUANTITY_ADD)
                    : con.prepareStatement(SQL_UPDATE_QUANTITY_SUBTRACT);
            prepSt.setInt(1, id);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't update quantity!",e);
        } finally {
            dbManager.close(prepSt);
        }
    }

    public List<CarTotal> findAllCars() throws DBException {
        List<CarTotal> cars = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_CARS_TOTAL);
            while (rs.next()) {
                cars.add(mapCarTotal(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Can't find all CarTotals!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return cars;
    }

    public Set<String> findAllBrands() throws DBException {
        Set<String> brands = new HashSet<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_CAR_TOTAL_BRANDS);
            while (rs.next()) {
                brands.add(rs.getString(CAR_TOTAL_BRAND));
            }
        } catch (SQLException e) {
            throw new DBException("Can't find all brands!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        return brands;
    }

    private CarTotal mapCarTotal(ResultSet rs) throws SQLException {
        CarTotal car = new CarTotal();
        car.setId(rs.getInt(ENTITY_ID));
        car.setModel(rs.getString(CAR_MODEL));
        car.setBrand(rs.getString(CAR_BRAND));
        car.setQuantity(rs.getInt(CAR_TOTAL_QUANTITY));
        car.setPrice(rs.getInt(CAR_TOTAL_PRICE));
        car.setDriverCat(new DriverCategory());
        car.getDriverCat().setId(rs.getInt(DRIVER_CATEGORY_ID));
        car.setPhoto(rs.getString(CAR_TOTAL_PHOTO));
        return car;
    }

    public void updateCarTotal(CarTotal car) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            updateCarTotal(con, car);
        } catch (SQLException e) {
            throw new DBException("Can't update CarTotal",e);
        } finally {
            dbManager.close(con);
        }
    }

    public void updateCarTotal(Connection con, CarTotal car) throws SQLException {
        PreparedStatement prepSt = con.prepareStatement(SQL_UPDATE_CAR_TOTAL);
        int z = 1;
        prepSt.setInt(z++, car.getPrice());
        prepSt.setString(z++, car.getBrand());
        prepSt.setString(z++, car.getModel());
        prepSt.setInt(z++, car.getDriverCat().getId());
        prepSt.setString(z++, car.getPhoto());
        prepSt.setInt(z, car.getId());
        prepSt.executeUpdate();
        prepSt.close();
    }

    public void deleteCarTotal(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_DELETE_CAR_TOTAL);
            prepSt.setInt(1, id);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't delete CarTotal",e);
        } finally {
            dbManager.close(prepSt);
            dbManager.close(con);
        }
    }

    public CarTotal findCarTotalById(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        CarTotal total = null;
        try {
            con = dbManager.getConnection();
            prepSt = con.prepareStatement(SQL_FIND_CAR_TOTAL_BY_ID);
            prepSt.setInt(1, id);
            rs = prepSt.executeQuery();
            if (rs.next()) {
                total = mapCarTotal(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Can't find CarTotal by ID!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(prepSt);
            dbManager.close(con);
        }
        return total;
    }

    public List<CarTotal> findSelectedCars(ProductPageContent content) throws DBException {
        List<CarTotal> cars = new ArrayList<>();
        String query = buildQuery(content);
        String secondQuery;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                cars.add(mapCarTotal(rs));
            }
        } catch (SQLException e) {
            throw new DBException("Can't find selected CarTotals!",e);
        } finally {
            dbManager.close(rs);
            dbManager.close(stmt);
            dbManager.close(con);
        }
        secondQuery = query.replaceAll(" limit(.+)", ";")
                .replace("*", "count(id)");
        content.setCountOfProducts(countAllCarTotals(secondQuery));
        return cars;
    }

    private String buildQuery(ProductPageContent content) {
        StringBuilder query = new StringBuilder();
        int count = 0;
        boolean priceFromSet = false;
        boolean priceToSet = false;
        List<String> brands = content.getBrandFilter();
        List<String> driverCats = content.getCategoryFilter();
        query.append("select * from car_total");
        if (brands != null && !brands.isEmpty()) {
            query.append(" where(");
            for (int i = 0; i < brands.size(); i++) {
                query.append("brand='").
                        append(i + 1 == brands.size() ? brands.get(i) + "')" : brands.get(i) + "' or ");
            }
            count++;
        }
        if (driverCats != null && !driverCats.isEmpty()) {
            query.append(count > 0 ? " and (" : " where(");
            for (int i = 0; i < driverCats.size(); i++) {
                query.append("driver_category_id=").
                        append(i + 1 == driverCats.size() ?
                                driverCats.get(i) + ")" : driverCats.get(i) + " or ");
            }
            count++;
        }
        if (content.getPriceFromFilter() != null && content.getPriceFromFilter() != 0) {
            query.append(count > 0 ? " and (price>=" + content.getPriceFromFilter() : " where(price>=" + content.getPriceFromFilter());
            priceFromSet = true;
        }
        if (content.getPriceToFilter() != null && content.getPriceToFilter() != 0) {
            if (priceFromSet) {
                query.append(" and price<=").append(content.getPriceToFilter()).
                        append(")");
            } else {
                query.append(count > 0 ? " and price<=" + content.getPriceToFilter() : "where price<=" + content.getPriceToFilter());
            }
            priceToSet = true;
        }
        if (priceFromSet && !priceToSet) {
            query.append(")");
        }
        if (content.getSort() != null) {
            query.append(" order by ").append(WebPageConstants.getSort(content.getSort()));
            if (content.getSort().equals("priceHighest")) {
                query.append(" desc");
            }
        }
        if (content.getPageId() == 1) {
            query.append(" limit ").append(content.getProductsPerPage()).append(";");
            return query.toString();
        }
        int from = (content.getPageId() - 1) * content.getProductsPerPage();
        int to = content.getPageId() * content.getProductsPerPage();
        query.append(" limit ").append(from).append(",").append(to).append(";");
        return query.toString();
    }
}
