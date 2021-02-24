package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarTotal extends Bean{
    private static final long serialVersionUID = 1611035872086757011L;
    private int price;
    private int quantity;
    private DriverCategory driverCat;
    private String brand;
    private String model;
    private String photo;
}
