package org.example.socialmedia.repository;

import org.example.socialmedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindById_NotFound() {
        Optional<User> foundUser = userRepository.findById(999L);
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isNotPresent();
    }
}