namespace Backend.Dto;

public class AdvertDto
{
    public int Id { get; set; }
    public string Title { get; set; } = "";
    public string Description { get; set; } = "";
    public int OwnerId { get; set; }
    public string AdvertType { get; set; } = "";
    public DateTime CreationDate { get; set; }
    public int PlaceId { get; set; }
}