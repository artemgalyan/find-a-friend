using Backend.Repository;
using MediatR;

namespace Backend.Commands.Adverts.DeleteAdvert;

public class DeleteAdvertHandler : IRequestHandler<DeleteAdvertCommand, bool>
{
    private readonly IAdvertRepository _repository;

    public DeleteAdvertHandler(IAdvertRepository repository)
    {
        _repository = repository;
    }

    public async Task<bool> Handle(DeleteAdvertCommand request, CancellationToken cancellationToken)
    {
        return await _repository.DeleteAsync(request.AdvertId, cancellationToken) == 1;
    }
}