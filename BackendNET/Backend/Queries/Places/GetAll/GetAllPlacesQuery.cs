using Backend.Dto;
using MediatR;

namespace Backend.Queries.Places.GetAll;

public class GetAllPlacesQuery : IRequest<IEnumerable<PlaceDto>>
{
    
}