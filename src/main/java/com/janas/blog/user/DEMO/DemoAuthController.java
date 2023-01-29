package com.janas.blog.user.DEMO;

import com.janas.blog.configuration.security.JwtUtils;
import com.janas.blog.role.Role;
import com.janas.blog.role.RoleRepository;
import com.janas.blog.user.User;
import com.janas.blog.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DemoAuthController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder passEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    private String message;
    /* USERNAME REGEX (Requirements below)
    Username consists of alphanumeric characters (a-z, A-Z, 0-9) lowercase or uppercase, also includes underscore (_) and hyphen (-).
    The underscore (_), or hyphen (-) must not be the first or last character and does not appear consecutively, e.g., user__name.
    Username length must be 5 to 25 characters total.*/
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]([_-](?![_-])|[a-zA-Z0-9]){3,23}[a-zA-Z0-9]$");

    /* PASSWORD REGEX (Requirements below)
    Password must contain at least one digit [0-9], lowercase letter [a-z], uppercase letter [A-Z].
    Password must contain at least one special character like ! @ # & ( ) (no square brackets).
    Password length must be 8 to 30 characters total.*/
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,30}$");

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody DemoAuthDto dto) {
        if (validateUser(dto)) {

            Role role = new Role("ROLE_USER");
            roleRepo.save(role);

            User user = new User(dto.getUsername(), dto.getEmail(), passEncoder.encode(dto.getPassword()));
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
            userRepo.save(user);

            message = "Account created successfully.";
            return ResponseEntity.ok().body(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody DemoAuthDto dto) {
        User user = userRepo.findByUsername(dto.getUsername()).orElse(null);
        if (user == null) {
            message = "No user found with username "+dto.getUsername();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateToken(user);
        message = "Logged in successfully.";

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").body(message);
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/test")
    public String test() {
        return "Method security test successful.";
    }

    @GetMapping("/test2")
    public String test2() {
        return "Security Filter Chain test successful.";
    }

    private boolean validateUser(DemoAuthDto user) {
        if(user == null) {
            message = "Invalid request. No user defined.";
            return false;
        } else if (userRepo.findByUsername(user.getUsername()).orElse(null) != null) {
            message = "Username is taken.";
            return false;
        } else if (userRepo.findByEmail(user.getEmail()).orElse(null) != null) {
            message = "User with this email already exists.";
            return false;
        } else if (!user.getPassword().equals(user.getRepeatPassword())) {
            message = "Passwords don't match.";
            return false;
        } else if (validateRegex(user.getUsername(), USERNAME_PATTERN)) {
            message = "Username does not match regex.";
            return false;
        } else if (validateRegex(user.getPassword(), PASSWORD_PATTERN)) {
            message = "Password does not match regex.";
            return false;
        } else {
            return true;
        }
    }

    private static boolean validateRegex(final String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        return !matcher.matches();
    }
}
