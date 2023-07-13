using MediatR;

namespace Backend.Commands.UserShelter.AddUserToShelter;

public class AddUserToShelterCommand : IRequest<Unit>
{
    public int UserId { get; set; }
    public int ShelterId { get; set; }
}