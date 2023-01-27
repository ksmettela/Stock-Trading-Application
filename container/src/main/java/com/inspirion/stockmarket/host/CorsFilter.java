package com.inspirion.stockmarket.host;


import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@PreMatching
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    protected boolean allowCredentials = true;
    protected String allowedMethods;
    protected String allowedHeaders;
    protected String exposedHeaders;
    protected int corsMaxAge = -1;
    private Set<String> allowedOrigins = new HashSet<String>();
    private ContainerRequestContext containerRequestContext;

    /**
     * Put "*" if you want to accept all origins
     *
     * @return
     */
    public Set<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(Set<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins.stream().map(s -> s.trim()).collect(Collectors.toSet());
    }

    /**
     * Will allow all by default
     *
     * @return
     */
    public String getAllowedMethods() {
        return allowedMethods;
    }

    /**
     * Will allow all by default
     * comma delimited string for Access-Control-Allow-Methods
     *
     * @param allowedMethods
     */
    public void setAllowedMethods(String allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String getAllowedHeaders() {
        return allowedHeaders;
    }

    /**
     * Will allow all by default
     * comma delimited string for Access-Control-Allow-Headers
     *
     * @param allowedHeaders
     */
    public void setAllowedHeaders(String allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public int getCorsMaxAge() {
        return corsMaxAge;
    }

    public void setCorsMaxAge(int corsMaxAge) {
        this.corsMaxAge = corsMaxAge;
    }

    public String getExposedHeaders() {
        return exposedHeaders;
    }

    /**
     * comma delimited list
     *
     * @param exposedHeaders
     */
    public void setExposedHeaders(String exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String origin = requestContext.getHeaderString(CorsHeaders.ORIGIN);
        if (origin == null) {
            return;
        }
        origin = origin.toLowerCase();
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            preflight(origin, requestContext);
        } else {
            checkOrigin(requestContext, origin);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString(CorsHeaders.ORIGIN);
        if (origin == null)
            return;
        origin = origin.toLowerCase();
        if (origin == null || requestContext.getMethod().equalsIgnoreCase("OPTIONS") || requestContext.getProperty("cors.failure") != null) {
            // don't do anything if origin is null, its an OPTIONS request, or cors.failure is set
            return;
        }
        responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        responseContext.getHeaders().putSingle(CorsHeaders.VARY, CorsHeaders.ORIGIN);
        if (allowCredentials)
            responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

        if (exposedHeaders != null) {
            responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeaders);
        }
    }


    protected void preflight(String origin, ContainerRequestContext requestContext) throws IOException {
        checkOrigin(requestContext, origin);

        Response.ResponseBuilder builder = Response.ok();
        builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        builder.header(CorsHeaders.VARY, CorsHeaders.ORIGIN);
        if (allowCredentials) builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        String requestMethods = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_METHOD);
        if (requestMethods != null) {
            if (allowedMethods != null) {
                requestMethods = this.allowedMethods;
            }
            builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethods);
        }
        String allowHeaders = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        if (allowHeaders != null) {
            if (allowedHeaders != null) {
                allowHeaders = this.allowedHeaders;
            }
            builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
        }
        if (corsMaxAge > -1) {
            builder.header(CorsHeaders.ACCESS_CONTROL_MAX_AGE, corsMaxAge);
        }
        requestContext.abortWith(builder.build());

    }

    protected void checkOrigin(ContainerRequestContext requestContext, String origin) {
        if (!getAllowedOrigins().contains("*") && !getAllowedOrigins().contains(origin)) {
            requestContext.setProperty("cors.failure", true);
            throw new ForbiddenException(String.format("Origin not allowed: %s", origin));
        }
    }
}

class CorsHeaders
{
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    public static final String ORIGIN = "Origin";
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    public static final String VARY = "Vary";
}