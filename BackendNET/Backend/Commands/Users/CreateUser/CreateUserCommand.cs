using MediatR;

namespace Backend.Commands.Users.CreateUser;

public class CreateUserCommand : IRequest<bool>
{
    public string Name { get; set; } = "";
    public string Surname { get; set; } = "";
    public string Login { get; set; } = "";
    public string Password { get; set; } = "";
    public string Email { get; set; } = "";
    public string PhoneNumber { get; set; } = "";
}