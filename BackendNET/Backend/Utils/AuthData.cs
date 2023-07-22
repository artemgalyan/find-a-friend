using Backend.Constants;
using Backend.Entities;

namespace Backend.Utils;

public class AuthData
{
    private readonly ApiController _apiController;

    public AuthData(ApiController apiController)
    {
        _apiController = apiController;
    }

    public int UserId => GetClaim(Claims.Id, int.Parse);
    public User.UserRole UserRole => GetClaim(Claims.Role, Enum.Parse<User.UserRole>);

    public int? ShelterId => GetClaim<int?>(Claims.ShelterId, s => int.TryParse(s, out var result)
        ? result
        : null);

    private T GetClaim<T>(string claim, Func<string, T> parser)
    {
        return parser(ClaimValue(claim));
    }

    private string ClaimValue(string claim) =>
        _apiController.HttpContext.User.Claims.FirstOrDefault(c => c.Type == claim)!.Value;
}