using Backend.Repository;
using MediatR;

namespace Backend.Commands.AnimalAdverts.DeleteAnimalAdvert;

public class DeleteAnimalAdvertHandler : IRequestHandler<DeleteAnimalAdvertCommand, bool>
{
    private readonly IAnimalAdvertRepository _repository;

    public DeleteAnimalAdvertHandler(IAnimalAdvertRepository repository)
    {
        _repository = repository;
    }

    public async Task<bool> Handle(DeleteAnimalAdvertCommand request, CancellationToken cancellationToken)
    {
        return await _repository.DeleteAsync(request.AdvertId, cancellationToken) == 1;
    }
}