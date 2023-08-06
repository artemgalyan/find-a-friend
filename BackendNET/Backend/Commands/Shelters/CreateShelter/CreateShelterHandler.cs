using Backend.Entities;
using Backend.Repository;
using MediatR;

namespace Backend.Commands.Shelters.CreateShelter;

public class CreateShelterHandler : IRequestHandler<CreateShelterCommand, bool>
{
    private readonly IShelterRepository _shelterRepository;

    public CreateShelterHandler(IShelterRepository shelterRepository)
    {
        _shelterRepository = shelterRepository;
    }

    public async Task<bool> Handle(CreateShelterCommand request, CancellationToken cancellationToken)
    {
        var instance = new Shelter {
            Name = request.Name,
            Address = request.Address,
            Administrators = new List<User>(),
            PlaceId = request.PlaceId,
            Website = request.Website
        };
        await _shelterRepository.InsertAsync(instance, cancellationToken);
        return true;
    }
}