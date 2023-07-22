using Backend.Utils;

namespace UnitTests;

public class PhoneNumberVerifierTests
{
    [Theory]
    [InlineData("+375291111111")]
    [InlineData("375291111111")]
    [InlineData("375251111111")]
    [InlineData("375331111111")]
    [InlineData("375441111111")]
    public void ValidPhoneNumber_ReturnsTrue(string phoneNumber)
    {
        Assert.True(PhoneNumberVerifier.IsValidPhoneNumber(phoneNumber));
    }
    
    [Theory]
    [InlineData("+37529111111")]
    [InlineData("375351111111")]
    [InlineData("37525111111111111111")]
    [InlineData("475331111111")]
    [InlineData("-375441111111")]
    public void InvalidPhoneNumber_ReturnsFalse(string phoneNumber)
    {
        Assert.False(PhoneNumberVerifier.IsValidPhoneNumber(phoneNumber));
    }
}