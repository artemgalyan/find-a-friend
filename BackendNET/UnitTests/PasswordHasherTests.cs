using Backend;
using Backend.Entities;
using Microsoft.AspNetCore.Identity;

namespace UnitTests;

public class PasswordHasherTests
{
    private readonly IPasswordHasher<User> _passwordHasher = new PasswordHasher();

    [Theory]
    [InlineData("")]
    [InlineData("password")]
    [InlineData("verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword-verylongpassword")]
    public void UserCanSignInWithRightPassword(string password)
    {
        var user = new User { Login = "login" };
        var hashedPassword = _passwordHasher.HashPassword(user, password);
        user.Password = hashedPassword;
        Assert.Equal(PasswordVerificationResult.Success,
            _passwordHasher.VerifyHashedPassword(user, user.Password, password));
    }

    [Fact]
    public void UserCantSignInWithAWrongPassword()
    {
        
        var user = new User { Login = "login" };
        var hashedPassword = _passwordHasher.HashPassword(user, "password");
        user.Password = hashedPassword;
        Assert.Equal(PasswordVerificationResult.Failed,
            _passwordHasher.VerifyHashedPassword(user, user.Password, "wrong-password"));
    }
}