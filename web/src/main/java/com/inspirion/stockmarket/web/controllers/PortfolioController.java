package com.inspirion.stockmarket.web.controllers;

import com.inspirion.stockmarket.dao.OrderDAO;
import com.inspirion.stockmarket.entity.OrderStatus;
import com.inspirion.stockmarket.entity.OrderType;
import com.inspirion.stockmarket.entity.UserOrder;
import com.inspirion.stockmarket.services.PortfolioService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/portfolio")
public class PortfolioController {
    PortfolioService orderService = new PortfolioService(new OrderDAO());

//    public PortfolioController(PortfolioService orderDAO) {
//        this.orderService = orderDAO;
//    }

    @POST
    @Path("/place-order")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buy(com.inspirion.stockmarket.entity.Order order) {
        int id = orderService.placeOrder(order);
        return Response.ok(id).build();
    }

    @GET
    @Path("/funds/{userId}")
    public double getFunds(@PathParam("userId") int userId){
        return orderService.getUserFunds(userId);
    }

    @GET
    @Path("/orders/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserOrder> getOrders(@PathParam("userId") int userId) {
        List<UserOrder> orders = orderService.getOrders(userId, OrderStatus.EXECUTED);
        return orders;
    }

    @GET
    @Path("/transactions/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserOrder> getTransactionDetails(@PathParam("userId") int id) {
        List<UserOrder> orders = orderService.getOrders(id, null);
        return orders;
    }

    @POST
    @Path("deposit/{userId}/amount/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response depositCash(@PathParam("userId") int userId,
                                @PathParam("amount") double amount) {
        try {
            orderService.updateFunds(userId, amount, OrderType.CASH_DEPOSIT);
        } catch (Exception e) {
            System.out.println(e);
        }
        return Response.ok().build();
    }

    @POST
    @Path("withdraw/{userId}/amount/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response withdrawCash(@PathParam("userId") int userId,
                                 @PathParam("amount") double amount) {
        try {
            orderService.updateFunds(userId, amount, OrderType.CASH_DEPOSIT);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }
}
