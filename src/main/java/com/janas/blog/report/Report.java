package com.janas.blog.report;

import com.janas.blog.forum.comment.Comment;
import com.janas.blog.forum.post.Post;
import com.janas.blog.image.Image;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "report")
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne @JoinColumn(name = "reporting_user_id", nullable = false)
    private User reportingUser;
    @ManyToOne @JoinColumn(name = "reported_user_id")
    private User reportedUser;
    @ManyToOne @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne @JoinColumn(name = "report_reason_id")
    private ReportReason reason;
}
