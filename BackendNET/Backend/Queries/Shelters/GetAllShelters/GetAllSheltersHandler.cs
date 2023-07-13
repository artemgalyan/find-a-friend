using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.Shelters.GetAllShelters;

public class GetAllSheltersHandler : IRequestHandler<GetAllSheltersQuery, IEnumerable<ShelterDto>>
{
    private readonly IMapper _mapper;
    private readonly IShelterRepository _shelterRepository;

    public GetAllSheltersHandler(IMapper mapper, IShelterRepository shelterRepository)
    {
        _mapper = mapper;
        _shelterRepository = shelterRepository;
    }

    public async Task<IEnumerable<ShelterDto>> Handle(GetAllSheltersQuery request, CancellationToken cancellationToken)
    {
        var all = await _shelterRepository.GetAllAsync(cancellationToken);
        return all.Map<Shelter, ShelterDto>(_mapper);
    }
}