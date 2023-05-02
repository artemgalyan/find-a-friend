package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.controller.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/users/getAll")
public class GetUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var response = Application.get().send(new GetUsersQuery());
        ServletUtils.setResponseHeaders(resp);
        ServletUtils.writeResponse(response, resp.getOutputStream());
    }
}