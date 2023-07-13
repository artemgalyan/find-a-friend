using MediatR;

namespace Backend.Commands.UserShelter.RemoveUserFromShelter;

public class RemoveUserFromShelterCommand : IRequest<Unit>
{
    public int UserId { get; set; }
}