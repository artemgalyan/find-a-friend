using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace Backend.Repository;

public interface IUserShelterRepository : IRepository<UserShelter, (int UserId, int ShelterId)>
{
    public Task<int> RemoveByUserIdAsync(int userId, CancellationToken cancellationToken = default);
    public Task<int> RemoveByShelterIdAsync(int shelterId, CancellationToken cancellationToken = default);
    public Task<List<int>> GetUsersByShelterIdAsync(int shelterId, CancellationToken cancellationToken = default);
    public Task<int?> GetShelterByUserIdAsync(int userId, CancellationToken cancellationToken = default);
}

public class UserShelterRepository : IUserShelterRepository
{
    private readonly DbSet<UserShelter> _dbSet;

    public UserShelterRepository(ApplicationDbContext context)
    {
        _dbSet = context.UserShelters;
    }

    public Task<List<UserShelter>> GetAllAsync(CancellationToken cancellationToken = default)
    {
        return _dbSet.ToListAsync(cancellationToken);
    }

    public ValueTask<UserShelter?> GetByIdAsync((int UserId, int ShelterId) key,
        CancellationToken cancellationToken = default)
    {
        return _dbSet.FindAsync(new object?[] { key.UserId, key.ShelterId }, cancellationToken);
    }

    public ValueTask<EntityEntry<UserShelter>> InsertAsync(UserShelter entity,
        CancellationToken cancellationToken = default)
    {
        return _dbSet.AddAsync(entity, cancellationToken);
    }

    public Task InsertRangeAsync(IEnumerable<UserShelter> entities, CancellationToken cancellationToken = default)
    {
        return _dbSet.AddRangeAsync(entities, cancellationToken);
    }

    public Task<int> DeleteAsync((int UserId, int ShelterId) entityKey, CancellationToken cancellationToken = default)
    {
        return _dbSet.Where(e => e.UserId == entityKey.UserId && e.ShelterId == entityKey.ShelterId)
                     .ExecuteDeleteAsync(cancellationToken);
    }

    public Task UpdateAsync(UserShelter entity, CancellationToken cancellationToken = default)
    {
        _dbSet.Update(entity);
        return Task.CompletedTask;
    }

    public Task DeleteAsync(UserShelter entity, CancellationToken cancellationToken = default)
    {
        _dbSet.Remove(entity);
        return Task.CompletedTask;
    }

    public Task<int> RemoveByUserIdAsync(int userId, CancellationToken cancellationToken)
    {
        return _dbSet.Where(e => e.UserId == userId)
                     .ExecuteDeleteAsync(cancellationToken);
    }

    public Task<int> RemoveByShelterIdAsync(int shelterId, CancellationToken cancellationToken)
    {
        return _dbSet.Where(e => e.ShelterId == shelterId)
                     .ExecuteDeleteAsync(cancellationToken);
    }

    public Task<List<int>> GetUsersByShelterIdAsync(int shelterId, CancellationToken cancellationToken)
    {
        return _dbSet.Where(e => e.ShelterId == shelterId)
                     .Select(e => e.UserId)
                     .ToListAsync(cancellationToken);
    }

    public async Task<int?> GetShelterByUserIdAsync(int userId, CancellationToken cancellationToken = default)
    {
        var result = await _dbSet.Where(e => e.UserId == userId)
                                 .Select(x => x.UserId)
                                 .FirstOrDefaultAsync(cancellationToken);
        return result == 0
            ? null
            : result;
    }
}