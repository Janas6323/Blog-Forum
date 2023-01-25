package com.janas.blog.tag;

import com.janas.blog.enums.TagType;
import com.janas.blog.forum.post.Post;
import com.janas.blog.forum.section.Section;
import com.janas.blog.forum.topic.Topic;
import com.janas.blog.image.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "tag")
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    private String description;
    private TagType type;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    Set<Image> images;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    Set<Section> sections;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    Set<Topic> topics;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    Set<Post> posts;

}
