package com.daizer.social_media_backend.user;

import java.util.List;
import java.util.Optional;

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

    public UserModel create(UserModel user) {
        return repo.save(user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
