package com.rental.command;

import com.rental.Path;
import com.rental.bean.DriverCategory;
import com.rental.bean.ProductPageContent;
import com.rental.exception.DBException;
import com.rental.service.*;

import static com.rental.WebPageConstants.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class CarsListCommand extends Command {
    private static final long serialVersionUID = -4805360108029962462L;

    private DriverCategoryService driverCatServ;
    private CarTotalService carTotalServ;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        initServices(req);
        ProductPageContent content = (ProductPageContent) session.getAttribute("pageContent");
        try {
            if (content == null || req.getParameter("refresh") != null) {
                content = createDefaultWebPageContent();
            } else {
                updateWebPageContent(req, content);
            }
        }catch (DBException e){
            session.setAttribute("errorMessage",e.getMessage());
            return Path.ERROR_PAGE;
        }
        session.setAttribute("pageContent", content);
        return Path.CARS_LIST_PAGE;
    }

    private void initServices(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        driverCatServ = (DriverCategoryService) context.getAttribute("driverCatServ");
        carTotalServ = (CarTotalService) context.getAttribute("carTotalServ");
    }

    private ProductPageContent createDefaultWebPageContent() throws DBException {
        ProductPageContent content = new ProductPageContent();
        content.setPageId(DEFAULT_PAGE_ID);
        content.setProductsPerPage(DEFAULT_PROD_PER_PAGE);
        content.setSort(DEFAULT_SORT);
        content.setCategoryFilter(Collections.emptyList());
        content.setBrandFilter(Collections.emptyList());
        content.setPriceToFilter(0);
        content.setPriceFromFilter(0);
        content.setCountOfProducts(carTotalServ.countAllCarsTotals());
        content.setCarsList(carTotalServ.findSelectedCars(content));
        content.setBrands(carTotalServ.findAllBrands());
        content.setCategories(driverCatServ.findDriverCats());
        return content;
    }

    private void updateWebPageContent(HttpServletRequest req, ProductPageContent content) throws DBException {
        String pageIdValue = req.getParameter("pageId");
        String prodPerPageValue = req.getParameter("productsPerPage");
        String sortValue = req.getParameter("sort");
        String[] catFilterValues = req.getParameterValues("categoryFilter");
        String[] brandFilterValues = req.getParameterValues("brandFilter");
        String priceFromFilterValue = req.getParameter("priceFromFilter");
        String priceToFilterValue = req.getParameter("priceToFilter");

        pageIdCheck(pageIdValue, content);
        productsPerPageCheck(prodPerPageValue, content);
        sortCheck(sortValue, content);
        categoryFilterCheck(catFilterValues, content);
        brandFilterCheck(brandFilterValues, content);
        priceToCheck(priceToFilterValue, content);
        priceFromCheck(priceFromFilterValue, content);
        content.setCarsList(carTotalServ.findSelectedCars(content));
    }


    private void priceFromCheck(String value, ProductPageContent content) throws DBException {
        if (value != null && !value.isEmpty() && !content.getPriceFromFilter().toString().equals(value)) {
            content.setPriceFromFilter(priceCheck(value));
        }
    }

    private void priceToCheck(String value, ProductPageContent content) throws DBException {
        if (value != null && !value.isEmpty() && !content.getPriceToFilter().toString().equals(value)) {
            content.setPriceToFilter(priceCheck(value));
        }
    }

    private Integer priceCheck(String value) throws DBException {
        int price = Integer.parseInt(value);
        int maxPrice = new CarTotalService().findMaxPrice();
        if (price > maxPrice || price < 0) {
            return null;
        }
        return price;
    }

    private void sortCheck(String value, ProductPageContent content) {
        if (value != null && !value.isEmpty()
                && !content.getSort().equals(value)
                && getSortMap().containsKey(value)) {
            content.setSort(value);
        }
    }

    private void productsPerPageCheck(String value, ProductPageContent content) {
        if (value != null && !value.isEmpty()
                && !content.getProductsPerPage().toString().equals(value)
                && getProductsPerPageMap().containsKey(value)) {
            if (content.getPageId() != DEFAULT_PAGE_ID) {
                content.setPageId(DEFAULT_PAGE_ID);
            }
            content.setProductsPerPage(Integer.parseInt(value));
        }
    }

    private void pageIdCheck(String value, ProductPageContent content) {
        if (value != null && !value.isEmpty() && !content.getPageId().toString().equals(value)) {
            int pageId = Integer.parseInt(value);
            int maxPage = content.getCountOfProducts()
                    % DEFAULT_PROD_PER_PAGE > 0
                    ? (content.getCountOfProducts() / DEFAULT_PROD_PER_PAGE) + 1
                    : (content.getCountOfProducts() / DEFAULT_PROD_PER_PAGE);
            if (pageId > maxPage || pageId <= 0) {
                content.setPageId(DEFAULT_PAGE_ID);
                return;
            }
            content.setPageId(pageId);
        }
    }

    private void brandFilterCheck(String[] values, ProductPageContent content) {
        if (values != null && values.length > 0 && !Arrays.equals(values, content.getBrandFilter().toArray())) {
            List<String> list = new ArrayList<>();
            for (String brand : values) {
                if (brand != null && !brand.isEmpty() && content.getBrands().contains(brand)) {
                    list.add(brand);
                }
            }
            content.setBrandFilter(list);
        }
    }

    private void categoryFilterCheck(String[] values, ProductPageContent content) {
        List<String> catIds = new ArrayList<>();
        for (DriverCategory cat : content.getCategories()) {
            catIds.add(Integer.toString(cat.getId()));
        }
        if (values != null && values.length > 0 && !Arrays.equals(values, content.getCategoryFilter().toArray())) {
            List<String> list = new ArrayList<>();
            for (String category : values) {
                if (category != null && !category.isEmpty() && catIds.contains(category)) {
                    list.add(category);
                }
            }
            content.setCategoryFilter(list);
        }
    }
}
