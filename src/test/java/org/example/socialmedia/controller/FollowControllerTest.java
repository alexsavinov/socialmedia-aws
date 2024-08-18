package org.example.socialmedia.controller;

import org.example.socialmedia.entity.Follow;
import org.example.socialmedia.service.FollowService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FollowController.class)
public class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowService followService;

    @Test
    public void testGetAllFollows() throws Exception {
        List<Follow> follows = Arrays.asList(new Follow(), new Follow());
        Mockito.when(followService.getAllFollows()).thenReturn(follows);

        mockMvc.perform(get("/follows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(follows.size()));
    }

    @Test
    public void testGetFollowById() throws Exception {
        Follow follow = new Follow();
        follow.setId(1L);
        Mockito.when(followService.getFollowById(anyLong())).thenReturn(follow);

        mockMvc.perform(get("/follows/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(follow.getId()));
    }

    @Test
    public void testCreateFollow() throws Exception {
        Follow follow = new Follow();
        follow.setId(1L);
        Mockito.when(followService.createFollow(any(Follow.class))).thenReturn(follow);

        mockMvc.perform(post("/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"follower\": {\"id\": 1}, \"followed\": {\"id\": 2}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(follow.getId()));
    }

    @Test
    public void testDeleteFollow() throws Exception {
        mockMvc.perform(delete("/follows/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(followService, Mockito.times(1)).deleteFollow(1L);
    }
}