package org.example.socialmedia.repository;

import org.example.socialmedia.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}