package com.rental;

public class Path {
    private Path(){}
    public static final String ERROR_PAGE = "error_page.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String HOME_PAGE = "index.jsp";
    public static final String CARS_LIST_PAGE = "car_list.jsp";
    public static final String REGISTRATION_PAGE = "registration.jsp";
    public static final String USER_ORDERS_PAGE = "user_orders.jsp";
    public static final String USER_ORDERS_PAGE_REDIRECT = "controller?command=userOrders";
    public static final String THANKS_PAGE = "thanks.jsp";
    public static final String MANAGEMENT_PAGE = "management.jsp";
    public static final String MANAGE_ORDERS_PAGE = "manage_orders.jsp";
    public static final String MANAGE_ORDERS_PAGE_REDIRECT = "controller?manage=Orders&command=management";
    public static final String MANAGE_DRIVERS_CARS_PAGE = "manage_drivers_cars.jsp";
    public static final String MANAGE_DRIVERS_CARS_PAGE_REDIRECT = "controller?manage=Drivers%26Cars&command=management";
    public static final String MANAGE_USERS_PAGE = "manage_users.jsp";
    public static final String MANAGE_USERS_PAGE_REDIRECT = "controller?manage=Users&command=management";
    public static final String USER_ORDER_CONFIRM = "user_order_confirm.jsp";
    public static final String ADMIN_MAIN_PAGE = "administration.jsp";
    public static final String ADMIN_USERS_PAGE = "admin_users.jsp";
    public static final String ADMIN_USERS_PAGE_REDIRECT = "controller?admin=Users&command=administration";
    public static final String ADMIN_CARS_PAGE = "admin_cars.jsp";
    public static final String ADMIN_CARS_PAGE_REDIRECT = "controller?admin=Cars&command=administration";
    public static final String ADMIN_DRIVERS_PAGE = "admin_drivers.jsp";
    public static final String ADMIN_DRIVERS_PAGE_REDIRECT = "controller?admin=Drivers&command=administration";
}