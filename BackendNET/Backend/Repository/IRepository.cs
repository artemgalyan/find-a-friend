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