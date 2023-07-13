using System.Security.Cryptography;
using System.Text;
using Backend.Entities;
using Microsoft.AspNetCore.Identity;

namespace Backend;

public class PasswordHasher : IPasswordHasher<User>
{
    private const int OutputLength = 64;
    private const int SaltSize = 12;
    private static readonly int InStringSaltSize = (int) Math.Floor(SaltSize * 8m / 6m);
    private const int Iterations = 500;
    private static readonly HashAlgorithmName HashAlgorithm = HashAlgorithmName.SHA256;
    
    public string HashPassword(User user, string password)
    {
        var salt = new byte[SaltSize];
        Random.Shared.NextBytes(salt);
        var saltString = Convert.ToBase64String(salt);
        var hash = HashPassword(password, salt);
        return saltString + Convert.ToBase64String(hash);
    }

    public PasswordVerificationResult VerifyHashedPassword(User user, string hashedPassword, string providedPassword)
    {
        var saltString = hashedPassword[..InStringSaltSize];
        var actualHash = hashedPassword[InStringSaltSize..];
        var salt = Convert.FromBase64String(saltString);

        var providedHash = HashPassword(providedPassword, salt);
        return providedHash.SequenceEqual(Convert.FromBase64String(actualHash))
            ? PasswordVerificationResult.Success
            : PasswordVerificationResult.Failed;
    }

    private static byte[] HashPassword(string password, byte[] salt)
    {
        return Rfc2898DeriveBytes.Pbkdf2(
            password: Encoding.UTF8.GetBytes(password),
            salt: salt,
            iterations: Iterations,
            hashAlgorithm: HashAlgorithm,
            outputLength: OutputLength
        );
    }
}