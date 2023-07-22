using System.Text.Json.Serialization;
using Backend.Entities;
using MediatR;

namespace Backend.Commands.AnimalAdverts.CreateAnimalAdvert;

public class CreateAnimalAdvertCommand : IRequest<bool>
{
    [JsonIgnore] public int UserId { get; set; }
    public string Title { get; set; } = "";
    public string Description { get; set; } = "";
    public string AnimalType { get; set; } = "";
    public int PlaceId { get; set; }
    public DateOnly BirthDate { get; set; }
    public AnimalAdvert.AnimalSex Sex { get; set; }
    public bool IsCastrated { get; set; }
    public List<string> Photos { get; set; } = null!; // base64
}