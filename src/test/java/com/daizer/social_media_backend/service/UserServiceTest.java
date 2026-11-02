package com.daizer.social_media_backend.service;

import com.daizer.social_media_backend.exception.DuplicateUserException;
import com.daizer.social_media_backend.model.User;
import com.daizer.social_media_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    AutoCloseable autoCloseable;
    User user;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
        user = new User("alice","alice@email.com","password123");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testgetAllUser() {

    }

    @Test
    void testgetUserById() {
    }

    @Test
    void testcreateUser() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("alice");
        assertThat(result.getEmail()).isEqualTo("alice@email.com");

        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_DuplicateUsername() {
        when(userRepository.existsByUsername("alice")).thenReturn(true);

        // Verify that an exception is thrown
        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(DuplicateUserException.class);

        //Verify that the user was never saved
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_DuplicateEmail() {
        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(userRepository.existsByEmail("alice@email.com")).thenReturn(true);

        // Verify that an exception is thrown
        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(DuplicateUserException.class);

        //Verify that the user was never saved
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testdeleteUser() {
    }
}
