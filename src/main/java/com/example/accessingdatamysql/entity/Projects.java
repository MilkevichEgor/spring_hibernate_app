package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import com.example.accessingdatamysql.repository.UserRepository;
import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Projects {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pr_tech",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tech_id"))
    private List<Technology> technologies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id")
    private User projectManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_id")
    private User developer;

    public Projects() { }

    public Projects(String title) {
        this.title = title;
    }

//    public Projects(String title, Integer pmId, Integer devId) {
//        this.title = title;
//        this.projectManagerId = pmId;
//        this.developerId = devId;
//    }

    public Projects(String title, User pm, User dev) {
        this.title = title;
        this.projectManager = pm;
        this.developer = dev;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Projects getById(Integer id) {
        return this;
    }

    public void setUser(User user){
        this.projectManager = user;
        this.developer = user;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }
}
