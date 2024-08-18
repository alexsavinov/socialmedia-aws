package org.example.socialmedia.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void testGettersAndSetters() {
        Post post = new Post();
        post.setId(1L);
        post.setContent("Test Content");
        post.setImageUrl("http://example.com/image.jpg");
        User user = new User();
        user.setId(1L);
        post.setUser(user);

        assertEquals(1L, post.getId());
        assertEquals("Test Content", post.getContent());
        assertEquals("http://example.com/image.jpg", post.getImageUrl());
        assertEquals(user, post.getUser());
    }

    @Test
    public void testEqualsAndHashCode() {
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(1L);

        assertEquals(post1, post2);
        assertEquals(post1.hashCode(), post2.hashCode());
    }

    @Test
    public void testToString() {
        Post post = new Post();
        post.setId(1L);
        post.setContent("Test Content");
        post.setImageUrl("http://example.com/image.jpg");
        User user = new User();
        user.setId(1L);
        post.setUser(user);

        String expected = "Post(id=1, content=Test Content, imageUrl=http://example.com/image.jpg, user=User(id=1, username=null, email=null, password=null, profilePictureUrl=null, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null))";
        assertEquals(expected, post.toString());
    }
}