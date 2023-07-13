using System.Text.Json.Serialization;
using Backend.Entities;
using MediatR;

namespace Backend.Commands.Adverts.CreateAdvert;

public class CreateAdvertCommand : IRequest<bool>
{
    public Advert.AdvertType AdvertType { get; set; }
    public string Title { get; set; }
    public string Description { get; set; }
    public int PlaceId { get; set; }
    [JsonIgnore]
    public int UserId { get; set; }
}