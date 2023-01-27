package com.inspirion.stockmarket.services;

import com.inspirion.stockmarket.dao.StocksDAO;
import com.inspirion.stockmarket.entity.Stock;

import java.util.List;

public class StockService {
    StocksDAO stocksDAO;

    public StockService(StocksDAO dao) {
        stocksDAO = dao;
    }

    public List<Stock> get() {
        return stocksDAO.getAll();
    }

    public Stock get(int id) {
        return stocksDAO.getById(id);
    }

    public int create(Stock stock) {
       return stocksDAO.add(stock);
    }
    public void update(Stock stock) {
        stocksDAO.update(stock);
    }
}
