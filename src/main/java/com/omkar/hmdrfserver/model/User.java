package com.omkar.hmdrfserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private boolean active;

    @Column
    private String roles = "";

    public User(String username, String email,String password, String roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.active = true;
    }

    protected User(){}

    @JsonIgnore
    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
