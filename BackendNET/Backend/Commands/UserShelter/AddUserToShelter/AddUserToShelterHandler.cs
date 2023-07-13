using Backend.Entities;
using Backend.Repository;
using MediatR;

namespace Backend.Commands.UserShelter.AddUserToShelter;

public class AddUserToShelterHandler : IRequestHandler<AddUserToShelterCommand, Unit>
{
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IUserRepository _userRepository;

    public AddUserToShelterHandler(IUserShelterRepository userShelterRepository, IUserRepository userRepository)
    {
        _userShelterRepository = userShelterRepository;
        _userRepository = userRepository;
    }

    public async Task<Unit> Handle(AddUserToShelterCommand request, CancellationToken cancellationToken)
    {
        var user = await _userRepository.GetByIdAsync(request.UserId, cancellationToken);
        if (user is null)
        {
            return default;
        }

        if (user.Role == User.UserRole.User)
        {
            user.Role = User.UserRole.ShelterAdministrator;
            await _userRepository.UpdateAsync(user, cancellationToken);
        }

        await _userShelterRepository.RemoveByUserIdAsync(request.UserId, cancellationToken);
        var obj = new Entities.UserShelter { UserId = request.UserId, ShelterId = request.ShelterId };
        await _userShelterRepository.InsertAsync(obj, cancellationToken);
        return default;
    }
}