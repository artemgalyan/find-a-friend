package by.fpmibsu.find_a_friend.application.controllers;

import by.fpmibsu.find_a_friend.application.HttpMethod;
import by.fpmibsu.find_a_friend.application.endpoints.RequestData;
import by.fpmibsu.find_a_friend.application.mediatr.RequestHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Endpoint {
    String route();
    HttpMethod method() default HttpMethod.GET;
    Class<? extends RequestHandler<?, ?>> requestHandler();
    RequestData requestData() default RequestData.FROM_BODY;
}
