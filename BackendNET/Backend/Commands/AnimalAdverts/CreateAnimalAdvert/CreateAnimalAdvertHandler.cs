using Backend.Entities;
using Backend.Repository;
using MediatR;

namespace Backend.Commands.AnimalAdverts.CreateAnimalAdvert;

public class CreateAnimalAdvertHandler : IRequestHandler<CreateAnimalAdvertCommand, bool>
{
    private readonly IAnimalAdvertRepository _advertRepository;
    private readonly IPhotoRepository _photoRepository;

    public CreateAnimalAdvertHandler(IPhotoRepository photoRepository, IAnimalAdvertRepository advertRepository)
    {
        _photoRepository = photoRepository;
        _advertRepository = advertRepository;
    }

    public async Task<bool> Handle(CreateAnimalAdvertCommand request, CancellationToken cancellationToken)
    {
        var instance = new AnimalAdvert {
            Title = request.Title,
            Description = request.Description,
            AnimalType = request.AnimalType,
            BirthDate = request.BirthDate,
            CreationDate = DateTime.Now,
            IsCastrated = request.IsCastrated,
            OwnerId = request.UserId,
            PlaceId = request.PlaceId,
            Photos = request.Photos.Select(x => new Photo { Base64Content = x }).ToList()
        };

        await _advertRepository.InsertAsync(instance, cancellationToken);
        return true;
    }
}