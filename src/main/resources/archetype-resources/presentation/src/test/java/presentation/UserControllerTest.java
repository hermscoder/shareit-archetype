package ${package}.presentation;

import ${package}.domain.dto.CreateUser;
import ${package}.domain.dto.User;
import ${package}.domain.dto.UserCreated;
import ${package}.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class UserControllerTest {

    private final UserController userController;
    private final UserService userService;

    private final User expectedUser;

    public UserControllerTest() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        expectedUser = new User(1L, "test@gmail.com", "password", "test", LocalDate.now());
    }

    @Test
    public void testGetUserById() {

        when(userService.findById(anyLong())).thenReturn(expectedUser);
        User userById = userController.getUserById(1L);
        assertNotNull(userById);
        assertEquals(expectedUser, userById);
    }

    @Test
    public void testUserRegister() {
        when(userService.createUser(any(CreateUser.class))).thenReturn(1L);

        UserCreated userCreated = userController.userRegister(
                new CreateUser("any_email@mail.com",
                        "any_password",
                        "any_password",
                        "any_name",
                        LocalDate.now()));
        assertNotNull(userCreated);
        assertEquals(1L, userCreated.getId());
    }
}