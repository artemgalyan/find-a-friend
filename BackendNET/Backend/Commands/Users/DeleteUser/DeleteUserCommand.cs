using MediatR;

namespace Backend.Commands.Users.DeleteUser;

public class DeleteUserCommand : IRequest<bool>
{
    public int UserId { get; set; }
}