package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.controller.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/users/update")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var request = new ObjectMapper().readValue(ServletUtils.getBody(req), UpdateUserCommand.class);
        var response = Application.get().send(request);
        ServletUtils.setResponseHeaders(resp);
        ServletUtils.writeResponse(response, resp.getOutputStream());
    }
}
