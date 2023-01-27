package com.inspirion.stockmarket.dao;

import com.inspirion.stockmarket.entity.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO extends BaseDAO {

    public int placeOrder(Order order) {
        QUERY = "insert into orders(user_id,stock_id,quantity,type,status,unit_price)" +
                "values(:userId,:stockId,:quantity,:orderType,:status,:unitPrice);";
        return namedJdbcTemplate.update(QUERY, new BeanPropertySqlParameterSource(order));
    }

    public Order getById(long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        QUERY = "select * from orders where id=:id";
        List<Order> o = namedJdbcTemplate.query(QUERY, map, new OrderRowMapper());
        return o.size() > 0 ? o.get(0) : null;
    }

    public List<Order> getPendingOrders(Order order) {
        Map<String, Object> map = new HashMap<>();
        String clause = "";
        map.put("status", order.getStatus());
        map.put("orderDate", order.getOrderDate());
        map.put("unitPrice", order.getUnitPrice());

        QUERY = "select * from orders o \n" +
                "where status = :status and unit_price=:unitPrice and placing_on::date = :orderDate;";

        return namedJdbcTemplate.query(QUERY, map, new OrderRowMapper());
    }

    public List<UserOrder> getOrdersForUser(int userId, OrderStatus executed) {
        Map<String, Object> map = new HashMap<>();
        String clause = "";
        map.put("userId", userId);
        QUERY = "select o.id, s.company_name as stock, o.quantity,o.unit_price,o.type,o.placing_on,o.status from orders o \n" +
                "inner join stocks s on o.stock_id = s.id  where user_id = :userId";
        if (executed != null) {
            map.put("status", executed.toString());
            clause = " and o.status = :status";
        }
        QUERY = new StringBuilder(QUERY).append(clause).append(";").toString();
        return namedJdbcTemplate.query(QUERY, map, new UserOrderMapper());
    }

    public List<Transaction> getUserTransactions(int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        QUERY = "select * from user_funds \n" +
                "where user_id = :userId";
        return namedJdbcTemplate.query(QUERY, map, new TransactionRowMapper());
    }

    public int updateFunds(long userId, double amount, String orderType, long transactionId) {
        String tranId = "", tranVal = "";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("amount", amount);
        map.put("orderType", orderType);
        if (transactionId > 0) {
            tranId = ",transaction_id";
            map.put("transactionId", transactionId);
            tranVal = ",:transactionId";
        }
        StringBuilder s = new StringBuilder("insert into user_funds(user_id,transcation_type,amount");
        s.append(tranId).append(")values(:userId,:orderType,:amount").append(tranVal).append(");");
        QUERY = s.toString();
        return namedJdbcTemplate.update(QUERY, map);
    }

    public double getFundsForUser(long userId) {
        class Temp {
            public double amount, userId;

            public Temp(double amount, double userId) {
                this.amount = amount;
                this.userId = userId;
            }
        }
        class Mapper implements RowMapper<Temp> {

            @Override
            public Temp mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Temp(rs.getDouble("sum"), rs.getDouble("user_id"));
            }
        }
        QUERY = "select user_id, sum(amount) from user_funds where user_id=:userId group by user_id;";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<Temp> order = namedJdbcTemplate.query(QUERY, map, new Mapper());
        return (order.size() > 0) ? order.get(0).amount : 0;
    }

    public int updateOrderStatus(Order order) {
        QUERY = "update orders set status=:status where id=:id;";
        return namedJdbcTemplate.update(QUERY, new BeanPropertySqlParameterSource(order));
    }

    public class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getInt("quantity"),
                    rs.getString("type"),
                    rs.getString("status"),
                    rs.getDate("placing_on"),
                    rs.getDouble("unit_price")
            );
        }
    }

    public class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Transaction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("transaction_id"),
                    rs.getDouble("amount"),
                    OrderType.values()[rs.getInt("transcation_type")],
                    rs.getDate("transaction_date")
            );
        }
    }

    public class UserOrderMapper implements RowMapper<UserOrder> {

        @Override
        public UserOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserOrder(
                    rs.getLong("id"),
                    rs.getLong("quantity"),
                    rs.getLong("unit_price"),
                    rs.getString("stock"),
                    rs.getString("type"),
                    rs.getString("status"),
                    rs.getDate("placing_on")
            );
        }
    }
}
