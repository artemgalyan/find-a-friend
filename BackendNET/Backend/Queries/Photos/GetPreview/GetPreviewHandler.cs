using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Photos.GetPreview;

public class GetPreviewHandler : IRequestHandler<GetPreviewQuery, PhotoDto?>
{
    private readonly IMapper _mapper;
    private readonly IPhotoRepository _photoRepository;

    public GetPreviewHandler(IMapper mapper, IPhotoRepository photoRepository)
    {
        _mapper = mapper;
        _photoRepository = photoRepository;
    }

    public async Task<PhotoDto?> Handle(GetPreviewQuery request, CancellationToken cancellationToken)
    {
        return _mapper.Map<PhotoDto>(await _photoRepository.GetPreviewAsync(request.AdvertId, cancellationToken));
    }
}