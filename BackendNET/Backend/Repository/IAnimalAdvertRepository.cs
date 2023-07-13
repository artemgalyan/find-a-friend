using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IAnimalAdvertRepository : IRepository<AnimalAdvert, int>
{
    public Task<List<AnimalAdvert>> GetByUserIdAsync(int userId, CancellationToken cancellationToken = default);
}

public class AnimalAdvertRepository : RepositoryImpl<AnimalAdvert>, IAnimalAdvertRepository
{
    public AnimalAdvertRepository(ApplicationDbContext context)
    {
        DbSet = context.AnimalAdverts;
    }
    protected override sealed DbSet<AnimalAdvert> DbSet { get; set; }
    
    public Task<List<AnimalAdvert>> GetByUserIdAsync(int userId, CancellationToken cancellationToken = default)
    {
        return DbSet.Where(e => e.OwnerId == userId)
                    .ToListAsync(cancellationToken);
    }
}