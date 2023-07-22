using System.Diagnostics;

namespace Backend.Middleware;

public class RequestDurationMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger<RequestDurationMiddleware> _logger;

    public RequestDurationMiddleware(RequestDelegate next, ILogger<RequestDurationMiddleware> logger)
    {
        _next = next;
        _logger = logger;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        var watch = new Stopwatch();
        watch.Start();
        await _next(context);
        watch.Stop();
        _logger.LogInformation("Request to {Method} was handled in {Milliseconds}ms", context.Request.Path,
            watch.ElapsedMilliseconds);
    }
}