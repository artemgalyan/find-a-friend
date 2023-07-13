using Backend.Repository;
using MediatR;

namespace Backend.Commands.Shelters.DeleteShelter;

public class DeleteShelterHandler : IRequestHandler<DeleteShelterCommand, bool>
{
    private readonly IShelterRepository _shelterRepository;

    public DeleteShelterHandler(IShelterRepository shelterRepository)
    {
        _shelterRepository = shelterRepository;
    }

    public async Task<bool> Handle(DeleteShelterCommand request, CancellationToken cancellationToken)
    {
        return await _shelterRepository.DeleteAsync(request.ShelterId, cancellationToken) == 1;
    }
}