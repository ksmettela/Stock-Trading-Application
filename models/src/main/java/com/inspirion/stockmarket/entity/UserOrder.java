package com.inspirion.stockmarket.entity;

import java.util.Date;

public class UserOrder {
    public long id,quantity,price;
    public String stock,type,status;
    Date orderDate;

    public UserOrder(long id, long quantity, long price, String stock, String type, String status, Date orderDate) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.status = status;
        this.orderDate = orderDate;
    }

    public UserOrder() {
    }
}
