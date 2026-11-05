package com.daizer.social_media_backend.controller;

import com.daizer.social_media_backend.model.User;
import com.daizer.social_media_backend.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    User userOne;
    User userTwo;
    List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userOne = new User("Alice","alice@email.com","123");
        userTwo = new User("Bob","bob@email.com","345");

        userList.add(userOne);
        userList.add(userTwo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testgetAllUser() throws Exception {
        when(userService.getAllUser()).thenReturn(userList);
        ResultActions response = mockMvc.perform(get("/social-media/users")) ;

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUser_idOne_thenReturnUserOne() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userOne));
        ResultActions response = mockMvc.perform(get("/social-media/users/1"));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUser_iTwo_thenReturnUserTwo() throws Exception {
        when(userService.getUserById(2L)).thenReturn(Optional.of(userTwo));
        ResultActions response = mockMvc.perform(get("/social-media/users/2"));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUser_invalid_Id() throws Exception {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(get("/social-media/users/99"));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testcreateUser() throws Exception {
        User userThree = new User("Charlie", "charlie@email.com","678");


        // Setting the ID for the test using reflection
        Field fields = User.class.getDeclaredField("id");
        fields.setAccessible(true);
        fields.set(userThree,3L);

        when(userService.createUser(any(User.class))).thenReturn(userThree);

        ResultActions response = mockMvc.perform(post("/social-media/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "Charlie",
                            "email": "charlie@email.com",
                            "password": "678"
                        }
                        """));

        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/social-media/users/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.username").value("Charlie"))
                .andExpect(jsonPath("$.email").value("charlie@email.com"))
                .andExpect( jsonPath("$.password").value("678"))
                .andDo(print());

        ArgumentCaptor<User> usercaptor = ArgumentCaptor.forClass(User.class);

        verify(userService).createUser(usercaptor.capture());

        User result = usercaptor.getValue();

        assertThat(result.getUsername()).isEqualTo("Charlie");
        assertThat(result.getEmail()).isEqualTo("charlie@email.com");
        assertThat(result.getPassword()).isEqualTo("678");

    }

    @Test
    void delete_valideUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userOne));
        ResultActions response = mockMvc.perform(delete("/social-media/users/1"));

        response.andExpect(status().isNoContent())
                .andDo(print());

        verify(userService).deleteUser(1L);

    }

    @Test
    void delete_indvalideUser() throws Exception {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(delete("/social-media/users/99"));

        response.andExpect(status().isNotFound())
                .andDo(print());

        verify(userService, never()).deleteUser(99L);
    }
}