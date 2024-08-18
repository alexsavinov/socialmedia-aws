package org.example.socialmedia.repository;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        Post post = new Post();
        post.setContent("This is a test post.");
        post.setUser(user);
        post = postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getContent()).isEqualTo("This is a test post.");
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Post> foundPost = postRepository.findById(999L);
        assertThat(foundPost).isNotPresent();
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        Post post = new Post();
        post.setContent("This is a test post.");
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getContent()).isEqualTo("This is a test post.");
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        Post post = new Post();
        post.setContent("This is a test post.");
        post.setUser(user);
        post = postRepository.save(post);

        postRepository.deleteById(post.getId());
        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isNotPresent();
    }
}