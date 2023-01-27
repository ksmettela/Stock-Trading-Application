package com.inspirion.stockmarket.web.controllers;

import com.inspirion.stockmarket.dao.UserDAO;
import com.inspirion.stockmarket.model.Login;
import com.inspirion.stockmarket.model.User;
import com.inspirion.stockmarket.services.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

;

@Path("/user")
public class UserController {

    UserService userService = new UserService(new UserDAO());

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public User login(Login login) {
        User user = userService.loginUser(login);
        return user;
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public int register(User user) {
        int id = userService.registerUser(user);
        return id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return null;
    }

}
