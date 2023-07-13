using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IPhotoRepository : IRepository<Photo, int>
{
    public Task<List<Photo>> GetByAdvertIdAsync(int advertId, CancellationToken cancellationToken = default);
    public Task<Photo?> GetPreviewAsync(int advertId, CancellationToken cancellationToken = default);
}

public class PhotoRepository : RepositoryImpl<Photo>, IPhotoRepository
{
    public PhotoRepository(ApplicationDbContext context)
    {
        DbSet = context.Photos;
    }
    protected override sealed DbSet<Photo> DbSet { get; set; }
    
    public Task<List<Photo>> GetByAdvertIdAsync(int advertId, CancellationToken cancellationToken = default)
    {
        return DbSet.Where(a => a.AdvertId == advertId)
                    .ToListAsync(cancellationToken);
    }

    public Task<Photo?> GetPreviewAsync(int advertId, CancellationToken cancellationToken = default)
    {
        return DbSet.Where(p => p.AdvertId == advertId)
                    .FirstOrDefaultAsync(cancellationToken);
    }
}