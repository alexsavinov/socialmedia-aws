package org.example.socialmedia.repository;

import org.example.socialmedia.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testFindById() {
        Message message = new Message();
        message.setContent("test message");
        message = messageRepository.save(message);

        Optional<Message> foundMessage = messageRepository.findById(message.getId());
        assertThat(foundMessage).isPresent();
        assertThat(foundMessage.get().getContent()).isEqualTo("test message");
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Message> foundMessage = messageRepository.findById(999L);
        assertThat(foundMessage).isNotPresent();
    }

    @Test
    public void testSave() {
        Message message = new Message();
        message.setContent("test message");

        Message savedMessage = messageRepository.save(message);
        assertThat(savedMessage.getId()).isNotNull();
        assertThat(savedMessage.getContent()).isEqualTo("test message");
    }

    @Test
    public void testDeleteById() {
        Message message = new Message();
        message.setContent("test message");
        message = messageRepository.save(message);

        messageRepository.deleteById(message.getId());
        Optional<Message> foundMessage = messageRepository.findById(message.getId());
        assertThat(foundMessage).isNotPresent();
    }
}