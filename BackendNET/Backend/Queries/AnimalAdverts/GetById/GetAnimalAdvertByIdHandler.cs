using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetById;

public class GetAnimalAdvertByIdHandler : IRequestHandler<GetAnimalAdvertByIdQuery, AnimalAdvertDto?>
{
    private readonly IMapper _mapper;
    private readonly IAnimalAdvertRepository _repository;
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IShelterRepository _shelterRepository;

    public GetAnimalAdvertByIdHandler(IMapper mapper, IAnimalAdvertRepository repository,
        IShelterRepository shelterRepository, IUserShelterRepository userShelterRepository)
    {
        _mapper = mapper;
        _repository = repository;
        _shelterRepository = shelterRepository;
        _userShelterRepository = userShelterRepository;
    }

    public async Task<AnimalAdvertDto?> Handle(GetAnimalAdvertByIdQuery request, CancellationToken cancellationToken)
    {
        var instance =
            _mapper.Map<AnimalAdvertDto>(await _repository.GetByIdAsync(request.AdvertId, cancellationToken));
        if (instance is null)
        {
            return null;
        }

        instance.ShelterId = await _userShelterRepository.GetShelterByUserId(instance.OwnerId, cancellationToken);
        if (instance.ShelterId is not null)
        {
            var shelter = await _shelterRepository.GetByIdAsync(instance.ShelterId.Value, cancellationToken);
            instance.ShelterName = shelter!.Name;
        }

        return instance;
    }
}