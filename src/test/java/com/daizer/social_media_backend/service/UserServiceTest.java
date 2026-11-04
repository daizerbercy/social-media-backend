package com.daizer.social_media_backend.service;

import com.daizer.social_media_backend.exception.DuplicateUserException;
import com.daizer.social_media_backend.exception.InvalidUserException;
import com.daizer.social_media_backend.model.User;
import com.daizer.social_media_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

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
        User user1 = new User("bob","bob@email.com","password2");
        List<User> users = List.of(user,user1);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUser();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(userRepository).findAll();
    }

    @Test
    void testgetUserById_valide() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result).contains(user);

        verify(userRepository).findById(1L);
    }

    @Test
    void testgetUserById_unvalide(){
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99L);

        assertThat(result).isEmpty();

        verify(userRepository).findById(99L);
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
    void testCreateUser_BlankUsername(){
        user.setUsername("");
        when(userRepository.existsByUsername("")).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(InvalidUserException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_NullUsername(){
        user.setUsername(null);
        when(userRepository.existsByUsername(null)).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(InvalidUserException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_BlankEmail(){
        user.setEmail("");
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(InvalidUserException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_NullEmail(){
        user.setEmail(null);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(user)).isInstanceOf(InvalidUserException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_deleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}
