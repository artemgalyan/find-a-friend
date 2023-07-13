using MediatR;

namespace Backend.Commands.Places.UpdatePlace;

public class UpdatePlaceCommand : IRequest<bool>
{
    public int PlaceId { get; set; }
    public string? Country { get; set; }
    public string? Region { get; set; }
    public string? City { get; set; }
    public string? District { get; set; }
}