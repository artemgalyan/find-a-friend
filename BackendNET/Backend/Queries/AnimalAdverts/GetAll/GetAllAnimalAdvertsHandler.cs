using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetAll;

public class GetAllAnimalAdvertsHandler : IRequestHandler<GetAllAnimalAdvertsQuery, IEnumerable<AnimalAdvertDto>>
{
    private readonly IMapper _mapper;
    private readonly IAnimalAdvertRepository _repository;
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IShelterRepository _shelterRepository;

    public GetAllAnimalAdvertsHandler(IMapper mapper, IAnimalAdvertRepository repository, IUserShelterRepository userShelterRepository, IShelterRepository shelterRepository)
    {
        _mapper = mapper;
        _repository = repository;
        _userShelterRepository = userShelterRepository;
        _shelterRepository = shelterRepository;
    }

    public async Task<IEnumerable<AnimalAdvertDto>> Handle(GetAllAnimalAdvertsQuery request, CancellationToken cancellationToken)
    {
        var all = await _repository.GetAllAsync(cancellationToken);
        var userShelter = await _userShelterRepository.GetAllAsync(cancellationToken);
        var shelters = await _shelterRepository.GetAllAsync(cancellationToken);
        var shelterNames = new Dictionary<int, string>();
        shelters.ForEach(s => shelterNames.Add(s.Id, s.Name));
        var userToShelter = new Dictionary<int, int>();
        userShelter.ForEach(s => userToShelter.Add(s.UserId, s.ShelterId));
        return all.Map<AnimalAdvert, AnimalAdvertDto>(_mapper)
                  .Then(x =>
                  {
                      if (!userToShelter.ContainsKey(x.OwnerId))
                      {
                          return;
                      }
                      x.ShelterId = userToShelter[x.OwnerId];
                      x.ShelterName = shelterNames[x.ShelterId.Value];
                  });
    }
}