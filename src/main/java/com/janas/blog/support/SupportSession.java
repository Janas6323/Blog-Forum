package com.janas.blog.support;

import com.janas.blog.enums.SupportSessionStatus;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "support_session")
public class SupportSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "status")
    private SupportSessionStatus status;
    @OneToOne(mappedBy = "session")
    private SupportRequest request;
    @ManyToOne @JoinColumn(name = "staff_user_id", nullable = false)
    private User user;
}
