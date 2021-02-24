package com.rental;


import java.util.Map;
import java.util.TreeMap;

public class WebPageConstants {
    private WebPageConstants() {
    }

    private static final Map<String, String> sortConstants = new TreeMap<>();
    private static final Map<String, Integer> prodPerPageConstants = new TreeMap<>();
    public static final int DEFAULT_PAGE_ID = 1;
    public static final int DEFAULT_PROD_PER_PAGE = 4;
    public static final String DEFAULT_SORT = "defaultSort";

    static {
        sortConstants.put("priceLowest", "price");
        sortConstants.put("priceHighest", "price");
        sortConstants.put("brand", "brand");
        sortConstants.put("defaultSort", "id");

        prodPerPageConstants.put("4", 4);
        prodPerPageConstants.put("8", 8);
        prodPerPageConstants.put("16", 16);
        prodPerPageConstants.put("32", 32);
    }

    public static String getSort(String key) {
        return sortConstants.get(key);
    }

    public static Map<String,Integer> getProductsPerPageMap(){
        return prodPerPageConstants;
    }

    public static Map<String,String> getSortMap(){
        return sortConstants;
    }

    public static int getProductsPerPage(String key) {
        return prodPerPageConstants.get(key);
    }
}
