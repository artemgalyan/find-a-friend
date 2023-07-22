using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using MediatR;
using Backend.Utils;

namespace Backend.Queries.AnimalAdverts.GetByUserId;

public class GetAnimalAdvertsByUserIdHandler : IRequestHandler<GetAnimalAdvertsByUserIdQuery, IEnumerable<AnimalAdvertDto>>
{
    private readonly IMapper _mapper;
    private readonly IAnimalAdvertRepository _advertRepository;
    private readonly IUserShelterRepository _userShelterRepository;
    private readonly IShelterRepository _shelterRepository;

    public GetAnimalAdvertsByUserIdHandler(IMapper mapper, IAnimalAdvertRepository advertRepository, IUserShelterRepository userShelterRepository, IShelterRepository shelterRepository)
    {
        _mapper = mapper;
        _advertRepository = advertRepository;
        _userShelterRepository = userShelterRepository;
        _shelterRepository = shelterRepository;
    }

    public async Task<IEnumerable<AnimalAdvertDto>> Handle(GetAnimalAdvertsByUserIdQuery request, CancellationToken cancellationToken)
    {
        int? shelterId = await _userShelterRepository.GetShelterByUserIdAsync(request.UserId, cancellationToken);
        string? shelterName = shelterId is null
            ? null
            : (await _shelterRepository.GetByIdAsync(shelterId.Value, cancellationToken))!.Name;

        var adverts = await _advertRepository.GetByUserIdAsync(request.UserId, cancellationToken);
        return adverts.Map<AnimalAdvert, AnimalAdvertDto>(_mapper)
                      .Then(x =>
                      {
                          x.ShelterId = shelterId;
                          x.ShelterName = shelterName;
                      });

    }
}