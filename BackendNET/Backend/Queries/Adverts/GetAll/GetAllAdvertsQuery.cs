using Backend.Dto;
using MediatR;

namespace Backend.Queries.Adverts.GetAll;

public class GetAllAdvertsQuery : IRequest<IEnumerable<AdvertDto>>
{
    
}