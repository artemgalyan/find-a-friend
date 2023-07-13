using MediatR;

namespace Backend.Queries.Users.GetIdAndLogin;

public class GetIdAndLoginQuery : IRequest<GetIdAndLoginResponse?>
{
    public int UserId { get; set; }
}