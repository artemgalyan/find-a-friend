using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace Backend.Repository;

public interface ITokenRepository
{
    public Task<bool> IsValidTokenAsync(string token, CancellationToken cancellationToken = default);
    public Task InvalidateTokensAsync(int userId, CancellationToken cancellationToken);
    public ValueTask<EntityEntry<AuthToken>> AddTokenAsync(AuthToken token, CancellationToken cancellationToken);
}

public class TokenRepository : ITokenRepository
{
    private readonly DbSet<AuthToken> _dbSet;

    public TokenRepository(ApplicationDbContext context)
    {
        _dbSet = context.Tokens;
    }

    public async Task<bool> IsValidTokenAsync(string token, CancellationToken cancellationToken = default)
    {
        return await _dbSet.Where(a => a.Token == token).FirstOrDefaultAsync(cancellationToken) is not null;
    }

    public Task InvalidateTokensAsync(int userId, CancellationToken cancellationToken)
    {
        return _dbSet.Where(t => t.UserId == userId)
                     .ExecuteDeleteAsync(cancellationToken);
    }

    public ValueTask<EntityEntry<AuthToken>> AddTokenAsync(AuthToken token, CancellationToken cancellationToken)
    {
        return _dbSet.AddAsync(token, cancellationToken);
    }
}