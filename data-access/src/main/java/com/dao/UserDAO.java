package com.inspirion.stockmarket.dao;

import com.inspirion.stockmarket.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO extends BaseDAO {

    public int createUser(User user) {
        QUERY = "insert into users (fname,lname,email,password,phoneno) values\n" +
                "(:firstName,:lastName,:email,:password,:phoneNumber);";
        int id = namedJdbcTemplate.update(QUERY, new BeanPropertySqlParameterSource(user));
        if (id > 0) {
            Map<String, Integer> map = new HashMap<>();
            map.put("userId", id);
            QUERY = "insert into role_to_user(role_id,user_id) values " +
                    "((select id from roles r2 where name = 'USER'),:userId);";
            namedJdbcTemplate.update(QUERY, map);
        }
        return id;
    }

    public List<User> getAll() {
        QUERY = "select u.id, u.fname,u.lname,u.email,u.phoneno,u.password,r.name as role from users u " +
                "inner join role_to_user rtu on rtu.user_id = u.id " +
                "inner join roles r on rtu.role_id = r.id;";
        List<User> users = namedJdbcTemplate.query(QUERY, new UserRowMapper());
        return users;
    }

    public User get(String name, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("email", name);
        map.put("password", password);
        QUERY = "select u.id, u.fname,u.lname,u.email,u.phoneno,u.password,r.name as role from users u " +
                "inner join role_to_user rtu on rtu.user_id = u.id " +
                "inner join roles r on rtu.role_id = r.id " +
                "where u.email=:email and u.password=:password;";
        List<User> users = namedJdbcTemplate.query(QUERY, map, new UserRowMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("id"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDouble("phoneno"),
                    rs.getString("role")
            );
        }
    }
}
