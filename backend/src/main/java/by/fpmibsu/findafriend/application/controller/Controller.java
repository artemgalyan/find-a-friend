package by.fpmibsu.findafriend.application.controller;

import by.fpmibsu.findafriend.application.HandleResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

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
    // TODO: Добавить подобные
    protected static HandleResult notAuthorized() { return new HandleResult(HttpServletResponse.SC_FORBIDDEN); }
}