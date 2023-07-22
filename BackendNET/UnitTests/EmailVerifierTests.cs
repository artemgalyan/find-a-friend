using Backend.Utils;

namespace UnitTests;

public class EmailVerifierTests
{
    [Theory]
    [InlineData("email@email.com")]
    [InlineData("email@mail.com")]
    [InlineData("email123123@gmail.com")]
    public void ValidEmail_ReturnsTrue(string email)
    {
        Assert.True(EmailVerifier.IsValidEmail(email));
    }

    [Theory]
    [InlineData("")]
    [InlineData("email")]
    [InlineData("email@email")]
    public void InvalidEmail_ReturnsFalse(string email)
    {
        Assert.False(EmailVerifier.IsValidEmail(email));
    }
}