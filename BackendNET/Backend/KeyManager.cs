using System.Security.Cryptography;

namespace Backend;

public class KeyManager
{
    private const string KeyFile = "key";

    public KeyManager(string fileName = KeyFile)
    {
        RsaKey = RSA.Create();
        if (File.Exists(fileName))
        {
            RsaKey.ImportRSAPrivateKey(File.ReadAllBytes(fileName), out _);
        }
        else
        {
            var key = RsaKey.ExportRSAPrivateKey();
            File.WriteAllBytes(fileName, key);
        }
    }

    public RSA RsaKey { get; }
}