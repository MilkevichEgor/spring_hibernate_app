package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    public enum Role {
        DEV, PM, OWNER
    }
    
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "projectManager",
               cascade = CascadeType.ALL)
    private List<Projects> pmProject = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "developer",
               cascade = CascadeType.ALL)
    private List<Projects> devProject = new ArrayList<>();

    public User() { }

    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}