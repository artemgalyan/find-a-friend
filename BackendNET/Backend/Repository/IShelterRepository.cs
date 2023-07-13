using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IShelterRepository : IRepository<Shelter, int>
{
    
}

public class ShelterRepository : RepositoryImpl<Shelter>, IShelterRepository
{
    public ShelterRepository(ApplicationDbContext context)
    {
        DbSet = context.Shelters;
    }
    protected override sealed DbSet<Shelter> DbSet { get; set; }
}