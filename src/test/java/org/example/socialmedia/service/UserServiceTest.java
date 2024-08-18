package org.example.socialmedia.service;

import org.example.socialmedia.entity.User;
import org.example.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private S3Service s3Service;

    @Test
    public void getAllUsers_shouldReturnAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertThat(result).hasSize(users.size());
    }

    @Test
    public void getUserById_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
    }

    @Test
    public void getUserById_shouldThrowException_whenUserDoesNotExist() {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    public void createUser_shouldReturnCreatedUser_withProfilePicture() throws IOException {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(new byte[0]);
        Mockito.when(s3Service.uploadFile(anyString(), anyString(), any(MultipartFile.class))).thenReturn("s3-url");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user, file);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getProfilePictureUrl()).isEqualTo("s3-url");
    }

    @Test
    public void createUser_shouldReturnCreatedUser_withoutProfilePicture() throws IOException {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user, null);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getProfilePictureUrl()).isNull();
    }

    @Test
    public void deleteUser_shouldDeleteUser() {
        userService.deleteUser(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}