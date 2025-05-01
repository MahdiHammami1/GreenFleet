package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sender_id", "recipient_id"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId;

    private String recipientId;

    @Column(name = "chat_id")
    private String chatId;
}
