package com.inspirion.stockmarket.web.controllers;

import com.inspirion.stockmarket.dao.AppSettingsDAO;
import com.inspirion.stockmarket.dao.StocksDAO;
import com.inspirion.stockmarket.entity.AppSettings;
import com.inspirion.stockmarket.entity.Stock;
import com.inspirion.stockmarket.model.Setting;
import com.inspirion.stockmarket.services.SettingService;
import com.inspirion.stockmarket.services.StockService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/stocks")
public class StocksController {
    StockService stockService = new StockService(new StocksDAO());
    SettingService settingService = new SettingService(new AppSettingsDAO());
//    public StocksController(StockService service) {
//        stockService = service;
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stock> get() {
        List<Stock> stocks = stockService.get();
        return stocks;
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Stock getById(@PathParam("id") int id) {
        Stock stock = stockService.get(id);
        return stock;
    }

    @POST
    @Path("/add")
    public int add(Stock stock) {
        int id = stockService.create(stock);
        return id;
    }

    @GET
    @Path("/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public Setting getSettings() {
        Setting settings = settingService.get();
        return settings;
    }

    @POST
    @Path("/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSettings(AppSettings settings) {
        settingService.update(settings);
        return Response.ok().build();
    }


}
