using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IAdvertRepository : IRepository<Advert, int>
{
    
}

public class AdvertRepository : RepositoryImpl<Advert>, IAdvertRepository
{
    public AdvertRepository(ApplicationDbContext context)
    {
        DbSet = context.Adverts;
    }
    protected override sealed DbSet<Advert> DbSet { get; set; }
}