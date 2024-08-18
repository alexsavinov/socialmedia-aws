package org.example.socialmedia.controller;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    public void testGetAllPosts() throws Exception {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        Mockito.when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()));
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post();
        post.setId(1L);
        Mockito.when(postService.getPostById(anyLong())).thenReturn(post);

        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()));
    }

    @Test
    public void testCreatePost() throws Exception {
        Post post = new Post();
        post.setId(1L);
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
        MockMultipartFile postPart = new MockMultipartFile("post", "", MediaType.APPLICATION_JSON_VALUE, "{\"title\": \"Test Post\"}".getBytes());

        Mockito.when(postService.createPost(any(Post.class), any())).thenReturn(post);

        mockMvc.perform(multipart("/posts")
                        .file(postPart)
                        .file(image))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()));
    }

    @Test
    public void testDeletePost() throws Exception {
        mockMvc.perform(delete("/posts/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(postService, Mockito.times(1)).deletePost(1L);
    }
}