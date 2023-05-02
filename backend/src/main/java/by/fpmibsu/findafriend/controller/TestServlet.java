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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var result = Application.get().send(new GetPlacesRequest());
        var out = resp.getOutputStream();
        resp.setContentType("text/json; charset=UTF-8");
        out.write(new ObjectMapper().writeValueAsString(result).getBytes());
        out.flush();
    }

}
