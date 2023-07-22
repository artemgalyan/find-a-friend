using Backend.Repository;

namespace Backend.Services;

public interface IUserService
{
    public Task<bool> IsDuplicateLoginAsync(string login, CancellationToken cancellationToken = default);
}

public class UserService : IUserService
{
    private readonly IUserRepository _userRepository;

    public UserService(IUserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<bool> IsDuplicateLoginAsync(string login, CancellationToken cancellationToken = default)
    {
        return await _userRepository.GetByLoginAsync(login, cancellationToken) is not null;
    }
}