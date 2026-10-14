package com.daizer.social_media_backend.service;

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
        return repo.save(user);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
