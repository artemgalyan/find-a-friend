namespace Backend.Entities;

public class Advert : Entity
{
    public enum AdvertType
    {
        Sitter = 0,
        Volunteer
    }
    
    public string Title { get; set; } = "";
    public string Description { get; set; } = "";
    public DateTime CreationDate { get; set; }
    public int PlaceId { get; set; }
    public Place Place { get; set; } = null!;
    public int OwnerId { get; set; }
    public User Owner { get; set; } = null!;
    public AdvertType Type { get; set; }
}