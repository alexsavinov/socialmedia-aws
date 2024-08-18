package org.example.socialmedia.controller;

import org.example.socialmedia.entity.Message;
import org.example.socialmedia.service.MessageService;
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

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testGetAllMessages() throws Exception {
        List<Message> messages = Arrays.asList(new Message(), new Message());
        Mockito.when(messageService.getAllMessages()).thenReturn(messages);

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(messages.size()));
    }

    @Test
    public void testGetMessageById() throws Exception {
        Message message = new Message();
        message.setId(1L);
        Mockito.when(messageService.getMessageById(anyLong())).thenReturn(message);

        mockMvc.perform(get("/messages/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(message.getId()));
    }

    @Test
    public void testCreateMessage() throws Exception {
        Message message = new Message();
        message.setId(1L);
        Mockito.when(messageService.createMessage(any(Message.class))).thenReturn(message);

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"test message\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(message.getId()));
    }

    @Test
    public void testDeleteMessage() throws Exception {
        mockMvc.perform(delete("/messages/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(messageService, Mockito.times(1)).deleteMessage(1L);
    }

}