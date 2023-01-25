package com.janas.blog.image;

import com.janas.blog.enums.ImageType;
import com.janas.blog.forum.post.Post;
import com.janas.blog.report.Report;
import com.janas.blog.tag.Tag;
import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "image")
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "path")
    private String path;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private ImageType type;
    @ManyToMany @JoinTable(
            name = "image_tags",
            joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User profilePhotoOwner;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne @JoinColumn(name = "post_id")
    private Post post;
    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;


}
