using System.Security.Claims;
using Backend.Constants;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Identity;

namespace Backend.Commands.Auth;

public class SignInHandler : IRequestHandler<SignInCommand, SignInResponse?>
{
    private readonly IUserRepository _userRepository;
    private readonly ITokenRepository _tokenRepository;
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IPasswordHasher<User> _passwordHasher;
    private readonly IJwtProducer _jwtProducer;

    public SignInHandler(IUserRepository userRepository, ITokenRepository tokenRepository,
        IUserShelterRepository userShelterRepository, IPasswordHasher<User> passwordHasher, IJwtProducer jwtProducer)
    {
        _userRepository = userRepository;
        _tokenRepository = tokenRepository;
        _userShelterRepository = userShelterRepository;
        _passwordHasher = passwordHasher;
        _jwtProducer = jwtProducer;
    }

    public async Task<SignInResponse?> Handle(SignInCommand request, CancellationToken cancellationToken)
    {
        var user = await _userRepository.GetByLoginAsync(request.Login, cancellationToken);
        if (user is null)
        {
            return null;
        }
        if (_passwordHasher.VerifyHashedPassword(user, user.Password, request.Password) ==
            PasswordVerificationResult.Failed)
        {
            return null;
        }

        int shelterId = await _userShelterRepository.GetShelterByUserIdAsync(user.Id, cancellationToken) ?? -1;
        var claims = new List<Claim> { new(Claims.Role, user.Role.ToString()), new(Claims.Id, user.Id.ToString()) };
        if (shelterId != -1)
        {
            claims.Add(new Claim(Claims.ShelterId, shelterId.ToString()));
        }
        var token = _jwtProducer.MakeToken(claims);
        await _tokenRepository.AddTokenAsync(new AuthToken { Token = token, UserId = user.Id }, cancellationToken);
        return new SignInResponse {
            UserId = user.Id,
            Role = user.Role.ToString(),
            ShelterId = shelterId,
            Token = token
        };
    }
}