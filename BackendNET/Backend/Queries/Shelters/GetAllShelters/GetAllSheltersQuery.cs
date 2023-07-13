using Backend.Dto;
using MediatR;

namespace Backend.Queries.Shelters.GetAllShelters;

public class GetAllSheltersQuery : IRequest<IEnumerable<ShelterDto>>
{
    
}