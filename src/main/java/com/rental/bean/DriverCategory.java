package com.rental.bean;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverCategory extends Bean{

    private static final long serialVersionUID = -6359967211707461821L;

    private int quantity;
    private int price;
    private String name;

}
