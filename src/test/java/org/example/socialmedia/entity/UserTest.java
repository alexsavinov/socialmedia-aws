package org.example.socialmedia.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setProfilePictureUrl("http://example.com/profile.jpg");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("http://example.com/profile.jpg", user.getProfilePictureUrl());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(1L);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setProfilePictureUrl("http://example.com/profile.jpg");

        String expected = "User(id=1, username=testuser, email=testuser@example.com, password=null, profilePictureUrl=http://example.com/profile.jpg, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null)";
        assertEquals(expected, user.toString());
    }
}