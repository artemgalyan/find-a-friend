namespace Backend.Entities;

public class User : Entity
{
    public enum UserRole
    {
        User = 0,
        Moderator = 1,
        Administrator = 2,
        ShelterAdministrator = 3
    }

    public string Login { get; set; }
    public string Password { get; set; }
    public string Name { get; set; }
    public string Surname { get; set; }
    public string PhoneNumber { get; set; }
    public string Email { get; set; }
    public UserRole Role { get; set; }
    public List<Advert> Adverts { get; set; }
    public List<AnimalAdvert> AnimalAdverts { get; set; }
}