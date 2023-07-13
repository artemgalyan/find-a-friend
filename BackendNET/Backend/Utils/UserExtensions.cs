using System.Security.Claims;

namespace Backend.Utils;

public static class UserExtensions
{
    public static string GetClaim(this ClaimsPrincipal principal, string claimName)
    {
        return principal.Claims.First(c => c.Type == claimName).Value;
    }
}