using MediatR;

namespace Backend.Commands.Users.UpdateUser;

public class UpdateUserCommand : IRequest<bool>
{
    public int UserId { get; set; }
    public string? Name { get; set; }
    public string? Surname { get; set; }
    public string? Login { get; set; }
    public string? Password { get; set; }
    public string? Email { get; set; }
    public string? PhoneNumber { get; set; }
}