package com.inspirion.stockmarket.util;

public class Connection {
    protected String host;
    protected String database;
    protected int port;
    protected String userName;
    protected String password;
    protected String schema;

    public void setHost(String host) {
        this.host = host;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUrl(){
        StringBuilder builder = new StringBuilder();
        //jdbc:postgresql://localhost:5432/postgres?currentSchema=public
        return builder
                .append("jdbc:postgresql://")
                .append(host).append(":").append(port).append("/")
                .append(database).append("?currentSchema=")
                .append(schema)
                .toString();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
