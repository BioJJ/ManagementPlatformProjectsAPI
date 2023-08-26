package io.github.biojj.modules.user.services;

import io.github.biojj.exception.UserExistingException;
import io.github.biojj.modules.user.enums.ERole;
import io.github.biojj.modules.user.model.User;
import io.github.biojj.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    User user;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
        user = User.builder()
                .name("administrator")
                .password("123")
                .username("adm")
                .roles(Collections.singleton(ERole.USER))
                .build();
    }

    @Test
    public void testSaveNewUser() {
        User user = this.user;
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());

        verify(userRepository, times(1)).existsByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSaveExistingUser() {
        User user = this.user;
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(UserExistingException.class, () -> userService.save(user));

        verify(userRepository, times(1)).existsByUsername(user.getUsername());
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = this.user;
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());

        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }


    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "non_existing_user";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }
}
