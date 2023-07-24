package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.Projects;
import com.example.accessingdatamysql.entity.Technology;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.repository.ProjectRepository;
import com.example.accessingdatamysql.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @PostMapping(path="/addUser")
    public @ResponseBody String addNewUser (@RequestParam String name,
                @RequestParam String email, @RequestParam String role) {

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setRole(User.Role.valueOf(role));
        userRepository.save(n);
        return "Saved user";
    }

    @PostMapping(path="/addNewProject")
    public @ResponseBody String addNewProject (@RequestParam String title,
                                               @RequestParam Integer pmId,
                                               @RequestParam Integer devId) {
        User pm = userRepository.findById(pmId).get();
        User dev = userRepository.findById(devId).get();
        Projects p = new Projects(title, pm, dev);
        projectRepository.save(p);
        return "Saved project";
    }

    @PostMapping(path="/addTech")
    public @ResponseBody String addTech (@RequestParam String title,
                                         @RequestParam Integer projectId) {
        Technology existingTech = technologyRepository.findByTitle(title);
        Projects p = projectRepository.findById(projectId).get();

        if (existingTech != null) {
            p.getTechnologies().add(existingTech);
        } else {
            Technology newTech = new Technology(title);
            p.getTechnologies().add(newTech);
        }
        projectRepository.save(p);
        return "Saved technology";
    }

    @PostMapping(path="/updateDev")
    public @ResponseBody String updateDev (@RequestParam Integer devId,
                                           @RequestParam Integer projectId) {
        User dev = userRepository.findById(devId).get();
        Projects p = projectRepository.findById(projectId).get();
        p.setDeveloper(dev);
        projectRepository.save(p);
        return "Updated";
    }

    @PostMapping(path="/updatePm")
    public @ResponseBody String updatePm (@RequestParam Integer pmId,
                                          @RequestParam Integer projectId) {
        User pm = userRepository.findById(pmId).get();
        Projects p = projectRepository.findById(projectId).get();
        p.setProjectManager(pm);
        projectRepository.save(p);
        return "Updated";
    }

    @PostMapping(path="/rename")
    public @ResponseBody String renameUser (@RequestParam Integer id,
                                            @RequestParam String name) {
        User user = userRepository.findById(id).get();
            user.setName(name);
            userRepository.save(user);
            return "Renamed";
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteUser (@RequestParam Integer id) {
        userRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/allProjects")
    public @ResponseBody Iterable<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping(path="/allTech")
    public @ResponseBody Iterable<Technology> getAllTech() {
        return technologyRepository.findAll();
    }
}
