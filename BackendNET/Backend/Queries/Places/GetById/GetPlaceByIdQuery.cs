using Backend.Dto;
using MediatR;

namespace Backend.Queries.Places.GetById;

public class GetPlaceByIdQuery : IRequest<PlaceDto?>
{
    public int PlaceId { get; set; }
}