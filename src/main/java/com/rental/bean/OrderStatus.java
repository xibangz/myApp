package com.rental.bean;

public enum OrderStatus {
    IN_PROGRESS, CONFIRMED, REJECTED, COMPLETED;

    public static Role getStatus(OrderTotal total) {
        return Role.values()[total.getOrderStatusId()-1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
