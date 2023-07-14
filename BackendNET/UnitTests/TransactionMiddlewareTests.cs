using System.Data;
using Backend.Utils;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage;
using Moq;

namespace UnitTests;

public class TransactionMiddlewareTests
{
    private readonly Mock<RequestDelegate> _requestDelegateMock = new();
    private readonly Mock<IDbContextTransaction> _dbTransactionMock = new();
    
    [Theory]
    [InlineData("GET")]
    [InlineData("OPTIONS")]
    [InlineData("TRACE")]
    public async Task DoesntCreateTransactionTests(string method)
    {
        var ctx = new FakeDbContext();
        _requestDelegateMock.Setup(r => r.Invoke(It.IsAny<HttpContext>()))
                            .Returns(Task.CompletedTask);
        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object);
        var fakeHttpContext = CreateFakeHttpContext(method);
        await middleware.InvokeAsync(fakeHttpContext, ctx);
        Assert.Equal(0, ctx.Facade.InvocationsCount);
    }

    [Theory]
    [InlineData("POST")]
    [InlineData("PUT")]
    [InlineData("DELETE")]
    public async Task CreatesTransaction(string method)
    {
        var ctx = new FakeDbContext();
        _requestDelegateMock.Setup(r => r.Invoke(It.IsAny<HttpContext>()))
                            .Returns(Task.CompletedTask);
        
        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object);
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
        
        var middleware = new TransactionMiddleware<FakeDbContext>(_requestDelegateMock.Object);
        var fakeHttpContext = CreateFakeHttpContext(method);
        ctx.Facade.TransactionToReturn = _dbTransactionMock.Object;
        await Assert.ThrowsAsync<Exception>(async () => await middleware.InvokeAsync(fakeHttpContext, ctx));
        Assert.Equal(1, ctx.Facade.InvocationsCount);
        _dbTransactionMock.Verify(t => t.RollbackAsync(It.IsAny<CancellationToken>()), Times.Once);
        _dbTransactionMock.Verify(t => t.DisposeAsync(), Times.Once);
    }

    private HttpContext CreateFakeHttpContext(string method) => new DefaultHttpContext { Request = { Method = method } };
}

file class FakeDbContext : DbContext
{
    public FakeDatabaseFacade Facade { get; set; }
    public override DatabaseFacade Database => Facade;

    public int SavesCount { get; set; } = 0;
    
    public FakeDbContext()
    {
        Facade = new FakeDatabaseFacade(this);
    }

    public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = new CancellationToken())
    {
        ++SavesCount;
        return Task.FromResult(1);
    }
}

file class FakeDatabaseFacade : DatabaseFacade
{
    public IDbContextTransaction TransactionToReturn { get; set; }
    public int InvocationsCount { get; set; } = 0;
    
    public FakeDatabaseFacade(DbContext context) : base(context)
    {
    }

    public override async Task<IDbContextTransaction> BeginTransactionAsync(CancellationToken cancellationToken = new CancellationToken())
    {
        ++InvocationsCount;
        return TransactionToReturn;
    }
}