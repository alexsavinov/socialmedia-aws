package org.example.socialmedia.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FollowTest {

    @Test
    public void testGettersAndSetters() {
        Follow follow = new Follow();
        User follower = new User();
        follower.setId(1L);
        User followed = new User();
        followed.setId(2L);

        follow.setId(1L);
        follow.setFollower(follower);
        follow.setFollowed(followed);

        assertEquals(1L, follow.getId());
        assertEquals(follower, follow.getFollower());
        assertEquals(followed, follow.getFollowed());
    }

    @Test
    public void testEqualsAndHashCode() {
        Follow follow1 = new Follow();
        follow1.setId(1L);
        Follow follow2 = new Follow();
        follow2.setId(1L);

        assertEquals(follow1, follow2);
        assertEquals(follow1.hashCode(), follow2.hashCode());
    }

    @Test
    public void testToString() {
        Follow follow = new Follow();
        follow.setId(1L);
        User follower = new User();
        follower.setId(1L);
        User followed = new User();
        followed.setId(2L);
        follow.setFollower(follower);
        follow.setFollowed(followed);

        String expected = "Follow(id=1, follower=User(id=1, username=null, email=null, password=null, profilePictureUrl=null, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null), followed=User(id=2, username=null, email=null, password=null, profilePictureUrl=null, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null))";
        assertEquals(expected, follow.toString());
    }
}