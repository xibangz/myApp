package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductPageContent implements Serializable {
    private static final long serialVersionUID = 1875832531442080069L;


    private Integer countOfProducts;
    private Integer pageId;
    private Integer productsPerPage;
    private String sort;
    private List<String> categoryFilter;
    private List<String> brandFilter;
    private Integer priceFromFilter;
    private Integer priceToFilter;
    private List<CarTotal> carsList;
    private Set<String> brands;
    private List<DriverCategory> categories;
}
