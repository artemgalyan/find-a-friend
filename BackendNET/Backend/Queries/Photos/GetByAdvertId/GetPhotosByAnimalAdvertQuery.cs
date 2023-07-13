using Backend.Dto;
using MediatR;

namespace Backend.Queries.Photos.GetByAdvertId;

public class GetPhotosByAnimalAdvertQuery : IRequest<IEnumerable<PhotoDto>>
{
    public int AdvertId { get; set; }
}