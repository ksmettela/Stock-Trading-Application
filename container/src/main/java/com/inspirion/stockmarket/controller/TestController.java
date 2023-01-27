package com.inspirion.stockmarket.controller;

import javax.ws.rs.*;

@Path("/test")
public class TestController {
    @GET
    @Path("/check")
    public boolean get() {
        return true;
    }
    @GET
    public String getMessage() {
        return "success";
    }
}
