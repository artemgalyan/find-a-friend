namespace Backend.Entities;

public class AnimalAdvert : Entity
{
    public enum AnimalSex
    {
        Male = 0,
        Female
    }
    
    public string Title { get; set; }
    public string Description { get; set; }
    public string AnimalType { get; set; }
    public List<Photo> Photos { get; set; }
    public int OwnerId { get; set; }
    public User Owner { get; set; }
    public DateTime CreationDate { get; set; }
    public int PlaceId { get; set; }
    public Place Place { get; set; }
    public DateOnly BirthDate { get; set; }
    public AnimalSex Sex { get; set; }
    public bool IsCastrated { get; set; }
}