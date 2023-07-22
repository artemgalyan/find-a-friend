using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;

namespace Backend.Middleware;

public class TransactionMiddleware<TDbContext> where TDbContext : DbContext
{
    private readonly RequestDelegate _next;
    private readonly ILogger<TransactionMiddleware<TDbContext>> _logger;

    public TransactionMiddleware(RequestDelegate next, ILogger<TransactionMiddleware<TDbContext>> logger)
    {
        _next = next;
        _logger = logger;
    }

    public async Task InvokeAsync(HttpContext context, TDbContext dbContext)
    {
        var method = context.Request.Method.ToUpper();
        if (!MethodChangesState(method))
        {
            await _next(context);
            return;
        }

        IDbContextTransaction transaction = await dbContext.Database.BeginTransactionAsync();
        try
        {
            await _next(context);
        }
        catch (Exception exception)
        {
            await transaction.RollbackAsync();
            await transaction.DisposeAsync();
            _logger.LogError("Exception during request to {RequestMethod}: {Exception}", context.Request.Path, exception.Message);
            throw;
        }

        await transaction.CommitAsync();
        await dbContext.SaveChangesAsync();
        await transaction.DisposeAsync();
    }

    private static bool MethodChangesState(string method)
    {
        return method is "POST" or "PUT" or "DELETE";
    }
}