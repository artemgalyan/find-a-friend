using Backend.Repository;
using MediatR;

namespace Backend.Commands.Places.UpdatePlace;

public class UpdatePlaceHandler : IRequestHandler<UpdatePlaceCommand, bool>
{
    private readonly IPlaceRepository _placeRepository;

    public UpdatePlaceHandler(IPlaceRepository placeRepository)
    {
        _placeRepository = placeRepository;
    }

    public async Task<bool> Handle(UpdatePlaceCommand request, CancellationToken cancellationToken)
    {
        var place = await _placeRepository.GetByIdAsync(request.PlaceId, cancellationToken);
        if (place is null)
        {
            return false;
        }

        place.Country = request.Country ?? place.Country;
        place.Region = request.Region ?? place.Region;
        place.City = request.City ?? place.City;
        place.District = request.District ?? place.District;
        await _placeRepository.UpdateAsync(place, cancellationToken);
        return true;
    }
}