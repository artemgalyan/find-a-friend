namespace Backend.Entities;

public class Shelter : Entity
{
    public string Name { get; set; } = "";
    public List<User> Administrators { get; set; } = null!;
    public int PlaceId { get; set; }
    public Place Place { get; set; } = null!;
    public string Address { get; set; } = "";
    public string Website { get; set; } = "";
}