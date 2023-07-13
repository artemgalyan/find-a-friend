using Backend.Dto;
using MediatR;

namespace Backend.Queries.Adverts.GetById;

public class GetAdvertByIdQuery : IRequest<AdvertDto?>
{
    public int AdvertId { get; set; }
}