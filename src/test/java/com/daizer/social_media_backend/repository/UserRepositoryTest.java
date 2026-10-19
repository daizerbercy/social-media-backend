package com.daizer.social_media_backend.repository;

import com.daizer.social_media_backend.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        user = new User("alice","alice@email.com","password123");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testExistsByUsername_Found(){
        boolean userFound = userRepository.existsByUsername("alice");
        assertTrue("the user isn't found", userFound);
    }

    // Test case FAILURE
    @Test
    void testExistsByUsername_NotFound(){
        boolean userNotFound = userRepository.existsByUsername("bob");
        assertFalse("The user exists",userNotFound);
    }

    @Test
    void testExistsByEmail_Found(){
        boolean emailFound = userRepository.existsByEmail("alice@email.com");
        assertTrue("email isn't found", emailFound);
    }

    @Test
    void testExistsByEmail_NotFound(){
        boolean emailNotFound = userRepository.existsByEmail("bob@email.com");
        assertFalse("email exists", emailNotFound);
    }


}
