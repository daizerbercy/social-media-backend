package com.daizer.social_media_backend.service;

import com.daizer.social_media_backend.exception.DuplicateUserException;
import com.daizer.social_media_backend.exception.InvalidUserException;
import com.daizer.social_media_backend.model.User;
import com.daizer.social_media_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUser() {
        return repo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repo.findById(id);
    }

    public User createUser(User user) {
        if(repo.existsByUsername(user.getUsername())){
            throw new DuplicateUserException("Username Already exists");
        }

        if(repo.existsByEmail(user.getEmail())){
            throw new DuplicateUserException("Email Already exists");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new InvalidUserException("Username must not be blank");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidUserException("Email must not be blank");
        }

        return repo.save(user);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
