package com.janas.blog.user.DEMO;

import lombok.Data;

@Data
public class DemoAuthDto {
    private String username;
    private String email;
    private String password;
    private String repeatPassword;

    public DemoAuthDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
