package by.fpmibsu.findafriend.application.requestpipeline;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.ScopedServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PipelineSender implements PipelineHandler {
    private final List<PipelineHandler> handlers;
    private int index = 0;

    public PipelineSender(List<PipelineHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public HandleResult handle(HttpServletRequest request, HttpServletResponse response,
                               ScopedServiceProvider scopedServiceProvider,
                               EndpointInfo endpointInfo, PipelineHandler next) {
        var handler = handlers.get(index);
        ++index;
        return handler.handle(request, response, scopedServiceProvider, endpointInfo, this);
    }
}
