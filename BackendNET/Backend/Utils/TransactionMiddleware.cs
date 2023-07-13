using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;

namespace Backend.Utils;

public class TransactionMiddleware<TDbContext> where TDbContext : DbContext
{
    private readonly RequestDelegate _next;

    public TransactionMiddleware(RequestDelegate next)
    {
        _next = next;
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
        catch (Exception e)
        {
            await transaction.RollbackAsync();
            await transaction.DisposeAsync();
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