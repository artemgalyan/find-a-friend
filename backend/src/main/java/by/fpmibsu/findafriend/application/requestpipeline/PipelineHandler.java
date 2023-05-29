package by.fpmibsu.findafriend.application.requestpipeline;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.ScopedServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PipelineHandler {
    HandleResult handle(HttpServletRequest request, HttpServletResponse response,
                        ScopedServiceProvider scopedServiceProvider, EndpointInfo endpointInfo,
                        PipelineHandler next) throws Exception;
}
