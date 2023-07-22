using System.Text.RegularExpressions;

namespace Backend.Utils;

public static partial class PhoneNumberVerifier
{
    private static readonly Regex PhoneRegex = MyRegex(); 
    public static bool IsValidPhoneNumber(string phoneNumber)
    {
        return PhoneRegex.IsMatch(phoneNumber);
    }

    [GeneratedRegex("""^(\+?)375(25|29|33|44)(\d{7})$""")]
    private static partial Regex MyRegex();
}