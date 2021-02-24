package com.rental.listener;

import com.rental.bean.OrderStatus;
import com.rental.bean.Role;
import com.rental.service.*;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        initServices(context);
        initCommandContainer();
        initLog4J(context);
        context.setAttribute("orderStatus", OrderStatus.values());
        context.setAttribute("role", Role.values());
    }

    private void initServices(ServletContext context) {
        final CarService carServ = new CarService();
        final CarTotalService carTotalServ = new CarTotalService();
        final DriverCategoryService driverCatServ = new DriverCategoryService();
        final DriverService driverServ = new DriverService();
        final OrderService orderServ = new OrderService();
        final OrderTotalService orderTotalServ = new OrderTotalService();
        final UserService userServ = new UserService();
        context.setAttribute("carServ", carServ);
        context.setAttribute("carTotalServ", carTotalServ);
        context.setAttribute("driverCatServ", driverCatServ);
        context.setAttribute("driverServ", driverServ);
        context.setAttribute("orderServ", orderServ);
        context.setAttribute("orderTotalServ", orderTotalServ);
        context.setAttribute("userServ", userServ);
    }

    private void initCommandContainer() {
        try {
            Class.forName("com.rental.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    private void initLog4J(ServletContext servletContext) {
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
