package com.janas.blog.forum.section;

import com.janas.blog.forum.topic.Topic;
import com.janas.blog.tag.Tag;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.List;

@Entity @Data @Table(name = "section")
public class Section {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "allowed_roles")
    private List<String> allowedRoles;
    @ManyToMany
    @JoinTable(
            name = "section_tags",
            joinColumns = @JoinColumn(name = "section_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "section")
    private Set<Topic> topics;
}
