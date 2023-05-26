package test.fpmibsu.findafriend.services;

import by.fpmibsu.findafriend.entity.Contacts;
import by.fpmibsu.findafriend.entity.User;

import by.fpmibsu.findafriend.services.PasswordHasher;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

public class HashPasswordHasherTest {
    PasswordHasher passwordHasher;
    private final int passwordSize = 64 * 8;

    @Test
    public void testHashPassword() {
        Contacts contacts = new Contacts("Tom", "Hiddleston", "+375298888888", "tom@tom.uk");
        String expected = "Tom01";
        String hashedPassword = passwordHasher.hashPassword(expected);
        User user = new User(0, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.USER, "tom", hashedPassword);
        if (passwordHasher.verifyPassword(user, expected) == PasswordHasher.PasswordVerificationResult.SUCCESS) {
            assertTrue(true);
        } else assertFalse(false);
    }

    @Test
    public void testHashPasswordLength() {
        Contacts contacts = new Contacts("Abraham", "Lincoln", "+40429848596", "ablic21@gmail.com");
        String expected = "1090#01-_42";
        String hashedPassword = passwordHasher.hashPassword(expected);
        User user = new User(1, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.MODERATOR, "abusa", hashedPassword);
        if (user.getPassword().length() == passwordSize) {
            assertTrue(true);
        } else assertFalse(false);
    }

    @Test
    public void testHashPasswordNotCorrect() {
        Contacts contacts = new Contacts("Anne", "Hathaway", "+101303189", "lovely@woman.us");
        String expected = "Hat375%12";
        String incorrect = "hat375%12";
        String hashedPassword = passwordHasher.hashPassword(expected);
        User user = new User(3, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.USER, "tom", hashedPassword);
        if (passwordHasher.verifyPassword(user, incorrect) == PasswordHasher.PasswordVerificationResult.FAILED) {
            assertTrue(true);
        } else assertFalse(false);
    }
}

