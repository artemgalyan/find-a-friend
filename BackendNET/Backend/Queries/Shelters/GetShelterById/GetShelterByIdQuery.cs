using Backend.Dto;
using MediatR;

namespace Backend.Queries.Shelters.GetShelterById;

public class GetShelterByIdQuery : IRequest<ShelterDto?>
{
    public int ShelterId { get; set; }
}