package com.example.GreenBack.repository;


import com.example.GreenBack.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatId(String chatId);

    @Query("SELECT m FROM ChatMessage m WHERE m.senderId = :userId OR m.recipientId = :userId")
    List<ChatMessage> findMessagesInvolvingUser(String userId);
}