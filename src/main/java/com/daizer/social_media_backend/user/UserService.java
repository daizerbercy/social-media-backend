package com.daizer.social_media_backend.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<UserModel> findAll() {
        return repo.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return repo.findById(id);
    }

    public UserModel create(@Valid @RequestBody UserModel user) {
        if(user.getUsername() == null || user.getEmail() == null || user.getPassword() == null){
            throw new IllegalArgumentException("The user's fields cannot be null");
        }

        if(repo.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("This username is already used");
        }

        if(repo.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("This email is already used");
        }
        return repo.save(user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
