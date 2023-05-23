package by.fpmibsu.findafriend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ServletUtils {
    public static void writeResponse(Object response, ServletOutputStream outputStream) throws IOException {
        try {
            String str = new ObjectMapper().writeValueAsString(response);
            outputStream.write(str.getBytes());
            outputStream.flush();
        } catch (JsonProcessingException e) {
            return;
        }
    }

    public static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("text/json; charset=UTF-8");
    }

    public static String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
