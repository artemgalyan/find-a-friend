package by.fpmibsu.findafriend.services;

import by.fpmibsu.findafriend.entity.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class HashPasswordHasher extends PasswordHasher {
    private static final int algorithmVersion = 1;
    private static final String passwordPrefix = algorithmVersion + "|";
    private static final byte[] bytePasswordPrefix = passwordPrefix.getBytes();
    private static final int byteArrayForPasswordSize = 82;
    private static final int saltSize = 16;
    private final int iterationCount = 100;
    private final int passwordSize = 64 * 8;
    private final String algorithm = "PBKDF2WithHmacSHA1";

    @Override
    public String hashPassword(String password) {
        var random = new SecureRandom();
        var salt = new byte[saltSize];
        random.nextBytes(salt);
        byte[] hash = hash(password, salt);
        byte[] finalPassword = new byte[bytePasswordPrefix.length + hash.length + salt.length];
        System.arraycopy(bytePasswordPrefix, 0, finalPassword, 0, bytePasswordPrefix.length);
        System.arraycopy(salt, 0, finalPassword, bytePasswordPrefix.length, salt.length);
        System.arraycopy(hash, 0, finalPassword, bytePasswordPrefix.length + salt.length, hash.length);
        var encoder = Base64.getEncoder().withoutPadding();
        return encoder.encodeToString(finalPassword);
    }

    private byte[] hash(String password, byte[] salt) {
        var spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, passwordSize);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash;
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return hash;
    }

    @Override
    public PasswordVerificationResult verifyPassword(User user, String providedPassword) {
        var decoder = Base64.getDecoder();
        try {
            var decodedUserPassword = decoder.decode(user.getPassword());
            if (decodedUserPassword.length != byteArrayForPasswordSize) {
                return providedPassword.equals(user.getPassword())
                        ? PasswordVerificationResult.SUCCESS_NEEDS_TO_BE_REHASHED
                        : PasswordVerificationResult.FAILED;
            }
            var salt = Arrays.copyOfRange(decodedUserPassword, bytePasswordPrefix.length, bytePasswordPrefix.length + saltSize);
            var hash = Arrays.copyOfRange(decodedUserPassword, bytePasswordPrefix.length + saltSize, decodedUserPassword.length);
            byte[] passwordHash = hash(providedPassword, salt);
            return Arrays.equals(passwordHash, hash)
                    ? PasswordVerificationResult.SUCCESS
                    : PasswordVerificationResult.FAILED;
        } catch (Exception e) {
            return new SimplePasswordHasher().verifyPassword(user, providedPassword).equals(PasswordVerificationResult.SUCCESS)
                    ? PasswordVerificationResult.SUCCESS_NEEDS_TO_BE_REHASHED
                    : PasswordVerificationResult.FAILED;
        }
    }
}
