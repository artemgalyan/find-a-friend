using Backend.Dto;
using MediatR;

namespace Backend.Queries.AnimalAdverts.GetAll;

public class GetAllAnimalAdvertsQuery : IRequest<IEnumerable<AnimalAdvertDto>>
{
    
}