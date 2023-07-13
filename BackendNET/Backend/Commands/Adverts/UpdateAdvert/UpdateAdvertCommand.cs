using Backend.Entities;
using MediatR;

namespace Backend.Commands.Adverts.UpdateAdvert;

public class UpdateAdvertCommand : IRequest<bool>
{
    public int AdvertId { get; set; }
    public Advert.AdvertType? AdvertType { get; set; }
    public string? Title { get; set; }
    public string? Description { get; set; }
    public int? PlaceId { get; set; }
}