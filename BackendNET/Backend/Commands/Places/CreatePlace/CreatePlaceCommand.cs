using MediatR;

namespace Backend.Commands.Places.CreatePlace;

public class CreatePlaceCommand : IRequest<bool>
{
    public string Country { get; set; }
    public string Region { get; set; }
    public string City { get; set; }
    public string District { get; set; }
}