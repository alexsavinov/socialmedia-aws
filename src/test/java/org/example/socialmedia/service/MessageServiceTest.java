package org.example.socialmedia.service;

import org.example.socialmedia.entity.Message;
import org.example.socialmedia.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private S3Service s3Service;

    @Test
    public void getAllMessages_shouldReturnAllMessages() {
        List<Message> messages = Arrays.asList(new Message(), new Message());
        Mockito.when(messageRepository.findAll()).thenReturn(messages);

        List<Message> result = messageService.getAllMessages();
        assertThat(result).hasSize(messages.size());
    }

    @Test
    public void getMessageById_shouldReturnMessage_whenMessageExists() {
        Message message = new Message();
        message.setId(1L);
        Mockito.when(messageRepository.findById(anyLong())).thenReturn(Optional.of(message));

        Message result = messageService.getMessageById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(message.getId());
    }

    @Test
    public void getMessageById_shouldThrowException_whenMessageDoesNotExist() {
        Mockito.when(messageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> messageService.getMessageById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Message not found");
    }

    @Test
    public void createMessage_shouldReturnCreatedMessage_withFile() throws IOException {
        Message message = new Message();
        message.setContent("This is a test message.");
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(new byte[0]);
        Mockito.when(s3Service.uploadFile(anyString(), anyString(), any(MultipartFile.class))).thenReturn("s3-url");
        Mockito.when(messageRepository.save(any(Message.class))).thenReturn(message);

        Message result = messageService.createMessage(message);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(message.getContent());
    }

    @Test
    public void createMessage_shouldReturnCreatedMessage_withoutFile() throws IOException {
        Message message = new Message();
        message.setContent("This is a test message.");
        Mockito.when(messageRepository.save(any(Message.class))).thenReturn(message);

        Message result = messageService.createMessage(message);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(message.getContent());
    }

    @Test
    public void deleteMessage_shouldDeleteMessage() {
        messageService.deleteMessage(1L);
        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(1L);
    }
}