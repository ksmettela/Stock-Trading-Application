package com.inspirion.stockmarket.engine;

import com.inspirion.stockmarket.dao.AppSettingsDAO;
import com.inspirion.stockmarket.dao.OrderDAO;
import com.inspirion.stockmarket.dao.StocksDAO;
import com.inspirion.stockmarket.engine.util.MarketHandler;
import com.inspirion.stockmarket.services.PortfolioService;
import com.inspirion.stockmarket.services.SettingService;
import com.inspirion.stockmarket.services.StockService;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        startEngine();
    }

    private static void startEngine() {
        SettingService settingService = new SettingService(new AppSettingsDAO());
        StockService stockService = new StockService(new StocksDAO());
        PortfolioService portfolioService = new PortfolioService(new OrderDAO());

        MarketHandler handler = new MarketHandler(settingService, stockService, portfolioService);
        LocalTime date = handler.getSchedule();
        if (date.compareTo(LocalTime.now()) < 0) {
            handler.start();
            System.out.println("Server Started...!!!");
        }
    }
}
