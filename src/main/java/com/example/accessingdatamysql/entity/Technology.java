package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "technology")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToMany
    @JoinTable(name = "pr_tech",
            joinColumns = @JoinColumn(name = "tech_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Projects> projects;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
