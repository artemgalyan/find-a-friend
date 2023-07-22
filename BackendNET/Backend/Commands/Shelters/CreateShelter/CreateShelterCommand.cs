using MediatR;

namespace Backend.Commands.Shelters.CreateShelter;

public class CreateShelterCommand : IRequest<bool>
{
    public string Name { get; set; } = "";
    public string Address { get; set; } = "";
    public string Website { get; set; } = "";
    public int PlaceId { get; set; }
}