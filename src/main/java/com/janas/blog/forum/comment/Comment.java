package com.janas.blog.forum.comment;

import com.janas.blog.forum.post.Post;
import com.janas.blog.report.Report;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity @Data @Table(name = "comment")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "publish_date")
    private LocalDateTime publishDate;
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;
}
