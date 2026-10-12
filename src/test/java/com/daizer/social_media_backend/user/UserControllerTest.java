package com.daizer.social_media_backend.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

        @Mock
        private UserService service;

        @InjectMocks
        private UserController controller;

        @Test
        void allTest(){
            UserModel alice = new UserModel("alice","alice@email.fr","password123");
            UserModel bob = new UserModel("bob","bob@email.com","password45");
            UserModel charlie = new UserModel("charlie","charlie@email.com","password987");

            List<UserModel> expectuser = new ArrayList<>();

            expectuser.add(alice);
            expectuser.add(bob);
            expectuser.add(charlie);

            when(service.findAll()).thenReturn(expectuser);

            List<UserModel> actualuser = controller.all();

            assertEquals(actualuser,expectuser);
        }


}