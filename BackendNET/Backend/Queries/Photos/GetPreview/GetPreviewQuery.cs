using Backend.Dto;
using MediatR;

namespace Backend.Queries.Photos.GetPreview;

public class GetPreviewQuery : IRequest<PhotoDto?>
{
    public int AdvertId { get; set; }
}