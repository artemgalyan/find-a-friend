package by.fpmibsu.findafriend.application.controller;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServiceProvider serviceProvider;

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    protected HandleResult ok(Object value) {
        return new HandleResult(HttpServletResponse.SC_OK, value);
    }

    protected static HandleResult ok() {
        return new HandleResult(HttpServletResponse.SC_OK);
    }

    protected static HandleResult notAuthorized() {
        return new HandleResult(HttpServletResponse.SC_FORBIDDEN);
    }

    protected static HandleResult notAuthorized(Object value) {
        return new HandleResult(HttpServletResponse.SC_FORBIDDEN, value);
    }

    protected static HandleResult badRequest() {
        return new HandleResult(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected static HandleResult badRequest(Object value) {
        return new HandleResult(HttpServletResponse.SC_BAD_REQUEST, value);
    }
}