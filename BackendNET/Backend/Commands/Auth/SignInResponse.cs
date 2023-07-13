namespace Backend.Commands.Auth;

public class SignInResponse
{
    public string Token { get; set; }
    public int UserId { get; set; }
    public string Role { get; set; }
    public int ShelterId { get; set; }
}