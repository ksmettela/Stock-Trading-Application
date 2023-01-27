package com.inspirion.stockmarket.web.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/is-alive")
public class IsAliveController {
    @GET
    public String getMessage() {
        return "success";
    }
}
