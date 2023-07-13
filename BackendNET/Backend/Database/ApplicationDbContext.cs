using Backend.Entities;
using Microsoft.EntityFrameworkCore;

namespace Backend.Database;

public class ApplicationDbContext : DbContext
{
    protected ApplicationDbContext()
    {
    }

    public ApplicationDbContext(DbContextOptions options) : base(options)
    {
    }
    
    protected override void ConfigureConventions(ModelConfigurationBuilder configurationBuilder)
    {
        configurationBuilder.Properties<DateOnly>()
                            .HaveConversion<DateOnlyConverter>()
                            .HaveColumnType("date");
        base.ConfigureConventions(configurationBuilder);
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Advert>()
                    .HasKey(a => a.Id);
        modelBuilder.Entity<Advert>()
                    .HasOne(a => a.Owner)
                    .WithMany(o => o.Adverts)
                    .HasForeignKey(a => a.OwnerId)
                    .OnDelete(DeleteBehavior.Cascade);
        modelBuilder.Entity<Advert>()
                    .HasOne(a => a.Place)
                    .WithMany()
                    .HasForeignKey(a => a.PlaceId)
                    .OnDelete(DeleteBehavior.Restrict);
        
        modelBuilder.Entity<AnimalAdvert>()
                    .HasKey(a => a.Id);
        modelBuilder.Entity<AnimalAdvert>()
                    .HasMany(a => a.Photos)
                    .WithOne(p => p.AnimalAdvert)
                    .HasForeignKey(p => p.AdvertId)
                    .OnDelete(DeleteBehavior.Cascade);
        modelBuilder.Entity<AnimalAdvert>()
                    .HasOne(a => a.Place)
                    .WithMany()
                    .HasForeignKey(a => a.PlaceId)
                    .OnDelete(DeleteBehavior.Restrict);
        modelBuilder.Entity<AnimalAdvert>()
                    .HasOne(a => a.Owner)
                    .WithMany(o => o.AnimalAdverts)
                    .HasForeignKey(a => a.OwnerId)
                    .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Photo>()
                    .HasKey(p => p.Id);

        modelBuilder.Entity<Place>()
                    .HasKey(p => p.Id);

        modelBuilder.Entity<Shelter>()
                    .HasKey(s => s.Id);

        modelBuilder.Entity<Shelter>()
                    .HasOne(s => s.Place)
                    .WithMany()
                    .HasForeignKey(s => s.PlaceId)
                    .OnDelete(DeleteBehavior.Restrict);
        modelBuilder.Entity<Shelter>()
                    .HasMany(s => s.Administrators)
                    .WithMany()
                    .UsingEntity<UserShelter>();

        modelBuilder.Entity<User>()
                    .HasKey(u => u.Id);
        modelBuilder.Entity<UserShelter>()
                    .HasKey(s => new { s.UserId, s.ShelterId });
        modelBuilder.Entity<AuthToken>()
                    .HasKey(a => a.Id);
        modelBuilder.Entity<AuthToken>()
                    .Property(a => a.Token)
                    .HasColumnType("VARCHAR(MAX)");
        base.OnModelCreating(modelBuilder);
    }

    public DbSet<Advert> Adverts { get; set; }
    public DbSet<AnimalAdvert> AnimalAdverts { get; set; }
    public DbSet<Photo> Photos { get; set; }
    public DbSet<Place> Places { get; set; }
    public DbSet<Shelter> Shelters { get; set; }
    public DbSet<User> Users { get; set; }
    public DbSet<UserShelter> UserShelters { get; set; }
    public DbSet<AuthToken> Tokens { get; set; }
}