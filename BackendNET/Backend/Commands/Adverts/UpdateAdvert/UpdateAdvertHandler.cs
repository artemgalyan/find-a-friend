using Backend.Repository;
using MediatR;

namespace Backend.Commands.Adverts.UpdateAdvert;

public class UpdateAdvertHandler : IRequestHandler<UpdateAdvertCommand, bool>
{
    private readonly IAdvertRepository _repository;

    public UpdateAdvertHandler(IAdvertRepository repository)
    {
        _repository = repository;
    }

    public async Task<bool> Handle(UpdateAdvertCommand request, CancellationToken cancellationToken)
    {
        var instance = await _repository.GetByIdAsync(request.AdvertId, cancellationToken);
        if (instance is null)
        {
            return false;
        }

        instance.Type = request.AdvertType ?? instance.Type;
        instance.PlaceId = request.PlaceId ?? instance.PlaceId;
        instance.Title = request.Title ?? instance.Title;
        instance.Description = request.Description ?? instance.Description;
        await _repository.UpdateAsync(instance, cancellationToken);
        return true;
    }
}