package io.github.biojj.modules.user.controller;

import io.github.biojj.exception.UserExistingException;
import io.github.biojj.modules.user.model.User;
import io.github.biojj.modules.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;
    private Validator validator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSalvarUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        ResponseEntity<String> response = userController.salvar(user);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(userService, times(1)).save(user);
    }


    @Test
    public void testSalvarUserExistingException() {
        User user = new User();
        user.setUsername("existingUsername");
        user.setPassword("password");

        doThrow(new UserExistingException("Username already exists")).when(userService).save(user);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userController.salvar(user));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getReason().contains("Username already exists"));

        verify(userService, times(1)).save(user);
    }

}
