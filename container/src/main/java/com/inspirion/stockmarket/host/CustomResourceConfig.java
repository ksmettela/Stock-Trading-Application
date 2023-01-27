package com.inspirion.stockmarket.host;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomResourceConfig extends ResourceConfig {
    public CustomResourceConfig() {
        setupCORS();
    }

    private void setupCORS() {
        String allowedOrigins = "*";
        String allowedHeaders = "origin, content-type, accept, authorization";

        Set<String> allowedOriginsSet =
                new HashSet<>(Arrays.asList(allowedOrigins.split(",")));
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.setAllowedOrigins(allowedOriginsSet);
        corsFilter.setAllowedHeaders(allowedHeaders);
        corsFilter.setAllowedMethods("GET, POST, OPTIONS, HEAD,PUT,DELETE");
        register(corsFilter);
        register(JacksonFeature.class);
    }
}
