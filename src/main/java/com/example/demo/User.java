package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_table")
public class User {

    @Id //states that this is the primary key           strategy = GenerationType.AUTO means it will generate automatically. When one element with the id is deleted it is replaced with the next created element.
    @GeneratedValue(strategy = GenerationType.AUTO) //states how we are going to generate the id.
    private long id;

    @Column(name = "username")
    @Size(min=3)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
    }

    public User(@Size(min=3) String username,
                String password,
                boolean enabled) {
        this.username = username;
        this.setPassword(password);
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
