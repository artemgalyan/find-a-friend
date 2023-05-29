package test.fpmibsu.findafriend.services;

import by.fpmibsu.findafriend.entity.Contacts;
import by.fpmibsu.findafriend.entity.User;

import by.fpmibsu.findafriend.services.HashPasswordHasher;
import by.fpmibsu.findafriend.services.PasswordHasher;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

public class HashPasswordHasherTest {
    PasswordHasher passwordHasher = new HashPasswordHasher();

    @Test
    public void testHashPassword_successfullyChecksRightPassword() {
        Contacts contacts = new Contacts("Tom", "Hiddleston", "+375298888888", "tom@tom.uk");
        String expected = "Tom01";
        String hashedPassword = passwordHasher.hashPassword(expected);
        User user = new User(0, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.USER, "tom", hashedPassword);
        assertEquals(passwordHasher.verifyPassword(user, expected), PasswordHasher.PasswordVerificationResult.SUCCESS);
    }

    @Test
    public void testHashPasswordNotCorrect() {
        Contacts contacts = new Contacts("Anne", "Hathaway", "+101303189", "lovely@woman.us");
        String expected = "Hat375%12";
        String incorrect = "hat375%12";
        String hashedPassword = passwordHasher.hashPassword(expected);
        User user = new User(3, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.USER, "annHat", hashedPassword);
        assertEquals(passwordHasher.verifyPassword(user, incorrect), PasswordHasher.PasswordVerificationResult.FAILED);
    }
}

