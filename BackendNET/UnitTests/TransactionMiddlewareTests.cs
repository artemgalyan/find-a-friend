using Backend.Middleware;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.Extensions.Logging;
using Moq;

namespace UnitTests;

public class TransactionMiddlewareTests
{
    private readonly Mock<RequestDelegate> _requestDelegateMock = new();
    private readonly Mock<IDbContextTransaction> _dbTransactionMock = new();
    private readonly ILogger<TransactionMiddleware<FakeDbContext>> _loggerMock = new FakeLogger();
    
    [Theory]
    [InlineData("GET")]
    [InlineData("OPTIONS")]
    [InlineData("TRACE")]
    public async Task DoesntCreateTransactionTests(string method)
    {
        var ctx = new FakeDbContext();
        _requestDelegateMock.Setup(r => r.Invoke(It.IsAny<HttpContext>()))
                            .Returns(Task.CompletedTask);
        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object, _loggerMock);
        var fakeHttpContext = CreateFakeHttpContext(method);
        await middleware.InvokeAsync(fakeHttpContext, ctx);
        Assert.Equal(0, ctx.Facade.InvocationsCount);
    }

    [Theory]
    [InlineData("POST")]
    [InlineData("PUT")]
    [InlineData("DELETE")]
    [InlineData("PATCH")]
    public async Task CreatesTransaction(string method)
    {
        var ctx = new FakeDbContext();
        _requestDelegateMock.Setup(r => r.Invoke(It.IsAny<HttpContext>()))
                            .Returns(Task.CompletedTask);

        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object, _loggerMock);
        var fakeHttpContext = CreateFakeHttpContext(method);
        ctx.Facade.TransactionToReturn = _dbTransactionMock.Object;
        await middleware.InvokeAsync(fakeHttpContext, ctx);
        Assert.Equal(1, ctx.Facade.InvocationsCount);
        Assert.Equal(1, ctx.SavesCount);
        _dbTransactionMock.Verify(t => t.CommitAsync(It.IsAny<CancellationToken>()), Times.Once);
        _dbTransactionMock.Verify(t => t.DisposeAsync(), Times.Once);
    }
    
    [Theory]
    [InlineData("POST")]
    [InlineData("PUT")]
    [InlineData("DELETE")]
    public async Task RollbackOnException(string method)
    {
        var ctx = new FakeDbContext();
        _requestDelegateMock.Setup(r => r.Invoke(It.IsAny<HttpContext>()))
                            .Throws<Exception>();
        
        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object, _loggerMock);
        var fakeHttpContext = CreateFakeHttpContext(method);
        ctx.Facade.TransactionToReturn = _dbTransactionMock.Object;
        await Assert.ThrowsAsync<Exception>(async () => await middleware.InvokeAsync(fakeHttpContext, ctx));
        Assert.Equal(1, ctx.Facade.InvocationsCount);
        _dbTransactionMock.Verify(t => t.RollbackAsync(It.IsAny<CancellationToken>()), Times.Once);
        _dbTransactionMock.Verify(t => t.DisposeAsync(), Times.Once);
    }

    private static HttpContext CreateFakeHttpContext(string method) => new DefaultHttpContext { Request = { Method = method } };
}

internal class FakeDbContext : DbContext
{
    public FakeDatabaseFacade Facade { get; set; }
    public override DatabaseFacade Database => Facade;

    public int SavesCount { get; private set; } = 0;
    
    public FakeDbContext()
    {
        Facade = new FakeDatabaseFacade(this);
    }

    public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = default)
    {
        ++SavesCount;
        return Task.FromResult(1);
    }
}

internal class FakeDatabaseFacade : DatabaseFacade
{
    public IDbContextTransaction TransactionToReturn { get; set; } = null!;
    public int InvocationsCount { get; private set; } = 0;
    
    public FakeDatabaseFacade(DbContext context) : base(context) {}

    public override Task<IDbContextTransaction> BeginTransactionAsync(CancellationToken cancellationToken = new())
    {
        ++InvocationsCount;
        return Task.FromResult(TransactionToReturn);
    }
}

internal class FakeLogger : ILogger<TransactionMiddleware<FakeDbContext>>
{
    public IDisposable? BeginScope<TState>(TState state) where TState : notnull
    {
        return null;
    }

    public bool IsEnabled(LogLevel logLevel)
    {
        return true;
    }

    public void Log<TState>(LogLevel logLevel, EventId eventId, TState state, Exception? exception, Func<TState, Exception?, string> formatter)
    {
        
    }
}