package test.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.controller.commands.users.UpdateUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.UpdateUserHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.ValidTokensDao;
import by.fpmibsu.findafriend.entity.Contacts;
import by.fpmibsu.findafriend.entity.User;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

public class UpdateUserTests {
    @Test
    public void doesntUpdateNonExistingUser() {
        UserDao userDao = Mockito.mock(UserDao.class);
        ValidTokensDao tokensDao = Mockito.mock(ValidTokensDao.class);
        Mockito.doReturn(null).when(userDao).getEntityById(0);
        var handler = new UpdateUserHandler(new SimplePasswordHasher(), userDao, tokensDao);
        var command = new UpdateUserCommand();
        command.userId = 0;
        assertFalse(handler.handle(command));
    }

    @Test
    public void updatesUserNotInvalidatesToken() {
        var user = new User(1, new Contacts("name", "surname", "phoneNumber", "email"),
                new ArrayList<>(), new ArrayList<>(), User.Role.USER, "login", "password");
        UserDao userDao = Mockito.mock(UserDao.class);
        ValidTokensDao tokensDao = Mockito.mock(ValidTokensDao.class);
        Mockito.doReturn(user).when(userDao).getEntityById(1);
        var handler = new UpdateUserHandler(new SimplePasswordHasher(), userDao, tokensDao);
        var command = new UpdateUserCommand();
        command.userId = 1;
        command.name = "newname";
        command.email = "newmail";
        var result = handler.handle(command);
        assertFalse(result);
        assertEquals(user.getContacts().getName(), "newname");
        assertEquals(user.getContacts().getEmail(), "newmail");
        Mockito.verify(userDao, Mockito.times(1)).update(user);
    }

    @DataProvider
    private Object[][] getData() {
        return new Object[][]{{true, false}, {false, true}, {true, true}};
    }

    @Test(dataProvider = "getData")
    public void updateUserInvalidatesToken(boolean updateLogin, boolean updatePassword) {
        var user = new User(1, new Contacts("name", "surname", "phoneNumber", "email"),
                new ArrayList<>(), new ArrayList<>(), User.Role.USER, "login", "password");
        UserDao userDao = Mockito.mock(UserDao.class);
        ValidTokensDao tokensDao = Mockito.mock(ValidTokensDao.class);
        Mockito.doReturn(user).when(userDao).getEntityById(1);
        var handler = new UpdateUserHandler(new SimplePasswordHasher(), userDao, tokensDao);
        var command = new UpdateUserCommand();
        command.userId = 1;
        command.providedPassword = user.getPassword();
        if (updateLogin) {
            command.login = "newlogin";
        }
        if (updatePassword) {
            command.password = "newpassword";
        }
        var result = handler.handle(command);
        assertTrue(result);
        if (updateLogin) {
            assertEquals(command.login, user.getLogin());
        }
        if (updatePassword) {
            assertEquals(command.password, user.getPassword());
        }
        Mockito.verify(userDao, Mockito.times(1)).update(user);
        Mockito.verify(tokensDao, Mockito.times(1)).invalidateTokens(command.userId);
    }


    @DataProvider
    private Object[][] getData2() {
        return new Object[][]{{true, false, true}, {false, true, false}, {true, true, true}};
    }

    @Test(dataProvider = "getData2")
    public void updateUserDoesntInvalidateTokenIfPasswordNotProvided(boolean updateLogin, boolean updatePassword, boolean setWrongPassword) {
        var user = new User(1, new Contacts("name", "surname", "phoneNumber", "email"),
                new ArrayList<>(), new ArrayList<>(), User.Role.USER, "login", "password");
        UserDao userDao = Mockito.mock(UserDao.class);
        ValidTokensDao tokensDao = Mockito.mock(ValidTokensDao.class);
        Mockito.doReturn(user).when(userDao).getEntityById(1);
        var handler = new UpdateUserHandler(new SimplePasswordHasher(), userDao, tokensDao);
        var command = new UpdateUserCommand();
        command.userId = 1;
        if (setWrongPassword) {
            command.providedPassword = user.getPassword() + "1";
        }
        if (updateLogin) {
            command.login = "newlogin";
        }
        if (updatePassword) {
            command.password = "newpassword";
        }
        var result = handler.handle(command);
        assertFalse(result);
        if (updateLogin) {
            assertNotEquals(command.login, user.getLogin());
        }
        if (updatePassword) {
            assertNotEquals(command.password, user.getPassword());
        }
        Mockito.verify(userDao, Mockito.times(1)).update(user);
        Mockito.verify(tokensDao, Mockito.times(0)).invalidateTokens(command.userId);
    }
}
