using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.Photos.GetByAdvertId;

public class GetPhotosByAnimalAdvertHandler : IRequestHandler<GetPhotosByAnimalAdvertQuery, IEnumerable<PhotoDto>>
{
    private readonly IPhotoRepository _photoRepository;
    private readonly IMapper _mapper;

    public GetPhotosByAnimalAdvertHandler(IPhotoRepository photoRepository, IMapper mapper)
    {
        _photoRepository = photoRepository;
        _mapper = mapper;
    }

    public async Task<IEnumerable<PhotoDto>> Handle(GetPhotosByAnimalAdvertQuery request, CancellationToken cancellationToken)
    {
        var all = await _photoRepository.GetByAdvertIdAsync(request.AdvertId, cancellationToken);
        return all.Map<Photo, PhotoDto>(_mapper);
    }
}