package com.daizer.social_media_backend.repository;

import com.daizer.social_media_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // method created to avoid same username or same email
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
