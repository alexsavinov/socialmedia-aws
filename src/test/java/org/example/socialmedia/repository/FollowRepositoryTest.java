package org.example.socialmedia.repository;

import org.example.socialmedia.entity.Follow;
import org.example.socialmedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User follower = new User();
        follower.setUsername("follower");
        follower.setEmail("follower@example.com");
        follower = userRepository.save(follower);

        User followed = new User();
        followed.setUsername("followed");
        followed.setEmail("followed@example.com");
        followed = userRepository.save(followed);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        follow = followRepository.save(follow);

        Optional<Follow> foundFollow = followRepository.findById(follow.getId());
        assertThat(foundFollow).isPresent();
        assertThat(foundFollow.get().getFollower().getUsername()).isEqualTo("follower");
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Follow> foundFollow = followRepository.findById(999L);
        assertThat(foundFollow).isNotPresent();
    }

    @Test
    public void testSave() {
        User follower = new User();
        follower.setUsername("follower");
        follower.setEmail("follower@example.com");
        follower = userRepository.save(follower);

        User followed = new User();
        followed.setUsername("followed");
        followed.setEmail("followed@example.com");
        followed = userRepository.save(followed);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        Follow savedFollow = followRepository.save(follow);
        assertThat(savedFollow.getId()).isNotNull();
        assertThat(savedFollow.getFollower().getUsername()).isEqualTo("follower");
    }

    @Test
    public void testDeleteById() {
        User follower = new User();
        follower.setUsername("follower");
        follower.setEmail("follower@example.com");
        follower = userRepository.save(follower);

        User followed = new User();
        followed.setUsername("followed");
        followed.setEmail("followed@example.com");
        followed = userRepository.save(followed);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        follow = followRepository.save(follow);

        followRepository.deleteById(follow.getId());
        Optional<Follow> foundFollow = followRepository.findById(follow.getId());
        assertThat(foundFollow).isNotPresent();
    }
}