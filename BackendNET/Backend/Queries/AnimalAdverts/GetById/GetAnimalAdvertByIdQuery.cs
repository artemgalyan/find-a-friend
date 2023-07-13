using Backend.Dto;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetById;

public class GetAnimalAdvertByIdQuery : IRequest<AnimalAdvertDto?>
{
    public int AdvertId { get; set; }
}