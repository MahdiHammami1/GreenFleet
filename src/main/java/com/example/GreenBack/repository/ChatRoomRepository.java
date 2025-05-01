package com.example.GreenBack.repository;


import com.example.GreenBack.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
    List<ChatRoom> findByChatId(String chatId);
}