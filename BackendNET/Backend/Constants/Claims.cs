using System.Security.Claims;

namespace Backend.Constants;

public static class Claims
{
    public static readonly string Id = "id";
    public static readonly string Role = ClaimTypes.Role;
    public static readonly string ShelterId = "shelter_id";
}