using Backend.Repository;
using MediatR;

namespace Backend.Commands.Shelters.UpdateShelter;

public class UpdateShelterHandler : IRequestHandler<UpdateShelterCommand, bool>
{
    private readonly IShelterRepository _shelterRepository;

    public UpdateShelterHandler(IShelterRepository shelterRepository)
    {
        _shelterRepository = shelterRepository;
    }

    public async Task<bool> Handle(UpdateShelterCommand request, CancellationToken cancellationToken)
    {
        var instance = await _shelterRepository.GetByIdAsync(request.ShelterId, cancellationToken);
        if (instance is null)
        {
            return false;
        }

        instance.Name = request.Name ?? instance.Name;
        instance.Address = request.Address ?? instance.Address;
        instance.Website = request.Website ?? instance.Website;
        instance.PlaceId = request.PlaceId ?? instance.PlaceId;
        return true;
    }
}