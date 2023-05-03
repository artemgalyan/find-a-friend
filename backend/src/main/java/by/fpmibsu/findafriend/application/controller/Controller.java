package by.fpmibsu.findafriend.application.controller;

import by.fpmibsu.findafriend.application.HandleResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    protected HandleResult Ok(Object value) {
        return new HandleResult(200, value);
    }

    protected HandleResult Ok() {
        return new HandleResult(200);
    }
    // TODO: Добавить подобные
}