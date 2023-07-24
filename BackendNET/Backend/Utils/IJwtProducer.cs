using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using Microsoft.IdentityModel.Tokens;

namespace Backend.Utils;

public interface IJwtProducer
{
    public string MakeToken(IEnumerable<Claim> claims);
}

public class JwtProducer : IJwtProducer
{
    private readonly TimeSpan _tokenExpirationTime;
    private readonly SecurityKey _securityKey;
    private readonly JwtSecurityTokenHandler _securityTokenHandler = new();
    private readonly string _issuer;

    public JwtProducer(TimeSpan tokenExpirationTime, RSA rsaKey, string issuer)
    {
        _tokenExpirationTime = tokenExpirationTime;
        _securityKey = new RsaSecurityKey(rsaKey);
        _issuer = issuer;
    }

    public string MakeToken(IEnumerable<Claim> claims)
    {
        var jwt = new JwtSecurityToken(
            claims: claims,
            issuer: _issuer,
            expires: DateTime.Now + _tokenExpirationTime,
            signingCredentials: new SigningCredentials(_securityKey, SecurityAlgorithms.RsaSha256)
        );
        return _securityTokenHandler.WriteToken(jwt);
    }
}