package com.example.GreenBack.service.impl;


import com.example.GreenBack.entity.ChatRoom;
import com.example.GreenBack.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        // First try to find an existing chat room
        Optional<ChatRoom> existingRoom = chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId);

        // If found, return its chatId
        if (existingRoom.isPresent()) {
            return Optional.of(existingRoom.get().getChatId());
        }

        // If not found but we're allowed to create a new one
        if (createNewRoomIfNotExists) {
            // Create a unique chatId based on both user IDs
            var chatId = String.format("%s_%s", senderId, recipientId);

            // Create two ChatRoom entities - one for each direction of the conversation
            ChatRoom senderRecipient = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            ChatRoom recipientSender = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(recipientId)
                    .recipientId(senderId)
                    .build();

            // Save both entities
            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

            return Optional.of(chatId);
        }

        // If not found and we can't create a new one
        return Optional.empty();
    }



    public ChatRoom getChatRoomByChatId(String chatId, String currentUserId) {
        List<ChatRoom> rooms = chatRoomRepository.findByChatId(chatId);

        // Filter to find the room where the current user is the sender
        return rooms.stream()
                .filter(room -> room.getSenderId().equals(currentUserId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Chat room not found for user: " + currentUserId));
    }
}