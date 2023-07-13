using Backend.Dto;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetByUserId;

public class GetAnimalAdvertsByUserIdQuery : IRequest<IEnumerable<AnimalAdvertDto>>
{
    public int UserId { get; set; }
}