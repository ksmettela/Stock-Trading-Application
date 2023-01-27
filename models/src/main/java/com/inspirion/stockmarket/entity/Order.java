package com.inspirion.stockmarket.entity;

import java.util.Date;

public class Order {
    long id, userId, stockId, quantity;
    double unitPrice;
    String orderType;

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(){

    }

    String status;
    Date orderDate;

    public double getUnitPrice() {
        return unitPrice;
    }
    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getStockId() {
        return stockId;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Order(long id, long userId, long stockId, long quantity,
                 String orderType, String status, Date orderDate,double unitPrice) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.orderType = orderType;
        this.status = status;
        this.orderDate = orderDate;
        this.unitPrice = unitPrice;
    }
}
