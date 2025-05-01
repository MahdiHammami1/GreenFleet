package com.example.GreenBack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String recipientId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date timestamp;
}
