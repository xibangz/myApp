package com.rental.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("userOrders", new UserOrdersCommand());
        commands.put("carsList", new CarsListCommand());
        commands.put("order", new OrderCommand());
        commands.put("management", new ManagementCommand());
        commands.put("administration", new AdministrationCommand());
        commands.put("adminUsers", new AdminUsersCommand());
        commands.put("adminCars", new AdminCarsCommand());
        commands.put("adminDrivers", new AdminDriversCommand());
        commands.put("manageOrders", new ManageOrdersCommand());
        commands.put("manageDriversCars", new ManageDriversCarsCommand());
        commands.put("logout", new LogoutCommand());
    }

    public static Command getCommand(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
