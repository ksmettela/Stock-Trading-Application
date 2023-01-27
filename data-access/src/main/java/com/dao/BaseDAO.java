package com.inspirion.stockmarket.dao;

import com.inspirion.stockmarket.util.ConfigManager;
import com.inspirion.stockmarket.util.Connection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

class BaseDAO {
    protected static DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;
    protected String QUERY;

    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    public BaseDAO() {
        dataSource = Source.getInstance().getDataSource();
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    static class Source {
        private DataSource dataSource;
        private static Connection dbConnection;
        private static Source instance;

        private Source() {
            dbConnection = ConfigManager.getInstance()
                    .getConfiguration().getConnection();
            if (dbConnection != null && null != dbConnection.getUrl()) {
                this.dbConnection = dbConnection;
            } else
                throw new IllegalArgumentException("Invalid Database Connection String");
        }

        public static Source getInstance() {
            if (instance == null) {
                instance = new Source();
                instance.dataSource = new SingleConnectionDataSource(
                        dbConnection.getUrl(),
                        dbConnection.getUserName(),
                        dbConnection.getPassword(), true);
            }
            return instance;
        }

        public DataSource getDataSource() {
            return instance.dataSource;
        }
    }
}


