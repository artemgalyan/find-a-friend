namespace Backend.Entities;

public class Place : Entity
{
    public string Country { get; set; }
    public string Region { get; set; }
    public string City { get; set; }
    public string District { get; set; }
}