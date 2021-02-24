package com.rental.bean;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Driver extends Bean{
    private static final long serialVersionUID = 3257463483542986049L;

    private String name;
    private String driverLicense;
    private DriverCategory driverCat;
    private boolean ok;
}
