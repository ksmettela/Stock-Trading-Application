package com.inspirion.stockmarket.services;

import com.inspirion.stockmarket.dao.UserDAO;
import com.inspirion.stockmarket.model.User;
import com.inspirion.stockmarket.model.Login;

import java.security.InvalidParameterException;

public class UserService {
    UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public User loginUser(Login u) {
        User user = dao.get(u.email, u.password);
        if (null == user) throw new InvalidParameterException("User does not exists.");
        return user;
    }
    public int registerUser(User user) {
        int id = dao.createUser(
                (com.inspirion.stockmarket.entity.User)user);
        if(id > 0)
            return id;
        throw new IllegalArgumentException("User could not be added");
    }
}
