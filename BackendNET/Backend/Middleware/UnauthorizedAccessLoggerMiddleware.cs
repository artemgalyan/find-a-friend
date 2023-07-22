namespace Backend.Middleware;

public class UnauthorizedAccessLoggerMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger<UnauthorizedAccessLoggerMiddleware> _logger;

    public UnauthorizedAccessLoggerMiddleware(RequestDelegate next, ILogger<UnauthorizedAccessLoggerMiddleware> logger)
    {
        _next = next;
        _logger = logger;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        await _next(context);
        if (context.Response.StatusCode == StatusCodes.Status401Unauthorized)
        {
            _logger.LogWarning("Unauthorized access to {Path} from IP {Ip}", context.Request.Path, context.Connection.RemoteIpAddress);
        }
    }
}