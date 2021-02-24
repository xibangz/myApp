package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car extends Bean {
    private static final long serialVersionUID = 6651151867587981119L;

    private String model;
    private String brand;
    private String numbers;
    private boolean ok;
    private int carTotal;
}
