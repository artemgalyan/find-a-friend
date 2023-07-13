using System.Data.Common;
using Backend.Entities;
using Backend.Repository;
using MediatR;

namespace Backend.Commands.Adverts.CreateAdvert;

public class CreateAdvertHandler : IRequestHandler<CreateAdvertCommand, bool>
{
    private readonly IAdvertRepository _repository;

    public CreateAdvertHandler(IAdvertRepository repository)
    {
        _repository = repository;
    }

    public async Task<bool> Handle(CreateAdvertCommand request, CancellationToken cancellationToken)
    {
        var instance = new Advert {
            CreationDate = DateTime.Now,
            Description = request.Description,
            OwnerId = request.UserId,
            PlaceId = request.PlaceId,
            Title = request.Title,
            Type = request.AdvertType
        };

        try
        {
            await _repository.InsertAsync(instance, cancellationToken);
            return true;
        }
        catch (DbException e)
        {
            return false;
        }
    }
}