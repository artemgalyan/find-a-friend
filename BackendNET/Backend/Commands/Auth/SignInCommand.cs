using MediatR;

namespace Backend.Commands.Auth;

public class SignInCommand : IRequest<SignInResponse?>
{
    public string Login { get; set; }
    public string Password { get; set; }
}