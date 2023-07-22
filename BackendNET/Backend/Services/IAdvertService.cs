using Backend.Repository;

namespace Backend.Services;

public interface IAdvertService
{
    public Task<bool> IsOwnerOfAdvertAsync(int userId, int advertId, CancellationToken cancellationToken = default);
}

public class AdvertService : IAdvertService
{
    private readonly IAdvertRepository _advertRepository;

    public AdvertService(IAdvertRepository advertRepository)
    {
        _advertRepository = advertRepository;
    }

    public async Task<bool> IsOwnerOfAdvertAsync(int userId, int advertId, CancellationToken cancellationToken = default)
    {
        var advert = await _advertRepository.GetByIdAsync(advertId, cancellationToken);
        if (advert is null)
        {
            return false;
        }

        return advert.OwnerId == userId;
    }
}