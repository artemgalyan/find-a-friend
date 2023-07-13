namespace Backend.Repository;

public static class RepositoryConfiguration
{
    public static IServiceCollection AddRepositories(this IServiceCollection serviceCollection)
    {
        return serviceCollection.AddScoped<IAdvertRepository, AdvertRepository>()
                                .AddScoped<IAnimalAdvertRepository, AnimalAdvertRepository>()
                                .AddScoped<IPhotoRepository, PhotoRepository>()
                                .AddScoped<IPlaceRepository, PlaceRepository>()
                                .AddScoped<IShelterRepository, ShelterRepository>()
                                .AddScoped<IUserRepository, UserRepository>()
                                .AddScoped<IUserShelterRepository, UserShelterRepository>()
                                .AddScoped<ITokenRepository, TokenRepository>();
    }
}