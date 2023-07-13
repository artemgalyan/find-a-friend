using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Places.GetById;

public class GetPlaceByIdHandler : IRequestHandler<GetPlaceByIdQuery, PlaceDto?>
{
    private readonly IMapper _mapper;
    private readonly IPlaceRepository _placeRepository;

    public GetPlaceByIdHandler(IMapper mapper, IPlaceRepository placeRepository)
    {
        _mapper = mapper;
        _placeRepository = placeRepository;
    }

    public async Task<PlaceDto?> Handle(GetPlaceByIdQuery request, CancellationToken cancellationToken)
    {
        return _mapper.Map<PlaceDto>(
            await _placeRepository.GetByIdAsync(request.PlaceId, cancellationToken)
        );
    }
}