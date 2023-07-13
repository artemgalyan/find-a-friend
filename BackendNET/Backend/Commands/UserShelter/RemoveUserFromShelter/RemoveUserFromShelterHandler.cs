using Backend.Repository;
using MediatR;

namespace Backend.Commands.UserShelter.RemoveUserFromShelter;

public class RemoveUserFromShelterHandler : IRequestHandler<RemoveUserFromShelterCommand, Unit>
{
    private readonly IUserShelterRepository _userShelterRepository;

    public RemoveUserFromShelterHandler(IUserShelterRepository userShelterRepository)
    {
        _userShelterRepository = userShelterRepository;
    }

    public async Task<Unit> Handle(RemoveUserFromShelterCommand request, CancellationToken cancellationToken)
    {
        await _userShelterRepository.RemoveByUserIdAsync(request.UserId, cancellationToken);
        return default;
    }
}