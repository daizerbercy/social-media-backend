package com.daizer.social_media_backend.controller;

import com.daizer.social_media_backend.model.User;
import com.daizer.social_media_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/social-media/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUser(){
        return service.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        Optional<User> user = service.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User created = service.createUser(user);
        return ResponseEntity.created(URI.create("/social-media/users/" + created.getId())).body(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<User>> delete(@PathVariable Long id){
        Optional<User> user = service.getUserById(id);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
