package com.inspirion.stockmarket.entity;

import java.util.Date;

public class Transaction {
    long id, userId, transactionId;
    double amount;
    OrderType transactionType;
    Date transactionDate;

    public Transaction(long id, long userId, long transactionId, double amount, OrderType transactionType, Date transactionDate) {
        this.id = id;
        this.userId = userId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public OrderType getTransactionType() {
        return transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
