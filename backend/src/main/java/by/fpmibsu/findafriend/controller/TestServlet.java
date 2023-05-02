package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.Application;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var result = Application.get().send(new GetPlacesRequest());
        var out = response.getOutputStream();
        response.setContentType("text/json; charset=UTF-8");
        out.write(new ObjectMapper().writeValueAsString(result).getBytes());
        out.flush();
    }

}