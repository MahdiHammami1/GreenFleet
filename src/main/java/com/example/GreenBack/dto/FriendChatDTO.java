package com.example.GreenBack.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendChatDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String lastMessage;
    private Date lastMessageTime;
}