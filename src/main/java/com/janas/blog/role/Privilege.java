package com.janas.blog.role;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "privilege")
public class Privilege {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "display_name")
    private String displayName;
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
