using Backend.Entities;
using Backend.Repository;
using MediatR;
using Microsoft.AspNetCore.Identity;

namespace Backend.Commands.Users.CreateUser;

public class CreateUserHandler : IRequestHandler<CreateUserCommand, bool>
{
    private readonly IUserRepository _userRepository;
    private readonly IPasswordHasher<User> _passwordHasher;

    public CreateUserHandler(IUserRepository userRepository, IPasswordHasher<User> passwordHasher)
    {
        _userRepository = userRepository;
        _passwordHasher = passwordHasher;
    }

    public async Task<bool> Handle(CreateUserCommand request, CancellationToken cancellationToken)
    {
        var user = new User {
            Name = request.Name,
            Surname = request.Surname,
            Email = request.Email,
            PhoneNumber = request.PhoneNumber,
            Role = User.UserRole.User,
            Login = request.Login
        };

        var password = _passwordHasher.HashPassword(user, request.Password);
        user.Password = password;
        await _userRepository.InsertAsync(user, cancellationToken);
        return true;
    }
}