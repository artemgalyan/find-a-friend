using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetByShelterId;

public class
    GetAnimalAdvertsByShelterIdHandler : IRequestHandler<GetAnimalAdvertsByShelterIdQuery, IEnumerable<AnimalAdvertDto>>
{
    private readonly IMapper _mapper;
    private readonly IAnimalAdvertRepository _repository;
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IShelterRepository _shelterRepository;

    public GetAnimalAdvertsByShelterIdHandler(IMapper mapper, IAnimalAdvertRepository repository,
        IUserShelterRepository userShelterRepository, IShelterRepository shelterRepository)
    {
        _mapper = mapper;
        _repository = repository;
        _userShelterRepository = userShelterRepository;
        _shelterRepository = shelterRepository;
    }

    public async Task<IEnumerable<AnimalAdvertDto>> Handle(GetAnimalAdvertsByShelterIdQuery request,
        CancellationToken cancellationToken)
    {
        var shelter = await _shelterRepository.GetByIdAsync(request.ShelterId, cancellationToken);
        if (shelter is null)
        {
            return Enumerable.Empty<AnimalAdvertDto>();
        }

        var users = new HashSet<int>(
            await _userShelterRepository.GetUsersByShelterIdAsync(request.ShelterId, cancellationToken)
        );

        var adverts = await _repository.GetAllAsync(cancellationToken);
        return adverts
               .Where(u => users.Contains(u.OwnerId))
               .Map<AnimalAdvert, AnimalAdvertDto>(_mapper)
               .Then(a =>
               {
                   a.ShelterId = request.ShelterId;
                   a.ShelterName = shelter.Name;
               });
    }
}