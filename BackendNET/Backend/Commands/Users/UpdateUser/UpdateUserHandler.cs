using Backend.Entities;
using Backend.Repository;
using MediatR;
using Microsoft.AspNetCore.Identity;

namespace Backend.Commands.Users.UpdateUser;

public class UpdateUserHandler : IRequestHandler<UpdateUserCommand, bool>
{
    private readonly IUserRepository _userRepository;
    private readonly ITokenRepository _tokenRepository;
    private readonly IPasswordHasher<User> _passwordHasher;

    public UpdateUserHandler(IUserRepository userRepository, ITokenRepository tokenRepository, IPasswordHasher<User> passwordHasher)
    {
        _userRepository = userRepository;
        _tokenRepository = tokenRepository;
        _passwordHasher = passwordHasher;
    }

    public async Task<bool> Handle(UpdateUserCommand request, CancellationToken cancellationToken)
    {
        var instance = await _userRepository.GetByIdAsync(request.UserId, cancellationToken);
        if (instance is null)
        {
            return false;
        }

        bool invalidated = request.Login is not null || request.Password is not null;
        instance.Name = request.Name ?? instance.Name;
        instance.Surname = request.Surname ?? instance.Surname;
        instance.Email = request.Email ?? instance.Email;
        instance.PhoneNumber = request.PhoneNumber ?? instance.PhoneNumber;
        // instance.Login = request.Login ?? instance.Login;
        if (request.Password is not null)
        {
            var hashed = _passwordHasher.HashPassword(instance, request.Password);
            instance.Password = hashed;
        }
        await _userRepository.UpdateAsync(instance, cancellationToken);
        return invalidated;
    }
}