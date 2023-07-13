using MediatR;

namespace Backend.Commands.Places.DeletePlace;

public class DeletePlaceCommand : IRequest<bool>
{
    public int PlaceId { get; set; }
}