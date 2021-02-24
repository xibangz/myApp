package com.rental.bean;

public enum Role {
    CLIENT, MANAGER, ADMIN;

    public static Role getRole(User user) {
        return Role.values()[user.getRoleId()-1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
