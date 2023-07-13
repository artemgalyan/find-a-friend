using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IPlaceRepository : IRepository<Place, int>
{
    
}

public class PlaceRepository : RepositoryImpl<Place>, IPlaceRepository
{
    public PlaceRepository(ApplicationDbContext context)
    {
        DbSet = context.Places;
    }
    protected override sealed DbSet<Place> DbSet { get; set; }
}