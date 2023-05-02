package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.controller.QueryParser;
import by.fpmibsu.findafriend.controller.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/users/get")
public class GetUserByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var query = QueryParser.parseQuery(request.getQueryString());
        var req = GetUserByIdQuery.tryCreate(query);
        if (req.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var result = Application.get().send(req.get());
        ServletUtils.setResponseHeaders(response);
        ServletUtils.writeResponse(result, response.getOutputStream());
    }
}