package com.daizer.social_media_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<UserModel> all(){
        return service.findAll();
    }

    // Fix this
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> get(@PathVariable Long id){
        Optional<UserModel> u = service.findById(id);
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserModel user){
        UserModel created = service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
