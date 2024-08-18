package org.example.socialmedia.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testGettersAndSetters() {
        Message message = new Message();
        message.setId(1L);
        message.setContent("Test Content");
        User sender = new User();
        sender.setId(1L);
        User receiver = new User();
        receiver.setId(2L);
        message.setSender(sender);
        message.setReceiver(receiver);

        assertEquals(1L, message.getId());
        assertEquals("Test Content", message.getContent());
        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
    }

    @Test
    public void testEqualsAndHashCode() {
        Message message1 = new Message();
        message1.setId(1L);
        Message message2 = new Message();
        message2.setId(1L);

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    public void testToString() {
        Message message = new Message();
        message.setId(1L);
        message.setContent("Test Content");
        User sender = new User();
        sender.setId(1L);
        User receiver = new User();
        receiver.setId(2L);
        message.setSender(sender);
        message.setReceiver(receiver);

        String expected = "Message(id=1, content=Test Content, sender=User(id=1, username=null, email=null, password=null, profilePictureUrl=null, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null), receiver=User(id=2, username=null, email=null, password=null, profilePictureUrl=null, posts=null, followers=null, followings=null, sentMessages=null, receivedMessages=null))";
        assertEquals(expected, message.toString());
    }
}