using Backend.Entities;
using Backend.Repository;
using MediatR;

namespace Backend.Commands.Places.CreatePlace;

public class CreatePlaceHandler : IRequestHandler<CreatePlaceCommand, bool>
{
    private readonly IPlaceRepository _placeRepository;

    public CreatePlaceHandler(IPlaceRepository placeRepository)
    {
        _placeRepository = placeRepository;
    }

    public async Task<bool> Handle(CreatePlaceCommand request, CancellationToken cancellationToken)
    {
        var place = new Place {
            Country = request.Country,
            Region = request.Region,
            City = request.City,
            District = request.District
        };
        await _placeRepository.InsertAsync(place, cancellationToken);
        return true;
    }
}