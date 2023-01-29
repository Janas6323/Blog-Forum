package com.janas.blog.role;

import com.janas.blog.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "display_name")
    private String displayName;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @ManyToMany @JoinTable(
            name = "role_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;

    public Role(String displayName) {
        this.displayName = displayName;
    }
}
