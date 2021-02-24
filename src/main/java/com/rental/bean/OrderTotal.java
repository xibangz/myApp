package com.rental.bean;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
public class OrderTotal extends Bean {
    private static final long serialVersionUID = 2770497327634357276L;

    private int sum;
    private boolean penalty;
    private Order order;
    private int orderStatusId;
    private String description;
    private User manager;
    private Timestamp orderDate;
}
