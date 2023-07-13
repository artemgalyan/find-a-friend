using Backend.Repository;
using MediatR;

namespace Backend.Commands.Places.DeletePlace;

public class DeletePlaceHandler : IRequestHandler<DeletePlaceCommand, bool>
{
    private readonly IPlaceRepository _placeRepository;

    public DeletePlaceHandler(IPlaceRepository placeRepository)
    {
        _placeRepository = placeRepository;
    }

    public async Task<bool> Handle(DeletePlaceCommand request, CancellationToken cancellationToken)
    {
        return await _placeRepository.DeleteAsync(request.PlaceId, cancellationToken) == 1;
    }
}