package com.janas.blog.support;

import com.janas.blog.enums.SupportRequestStatus;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "support_request")
public class SupportRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "message")
    private String message;
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    @Column(name = "response_date")
    private LocalDateTime responseDate;
    @Column(name = "status")
    private SupportRequestStatus status;
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private SupportSession session;
}
