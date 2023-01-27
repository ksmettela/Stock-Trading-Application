package com.inspirion.stockmarket.dao;

import com.inspirion.stockmarket.entity.Stock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StocksDAO extends BaseDAO {

    public List<Stock> getAll() {
        QUERY = "select * from stocks";
        List<Stock> stocks = jdbcTemplate.query(QUERY, new StockRowMapper());
        return stocks;
    }

    public Stock getById(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        QUERY = "select * from stocks where id=:id";
        List<Stock> stocks = namedJdbcTemplate.query(QUERY, map, new StockRowMapper());
        return stocks.size() > 0 ? stocks.get(0) : null;
    }

    public int update(Stock stock) {
        QUERY = "update stocks set base_price=:price, volume=:volume where id=:id;";
        return namedJdbcTemplate.update(QUERY, new BeanPropertySqlParameterSource(stock));
    }

    public int add(Stock stock) {
        QUERY = "insert into stocks(company_name,base_price,volume,variation,interval)" +
                "values(:name,:basePrice,:volume,:margin,:interval);";
        return namedJdbcTemplate.update(QUERY, new BeanPropertySqlParameterSource(stock));
    }

    public class StockRowMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Stock(
                    rs.getInt("id"),
                    rs.getString("company_name"),
                    rs.getDouble("base_price"),
                    rs.getDouble("variation"),
                    rs.getInt("interval"),
                    rs.getInt("volume")
            );
        }
    }
}
