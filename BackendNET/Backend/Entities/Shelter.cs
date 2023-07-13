namespace Backend.Entities;

public class Shelter : Entity
{
    public string Name { get; set; }
    public List<User> Administrators { get; set; }
    public int PlaceId { get; set; }
    public Place Place { get; set; }
    public string Address { get; set; }
    public string Website { get; set; }
}