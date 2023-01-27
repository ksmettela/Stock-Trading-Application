package com.inspirion.stockmarket.engine.util;

import com.inspirion.stockmarket.contracts.IHandler;
import com.inspirion.stockmarket.entity.Stock;
import com.inspirion.stockmarket.model.Order;
import com.inspirion.stockmarket.model.Setting;
import com.inspirion.stockmarket.services.PortfolioService;
import com.inspirion.stockmarket.services.SettingService;
import com.inspirion.stockmarket.services.StockService;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MarketHandler {

    static final long stocksInterval = 30 * 60 * 1000;
    static final long ordersInterval = 01 * 60 * 1000;

    LocalTime startSchedule;
    LocalTime stopSchedule;
    TimerTask stocksTask;
    TimerTask orderTask;
    TimerTask stopTask;
    Map<Integer, TimerTask> tickerTasks;

    SettingService settingService;
    StockService stockService;
    PortfolioService portfolioService;

    public MarketHandler(SettingService settingService,
                         StockService stockService,
                         PortfolioService portfolioService) {
        this.settingService = settingService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
        tickerTasks = new HashMap<>();
    }

    public LocalTime getSchedule() {
        Setting settings = settingService.get();
        //To be retrieved from DB
        startSchedule = LocalTime.of(settings.getStartHour(), settings.getEndMinute());
        stopSchedule = LocalTime.of(settings.getEndHour(), settings.getEndMinute());
        return startSchedule;
    }

    public void start() {
        long millis = ChronoUnit.MILLIS.between(LocalTime.now(), stopSchedule);

        if (millis <= 0)
            return;

        //Start a timer to handle closure of market
        IHandler stopHandler = () -> stop();
        IHandler stocksHandler = () -> fetchStocks();
        IHandler orderHandler = () -> handleOrders();
        stopTask = createTimedTask(stopHandler);
        stocksTask = createTimedTask(stocksHandler);
        orderTask = createTimedTask(orderHandler);
        new Timer().schedule(stocksTask, 0, stocksInterval);
        new Timer().schedule(orderTask, 0, ordersInterval);
        new Timer().schedule(stopTask, millis);

        /*
        1. Start a timer of 30 minutes to get stocks
        1.1 Trigger randomizer
        2. Start a timer of 1 minute to handle orders
        3.
        * */
    }

    private TimerTask createTimedTask(IHandler handler) {
        return new TimerTask() {
            @Override
            public void run() {
                handler.execute();
            }
        };
    }

    private void CancelTask(TimerTask task) {
        if (task != null) task.cancel();
    }

    public void stop() {
        System.out.println("Shutting down the process at " + LocalTime.now());
        CancelTask(stopTask);
        CancelTask(orderTask);
        CancelTask(stocksTask);
        tickerTasks.values().forEach(t -> CancelTask(t));
        System.exit(0);
    }

    private void handleOrders() {
    }

    private void fetchStocks() {
        List<Stock> stocks = stockService.get();
        stocks.forEach(s -> {
            if (!tickerTasks.containsKey(s.getId())) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        s.varyPrice();
                        List<Order> pendingOrders = portfolioService.getPendingOrdersForToday(s.getBasePrice());
                        pendingOrders.forEach(p -> portfolioService.executeOrder(p.id));
                    }
                };
                if (s.getInterval() > 0) {
                    tickerTasks.put(s.getId(), task);
                    new Timer().schedule(task, 1000, s.getInterval() * 1000);
                }

            }
        });
    }
}

