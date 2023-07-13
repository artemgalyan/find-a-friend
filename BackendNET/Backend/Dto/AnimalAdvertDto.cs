using Backend.Entities;

namespace Backend.Dto;

public class AnimalAdvertDto
{
    public int Id { get; set; }
    public int OwnerId { get; set; }
    public string Title { get; set; }
    public string Description { get; set; }
    public string AnimalType { get; set; }
    public DateTime CreationDate { get; set; }
    public DateOnly BirthDate { get; set; }
    public int PlaceId { get; set; }
    public AnimalAdvert.AnimalSex Sex { get; set; }
    public bool IsCastrated { get; set; }
    public int? ShelterId { get; set; }
    public string? ShelterName { get; set; }
}