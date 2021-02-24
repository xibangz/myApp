package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderPageInfoContent implements Serializable {
    private static final long serialVersionUID = -971008251145340852L;

    private String numbOfDriversInfo;
    private String numbOfCarsInfo;
    private String rentFromInfo;
    private String rentToInfo;
    private String userPassportInfo;
}
