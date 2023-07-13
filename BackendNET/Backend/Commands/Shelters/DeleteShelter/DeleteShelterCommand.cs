using MediatR;

namespace Backend.Commands.Shelters.DeleteShelter;

public class DeleteShelterCommand : IRequest<bool>
{
    public int ShelterId { get; set; }
}