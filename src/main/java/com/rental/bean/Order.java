package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class Order extends Bean {
    private static final long serialVersionUID = -3384434201062177765L;

    private Date rentFrom;
    private Date rentTo;
    private int numbOfCars;
    private int numbOfDrivers;
    private User client;
    private CarTotal carTotal;

}
