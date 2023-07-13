using Backend.Entities;
using MediatR;

namespace Backend.Commands.Users.SetUserRole;

public class SetUserRoleCommand : IRequest<Unit>
{
    public int UserId { get; set; }
    public User.UserRole NewRole { get; set; }
}