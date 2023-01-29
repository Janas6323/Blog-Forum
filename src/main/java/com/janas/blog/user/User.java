package com.janas.blog.user;

import com.janas.blog.conversation.Conversation;
import com.janas.blog.conversation.Message;
import com.janas.blog.enums.AccountStatus;
import com.janas.blog.enums.OnlineStatus;
import com.janas.blog.forum.comment.Comment;
import com.janas.blog.forum.post.Post;
import com.janas.blog.forum.topic.Topic;
import com.janas.blog.friendship.Friendship;
import com.janas.blog.image.Image;
import com.janas.blog.report.Report;
import com.janas.blog.role.Role;
import com.janas.blog.support.SupportRequest;
import com.janas.blog.support.SupportSession;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;

@Entity @Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "bio")
    private String bio;
    @Column(name = "join_date")
    private LocalDateTime joinDate;
    @Column(name = "account_status")
    private AccountStatus accountStatus;
    @Column(name = "online_status")
    private OnlineStatus onlineStatus;
    @ManyToMany @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image profilePicture;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Image> images;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Topic> topics;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SupportRequest> supportRequests;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SupportSession> supportSessions;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Conversation> conversations;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> sentMessages;
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> receivedMessages;
    @OneToMany(mappedBy = "requestingUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Friendship> sentFriendshipRequests;
    @OneToMany(mappedBy = "respondingUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Friendship> receivedFriendshipRequests;
    @OneToMany(mappedBy = "reportingUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> sentReports;
    @OneToMany(mappedBy = "reportedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> receivedReports;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null) {
            return null;
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getDisplayName()));

            role.getPrivileges().stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getDisplayName()))
                    .forEach(authorities::add);
        }
        return authorities;
    }
}
