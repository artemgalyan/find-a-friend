using Backend.Dto;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetByShelterId;

public class GetAnimalAdvertsByShelterIdQuery : IRequest<IEnumerable<AnimalAdvertDto>>
{
    public int ShelterId { get; set; }
}