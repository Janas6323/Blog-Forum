package com.janas.blog.forum.topic;

import com.janas.blog.forum.post.Post;
import com.janas.blog.forum.section.Section;
import com.janas.blog.tag.Tag;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "topic")
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne @JoinColumn(name = "section_id", nullable = false)
    private Section section;
    @ManyToMany @JoinTable(
            name = "topic_tags",
            joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "topic")
    private Set<Post> posts;
}
