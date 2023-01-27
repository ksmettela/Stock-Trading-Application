package com.inspirion.stockmarket.model;

import java.util.Date;

public class Order {
    public long id, userId, stockId, quantity;
    public double unitPrice;
    public String orderType;
    public String status;
    public Date orderDate;

    public Order() {
    }
    public Order(com.inspirion.stockmarket.entity.Order o) {
        this.id = o.getId();
        this.orderType = o.getOrderType();
        this.stockId = o.getStockId();
        this.quantity = o.getQuantity();
        this.userId = o.getUserId();
        this.unitPrice = o.getUnitPrice();
        this.status = o.getStatus();
        this.orderDate = o.getOrderDate();
    }
    public com.inspirion.stockmarket.entity.Order convert(){
        return new com.inspirion.stockmarket.entity.Order(
                id,userId,stockId,quantity,orderType.toString(),status.toString(),orderDate,unitPrice);
    }
}
