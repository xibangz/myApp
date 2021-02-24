package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Bean {

    private static final long serialVersionUID = -1997180406957400557L;
    private String login;
    private String password;
    private String passport;
    private int roleId;
    private int amount;
    private boolean blocked;
}
