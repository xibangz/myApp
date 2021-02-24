package com.rental.dao;

public class Fields {
    private Fields() {
    }
    public static final int USER_CLIENT_ROLE = 1;
    public static final int USER_MANAGER_ROLE = 2;
    public static final int ORDER_TOTAL_STATUS_DEFAULT_ID = 1;
    public static final int ORDER_TOTAL_STATUS_CONFIRMED_ID = 2;
    public static final int ORDER_TOTAL_STATUS_REJECTED_ID = 3;
    public static final int ORDER_TOTAL_STATUS_COMPLETED_ID = 4;
    public static final String ENTITY_ID = "id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PASSPORT = "passport";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_AMOUNT = "amount";
    public static final String USER_IS_BLOCKED = "is_blocked";
    public static final String CAR_BRAND = "brand";
    public static final String CAR_MODEL = "model";
    public static final String CAR_OK = "is_ok";
    public static final String CAR_TOTAL_ID = "car_total_id";
    public static final String CAR_NUMBERS = "numbers";
    public static final String ORDER_RENT_FROM = "rent_from";
    public static final String ORDER_RENT_TO = "rent_to";
    public static final String ORDER_CAR_TOTAL_ID = "car_total_id";
    public static final String ORDER_ACCOUNT_ID = "account_id";
    public static final String ORDER_APPROVED_BY_ID = "approved_by_id";
    public static final String ORDER_NUMB_OF_DRIVERS = "numb_of_drivers";
    public static final String ORDER_NUMB_OF_CARS = "numb_of_cars";
    public static final String ORDER_TOTAL_DESCRIPTION ="description";
    public static final String ORDER_TOTAL_SUM ="sum";
    public static final String ORDER_TOTAL_IS_PENALTY="is_penalty";
    public static final String ORDER_TOTAL_STATUS_ID="order_status_id";
    public static final String ORDER_TOTAL_ORDER_ID="orders_id";
    public static final String ORDER_TOTAL_ORDER_DATE="order_date";
    public static final String DRIVER_NAME="name";
    public static final String DRIVER_LICENSE="driver_license";
    public static final String DRIVER_OK="is_ok";
    public static final String DRIVER_CATEGORY_ID="driver_category_id";
    public static final String CAR_TOTAL_QUANTITY="quantity";
    public static final String CAR_TOTAL_PRICE="price";
    public static final String CAR_TOTAL_BRAND="brand";
    public static final String CAR_TOTAL_PHOTO="photo";
    public static final String DRIVER_CATEGORY_NAME="name";
    public static final String DRIVER_CATEGORY_PRICE="price";
    public static final String DRIVER_CATEGORY_QUANTITY="quantity";
}
