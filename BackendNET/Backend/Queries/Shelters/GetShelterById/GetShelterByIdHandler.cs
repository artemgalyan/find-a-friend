using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Shelters.GetShelterById;

public class GetShelterByIdHandler : IRequestHandler<GetShelterByIdQuery, ShelterDto?>
{
    private readonly IMapper _mapper;
    private readonly IShelterRepository _shelterRepository;

    public GetShelterByIdHandler(IMapper mapper, IShelterRepository shelterRepository)
    {
        _mapper = mapper;
        _shelterRepository = shelterRepository;
    }

    public async Task<ShelterDto?> Handle(GetShelterByIdQuery request, CancellationToken cancellationToken)
    {
        return _mapper.Map<ShelterDto>(
            await _shelterRepository.GetByIdAsync(request.ShelterId, cancellationToken)
        );
    }
}