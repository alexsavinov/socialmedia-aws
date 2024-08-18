package org.example.socialmedia.controller;

import org.example.socialmedia.entity.User;
import org.example.socialmedia.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        Mockito.when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        Mockito.when(userService.getUserById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        MockMultipartFile profilePicture = new MockMultipartFile("profilePicture", "profile.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
        MockMultipartFile userPart = new MockMultipartFile("user", "", MediaType.APPLICATION_JSON_VALUE, "{\"username\": \"testuser\", \"email\": \"testuser@example.com\"}".getBytes());

        Mockito.when(userService.createUser(any(User.class), any())).thenReturn(user);

        mockMvc.perform(multipart("/users")
                        .file(userPart)
                        .file(profilePicture))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    public void testCreateUser_BadRequest() throws Exception {
        MockMultipartFile userPart = new MockMultipartFile("user", "", MediaType.APPLICATION_JSON_VALUE, "{\"username\": \"\", \"email\": \"\"}".getBytes());

        mockMvc.perform(multipart("/users")
                        .file(userPart))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).deleteUser(1L);
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        Mockito.when(userService.getUserById(anyLong())).thenReturn(null);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());
    }
}