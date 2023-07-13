using Backend.Database;
using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Repository;

public interface IUserRepository : IRepository<User, int>
{
    public Task<User?> GetByLoginAsync(string login, CancellationToken cancellationToken = default);
}

public class UserRepository : RepositoryImpl<User>, IUserRepository
{
    public UserRepository(ApplicationDbContext context)
    {
        DbSet = context.Users;
    }
    protected override sealed DbSet<User> DbSet { get; set; }
    
    public Task<User?> GetByLoginAsync(string login, CancellationToken cancellationToken = default)
    {
        return DbSet.Where(u => u.Login == login)
                    .FirstOrDefaultAsync(cancellationToken);
    }
}