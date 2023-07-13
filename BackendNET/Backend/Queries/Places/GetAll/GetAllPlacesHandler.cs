using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.Places.GetAll;

public class GetAllPlacesHandler : IRequestHandler<GetAllPlacesQuery, IEnumerable<PlaceDto>>
{
    private readonly IMapper _mapper;
    private readonly IPlaceRepository _placeRepository;

    public GetAllPlacesHandler(IMapper mapper, IPlaceRepository placeRepository)
    {
        _mapper = mapper;
        _placeRepository = placeRepository;
    }

    public async Task<IEnumerable<PlaceDto>> Handle(GetAllPlacesQuery request, CancellationToken cancellationToken)
    {
        var all = await _placeRepository.GetAllAsync(cancellationToken);
        return all.Map<Place, PlaceDto>(_mapper);
    }
}