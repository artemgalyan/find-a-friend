package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.serviceproviders.ScopedServiceProvider;
import by.fpmibsu.findafriend.application.utils.ObjectConstructor;
import by.fpmibsu.findafriend.controller.ServletUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ControllerMethodInvoker {
    private final static Logger logger = LogManager.getLogger();

    public static HandleResult invoke(HttpServletRequest request, HttpServletResponse response,
                                      EndpointInfo endpointInfo, ScopedServiceProvider sp) {

        var controller = ObjectConstructor.createInstance(endpointInfo.controller(), sp);
        logger.trace("Created controller " + endpointInfo.controller().getName());
        controller.setRequest(request);
        controller.setResponse(response);
        controller.setServiceProvider(sp);
        var method = endpointInfo.method();
        if (method.isAnnotationPresent(RequireAuthentication.class) &&
                !sp.getRequiredService(Application.AuthenticationData.class).isTokenValid()) {
            return new HandleResult(HttpServletResponse.SC_FORBIDDEN);
        }
        var claims = sp.getRequiredService(Application.AuthenticationData.class).claims();
        var parameters = method.getParameters();
        var methodParams = new Object[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            var parameter = parameters[i];
            try {
                if (parameter.isAnnotationPresent(FromQuery.class)) {
                    var annotation = parameter.getAnnotation(FromQuery.class);
                    methodParams[i] = readFromQuery(annotation.parameterName(), request, parameter.getType());
                } else if (parameter.isAnnotationPresent(FromBody.class)) {
                    methodParams[i] = readFromBody(request, parameter.getType());
                } else if (parameter.isAnnotationPresent(WebToken.class)) {
                    var annotation = parameter.getAnnotation(WebToken.class);
                    methodParams[i] = tryParseObject(claims.getClaimValueAsString(annotation.parameterName()),
                            parameter.getType());
                } else {
                    return new HandleResult(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (Exception e) {
                return new HandleResult(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        try {
            var result = (HandleResult) method.invoke(controller, methodParams);
            return result;
        } catch (Exception e) {
            return new HandleResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            try {
                sp.close();
            } catch (Exception e) {

            }
        }
    }

    private static Object readFromQuery(String key, HttpServletRequest request, Class<?> type) throws JsonProcessingException {
        if (request.getParameter(key) == null) {
            logger.error("arg is null in query");
            throw new RuntimeException("Arg is null");
        }
        return tryParseObject(request.getParameter(key), type);
    }

    private static Object readFromBody(HttpServletRequest request, Class<?> type) throws IOException {
        return tryParseObject(ServletUtils.getBody(request), type);
    }

    private static Object tryParseObject(String s, Class<?> type) throws JsonProcessingException {
        if (String.class.equals(type)) {
            return s;
        }
        if (!type.isPrimitive()) {
            return new ObjectMapper().readValue(s, type);
        }
        if (int.class.equals(type)) {
            return Integer.parseInt(s);
        }
        if (double.class.equals(type)) {
            return Double.parseDouble(s);
        }
        if (float.class.equals(type)) {
            return Float.parseFloat(s);
        }
        if (char.class.equals(type)) {
            if (s.length() > 1) {
                throw new RuntimeException("Too long string");
            }
            return s.charAt(0);
        }
        if (boolean.class.equals(type)) {
            return Boolean.parseBoolean(s);
        }
        if (byte.class.equals(type)) {
            return Byte.parseByte(s);
        }
        if (short.class.equals(type)) {
            return Short.parseShort(s);
        }
        if (long.class.equals(type)) {
            return Long.parseLong(s);
        }
        logger.error("incorrect type of object");
        throw new RuntimeException("Unreachable");
    }
}
