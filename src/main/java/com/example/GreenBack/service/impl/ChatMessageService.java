package com.example.GreenBack.service.impl;


import com.example.GreenBack.entity.ChatMessage;
import com.example.GreenBack.entity.ChatRoom;
import com.example.GreenBack.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .map(chatId -> {
                    chatMessage.setChatId(chatId);
                    // Now we pass the sender ID as the current user ID
                    return chatRoomService.getChatRoomByChatId(chatId, chatMessage.getSenderId());
                })
                .orElseThrow(() -> new RuntimeException("Chat room not found or could not be created"));

        return repository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        return chatRoomService.getChatRoomId(senderId, recipientId, false)
                .map(repository::findByChatId)
                .orElse(Collections.emptyList());
    }
}
