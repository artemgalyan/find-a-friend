using Backend.Repository;
using MediatR;

namespace Backend.Commands.Users.SetUserRole;

public class SetUserRoleHandler : IRequestHandler<SetUserRoleCommand, Unit>
{
    private readonly IUserRepository _userRepository;
    private readonly ITokenRepository _tokenRepository;

    public SetUserRoleHandler(IUserRepository userRepository, ITokenRepository tokenRepository)
    {
        _userRepository = userRepository;
        _tokenRepository = tokenRepository;
    }

    public async Task<Unit> Handle(SetUserRoleCommand request, CancellationToken cancellationToken)
    {
        var user = await _userRepository.GetByIdAsync(request.UserId, cancellationToken);
        if (user is null)
        {
            return default;
        }

        user.Role = request.NewRole;
        await _userRepository.UpdateAsync(user, cancellationToken);
        await _tokenRepository.InvalidateTokensAsync(user.Id, cancellationToken);
        return default;
    }
}