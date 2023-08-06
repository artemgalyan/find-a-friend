using Backend.Entities;
using Backend.Repository;

namespace Backend.Services;

public interface IAnimalAdvertService
{
    public Task<bool> CanDeleteAdvertAsync(int userId, User.UserRole role, int animalAdvertId, int? userShelterId,
        CancellationToken cancellationToken = default);

    public Task<bool> ExistsAsync(int advertId, CancellationToken cancellationToken = default);
}

public class AnimalAdvertService : IAnimalAdvertService
{
    private readonly IAnimalAdvertRepository _animalAdvertRepository;
    private readonly IUserShelterRepository _userShelterRepository;

    public AnimalAdvertService(IAnimalAdvertRepository animalAdvertRepository,
        IUserShelterRepository userShelterRepository)
    {
        _animalAdvertRepository = animalAdvertRepository;
        _userShelterRepository = userShelterRepository;
    }

    public async Task<bool> CanDeleteAdvertAsync(int userId, User.UserRole role, int animalAdvertId, int? userShelterId,
        CancellationToken cancellationToken = default)
    {
        switch (role)
        {
            case User.UserRole.ShelterAdministrator when userShelterId is null:
                throw new ArgumentException("User shelter id must be specified if user is shelter administrator");
            case User.UserRole.Administrator or User.UserRole.Moderator:
                return true;
        }

        var advert = await _animalAdvertRepository.GetByIdAsync(animalAdvertId, cancellationToken);
        if (advert is null)
        {
            return false;
        }

        if (role is User.UserRole.User)
        {
            return advert.OwnerId == userId;
        }

        // role is ShelterAdministrator
        return await _userShelterRepository.GetShelterByUserIdAsync(advert.OwnerId, cancellationToken) ==
               userShelterId!.Value;
    }

    public async Task<bool> ExistsAsync(int advertId, CancellationToken cancellationToken = default)
    {
        return await _animalAdvertRepository.GetByIdAsync(advertId, cancellationToken) is not null;
    }
}