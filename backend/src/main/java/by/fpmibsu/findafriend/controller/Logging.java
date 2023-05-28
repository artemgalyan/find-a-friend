package by.fpmibsu.findafriend.controller;

import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class Logging {
    public static void warnNonAuthorizedAccess(HttpServletRequest request, Logger logger) {
        logger.warn(String.format("Non authorized user (ip: %s) tries to access %s", request.getRemoteAddr(), request.getPathInfo()));
    }
}
