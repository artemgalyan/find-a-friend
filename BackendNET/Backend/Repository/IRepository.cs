using Backend.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace Backend.Repository;

public interface IRepository<TEntity, in TKey> where TEntity : class
{
    public Task<List<TEntity>> GetAllAsync(CancellationToken cancellationToken = default);
    public ValueTask<TEntity?> GetByIdAsync(TKey key, CancellationToken cancellationToken = default);
    public ValueTask<EntityEntry<TEntity>> InsertAsync(TEntity entity, CancellationToken cancellationToken = default);
    public Task InsertRangeAsync(IEnumerable<TEntity> entities, CancellationToken cancellationToken = default);
    public Task<int> DeleteAsync(TKey entityKey, CancellationToken cancellationToken = default);
    public Task UpdateAsync(TEntity entity, CancellationToken cancellationToken = default);
    public Task DeleteAsync(TEntity entity, CancellationToken cancellationToken = default);
}

public abstract class RepositoryImpl<TEntity> : IRepository<TEntity, int> where TEntity : Entity
{
    protected abstract DbSet<TEntity> DbSet { get; set; }

    public Task<List<TEntity>> GetAllAsync(CancellationToken cancellationToken = default)
    {
        return DbSet.ToListAsync(cancellationToken);
    }

    public ValueTask<TEntity?> GetByIdAsync(int key, CancellationToken cancellationToken = default)
    {
        return DbSet.FindAsync(new object?[] { key }, cancellationToken);
    }

    public ValueTask<EntityEntry<TEntity>> InsertAsync(TEntity entity, CancellationToken cancellationToken = default)
    {
        return DbSet.AddAsync(entity, cancellationToken);
    }

    public Task InsertRangeAsync(IEnumerable<TEntity> entities, CancellationToken cancellationToken = default)
    {
        return DbSet.AddRangeAsync(entities, cancellationToken);
    }

    public Task<int> DeleteAsync(int entityKey, CancellationToken cancellationToken = default)
    {
        return DbSet.Where(e => e.Id == entityKey)
                    .ExecuteDeleteAsync(cancellationToken);
    }

    public Task UpdateAsync(TEntity entity, CancellationToken cancellationToken = default)
    {
        DbSet.Update(entity);
        return Task.CompletedTask;
    }

    public Task DeleteAsync(TEntity entity, CancellationToken cancellationToken = default)
    {
        DbSet.Remove(entity);
        return Task.CompletedTask;
    }
}