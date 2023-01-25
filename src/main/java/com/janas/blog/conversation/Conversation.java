package com.janas.blog.conversation;

import com.janas.blog.enums.ConversationType;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "conversation")
public class Conversation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private ConversationType type;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> messages;
}
