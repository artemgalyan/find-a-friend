using Backend.Repository;
using MediatR;

namespace Backend.Queries.Users.GetIdAndLogin;

public class GetIdAndLoginHandler : IRequestHandler<GetIdAndLoginQuery, GetIdAndLoginResponse?>
{
    private readonly IUserRepository _userRepository;

    public GetIdAndLoginHandler(IUserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<GetIdAndLoginResponse?> Handle(GetIdAndLoginQuery request, CancellationToken cancellationToken)
    {
        var user = await _userRepository.GetByIdAsync(request.UserId, cancellationToken);
        return user is null
            ? null
            : new GetIdAndLoginResponse { UserId = user.Id, Login = user.Login };
    }
}