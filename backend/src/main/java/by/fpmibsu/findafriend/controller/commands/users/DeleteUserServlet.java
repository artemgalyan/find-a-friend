package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.controller.QueryParser;
import by.fpmibsu.findafriend.controller.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/users/delete")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var query = QueryParser.parseQuery(req.getQueryString());
        var command = DeleteUserCommand.tryCreate(query);
        if (command.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        var result = Application.get().send(command.get());
        ServletUtils.setResponseHeaders(resp);
        ServletUtils.writeResponse(result, resp.getOutputStream());
    }
}