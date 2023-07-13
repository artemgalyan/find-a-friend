using MediatR;

namespace Backend.Commands.Shelters.UpdateShelter;

public class UpdateShelterCommand : IRequest<bool>
{
    public int ShelterId { get; set; }
    public string? Name { get; set; }
    public string? Address { get; set; }
    public string? Website { get; set; }
    public int? PlaceId { get; set; }
}