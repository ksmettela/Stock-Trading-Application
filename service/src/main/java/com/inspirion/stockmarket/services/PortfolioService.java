package com.inspirion.stockmarket.services;

import com.inspirion.stockmarket.dao.OrderDAO;
import com.inspirion.stockmarket.entity.OrderStatus;
import com.inspirion.stockmarket.entity.OrderType;
import com.inspirion.stockmarket.entity.Transaction;
import com.inspirion.stockmarket.entity.UserOrder;
import com.inspirion.stockmarket.model.Order;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioService {
    OrderDAO orderDAO;

    public PortfolioService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public int placeOrder(com.inspirion.stockmarket.entity.Order order) {
        order.setStatus(OrderStatus.PENDING.toString());
        return orderDAO.placeOrder(order);
    }

    public void executeOrder(long id) {
        com.inspirion.stockmarket.entity.Order order = orderDAO.getById(id);
        double amountRequired = order.getQuantity() * order.getUnitPrice();
        double amount = orderDAO.getFundsForUser(order.getUserId());
        if (amount >= amountRequired && order.getStatus().equals(OrderStatus.PENDING.toString())) {
            /*
            1. withdraw funds for user
            2. execute the order successfully with status
            3. subract stocks from lot
             */
            orderDAO.updateFunds(order.getUserId(), 0 - amountRequired, order.getOrderType(), order.getId());
            order.setStatus(OrderStatus.EXECUTED.toString());
        } else {
            order.setStatus(OrderStatus.CANCELLED.toString());
        }
        orderDAO.updateOrderStatus(order);
    }

    public com.inspirion.stockmarket.entity.Order cancelOrder(long id) {
        com.inspirion.stockmarket.entity.Order o = orderDAO.getById(id);
        if (null != o && o.getStatus() == OrderStatus.PENDING.toString()) {
            o.setStatus(OrderStatus.CANCELLED.toString());
            orderDAO.updateOrderStatus(o);
        }
        return o;
    }

    public double getUserFunds(int userId) {
        return orderDAO.getFundsForUser(userId);
    }

    public List<Transaction> getTransactionsOfUser(int userId) {
        List<Transaction> transactions = orderDAO.getUserTransactions(userId);
        return transactions;
    }

    public List<UserOrder> getOrders(int userId, OrderStatus executed) {
        List<UserOrder> orders =
                orderDAO.getOrdersForUser(userId, executed);
        return orders;
    }

    public List<Order> getPendingOrdersForToday(double price) {
        com.inspirion.stockmarket.entity.Order order =
                new com.inspirion.stockmarket.entity.Order(0, 0, 0, 0,
                        OrderType.BUY_ON_LIMIT.toString(), OrderStatus.PENDING.toString(), new java.util.Date(), price);
        List<com.inspirion.stockmarket.entity.Order> orders = orderDAO.getPendingOrders(order);
        List<Order> orderList = new ArrayList<>();
        orders.forEach(o -> orderList.add(new Order(o)));
        return orderList;
    }

    public void updateFunds(long userId, double amount, OrderType orderType) throws InvalidObjectException {
        double availableAmount = orderDAO.getFundsForUser(userId);
        if (orderType == OrderType.CASH_WITHDRAW && availableAmount < amount)
            throw new InvalidObjectException("Insufficient Cash");
        amount = orderType == OrderType.CASH_WITHDRAW ? 0 - amount : amount;
        orderDAO.updateFunds(userId, amount, orderType.toString(), 0);
    }
}
