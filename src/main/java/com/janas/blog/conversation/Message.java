package com.janas.blog.conversation;

import com.janas.blog.enums.MessageStatus;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "send_date")
    private LocalDateTime sendDate;
    @Column(name = "seen_date")
    private LocalDateTime seenDate;
    @Column(name = "status")
    private MessageStatus status;
    @ManyToOne @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @ManyToOne @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne @JoinColumn(name = "recipient_id")
    private User recipient;
}
