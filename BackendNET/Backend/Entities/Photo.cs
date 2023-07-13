namespace Backend.Entities;

public class Photo : Entity
{
    public string Base64Content { get; set; }
    public int AdvertId { get; set; }
    public AnimalAdvert AnimalAdvert { get; set; }
}