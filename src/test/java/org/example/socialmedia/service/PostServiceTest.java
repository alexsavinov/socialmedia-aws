package org.example.socialmedia.service;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private S3Service s3Service;

    @Test
    public void getAllPosts_shouldReturnAllPosts() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();
        assertThat(result).hasSize(posts.size());
    }

    @Test
    public void getPostById_shouldReturnPost_whenPostExists() {
        Post post = new Post();
        post.setId(1L);
        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
    }

    @Test
    public void createPost_shouldReturnCreatedPost_withFile() throws IOException {
        Post post = new Post();
        post.setContent("This is a test post.");
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(new byte[0]);
        Mockito.when(s3Service.uploadFile(anyString(), anyString(), any(MultipartFile.class))).thenReturn("s3-url");
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.createPost(post, file);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(post.getContent());
    }

    @Test
    public void createPost_shouldReturnCreatedPost_withoutFile() throws IOException {
        Post post = new Post();
        post.setContent("This is a test post.");
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.createPost(post, null);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(post.getContent());
    }

    @Test
    public void deletePost_shouldDeletePost() {
        postService.deletePost(1L);
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(1L);
    }
}