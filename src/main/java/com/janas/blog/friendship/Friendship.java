package com.janas.blog.friendship;

import com.janas.blog.enums.FriendshipStatus;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "friendship")
public class Friendship {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private FriendshipStatus status;
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    @Column(name = "response_date")
    private LocalDateTime responseDate;
    @ManyToOne @JoinColumn(name = "requesting_user_id")
    private User requestingUser;
    @ManyToOne @JoinColumn(name = "responding_user_id")
    private User respondingUser;

}
