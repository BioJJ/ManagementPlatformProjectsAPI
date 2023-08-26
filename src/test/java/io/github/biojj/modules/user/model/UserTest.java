package io.github.biojj.modules.user.model;

import io.github.biojj.modules.user.enums.ERole;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password")
                .name("John Doe")
                .roles(getTestRoles())
                .build();

        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals(getTestRoles(), user.getRoles());

        assertNotNull(user);
    }

    private Set<ERole> getTestRoles() {
        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.USER);
        roles.add(ERole.ADMIN);
        return roles;
    }
}
