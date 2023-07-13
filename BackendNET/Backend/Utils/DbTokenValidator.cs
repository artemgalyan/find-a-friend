using Backend.Repository;

namespace Backend.Utils;

public class DbTokenValidator
{
    private readonly RequestDelegate _next;

    public DbTokenValidator(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context, ITokenRepository tokenRepository)
    {
        var token = context.Request.Query["token"].FirstOrDefault();
        if (token is null)
        {
            await _next(context);
            return;
        }

        if (await tokenRepository.IsValidTokenAsync(token))
        {
            await _next(context);
            return;
        }

        context.Response.StatusCode = StatusCodes.Status401Unauthorized;
        var writer = new StreamWriter(context.Response.Body);
        await writer.WriteAsync("The token is invalid");
    }
}