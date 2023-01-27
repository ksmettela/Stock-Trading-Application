package com.inspirion.stockmarket.host;

import com.inspirion.stockmarket.util.ConfigManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) {

        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jServer = new Server(
                ConfigManager.getInstance().getConfiguration().getWebPort());
        jServer.setHandler(context);

        ServletHolder jServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
//        jServlet.setInitParameter("jersey.config.server.provider.classnames", "com.inspirion.stockmarket.controller");
        jServlet.setInitParameter("jersey.config.server.provider.packages", "com.inspirion.stockmarket.web.controllers");
        jServlet.setInitParameter("javax.ws.rs.Application", "com.inspirion.stockmarket.host.CustomResourceConfig");

        try {
            jServer.start();
            jServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jServer.destroy();
        }
    }
}
