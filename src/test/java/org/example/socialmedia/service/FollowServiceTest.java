package org.example.socialmedia.service;

import org.example.socialmedia.entity.Follow;
import org.example.socialmedia.repository.FollowRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @MockBean
    private FollowRepository followRepository;

    @MockBean
    private S3Service s3Service;

    @Test
    public void getAllFollows_shouldReturnAllFollows() {
        List<Follow> follows = Arrays.asList(new Follow(), new Follow());
        Mockito.when(followRepository.findAll()).thenReturn(follows);

        List<Follow> result = followService.getAllFollows();
        assertThat(result).hasSize(follows.size());
    }

    @Test
    public void getFollowById_shouldReturnFollow_whenFollowExists() {
        Follow follow = new Follow();
        follow.setId(1L);
        Mockito.when(followRepository.findById(anyLong())).thenReturn(Optional.of(follow));

        Follow result = followService.getFollowById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(follow.getId());
    }

    @Test
    public void createFollow_shouldReturnCreatedFollow() {
        Follow follow = new Follow();
        follow.setId(1L);
        Mockito.when(followRepository.save(any(Follow.class))).thenReturn(follow);

        Follow result = followService.createFollow(follow);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(follow.getId());
    }

    @Test
    public void deleteFollow_shouldDeleteFollow() {
        followService.deleteFollow(1L);
        Mockito.verify(followRepository, Mockito.times(1)).deleteById(1L);
    }
}