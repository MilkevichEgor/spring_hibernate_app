package com.example.accessingdatamysql.controller;

import Exceptions.NotExistingIdExpection;
import com.example.accessingdatamysql.entity.Projects;
import com.example.accessingdatamysql.entity.Technology;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.repository.ProjectRepository;
import com.example.accessingdatamysql.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

//    @PostMapping(path="/addNewProject")
//    public @ResponseBody String addNewProject (@RequestParam String title,
//                                               @RequestParam Integer pmId,
//                                               @RequestParam Integer devId) {
//        User pm = userRepository.findById(pmId).get();
//        User dev = userRepository.findById(devId).get();
//        Projects p = new Projects(title, pm, dev);
//        projectRepository.save(p);
//        return "Saved project";
//    }

    @PostMapping(path="/addNewProject")
    public @ResponseBody ResponseEntity<String> addNewProject (@RequestParam String title,
                                               @RequestParam Integer pmId,
                                               @RequestParam Integer devId) {
        try {
            User pm = userRepository.findById(pmId).orElseThrow(() -> new NotExistingIdExpection("Error: Project Manager not found"));
            User dev = userRepository.findById(devId).orElseThrow(() -> new NotExistingIdExpection("Error: Developer not found"));


            Projects p = new Projects(title);
            p.setProjectManager(pm);
            p.setDeveloper(dev);
            projectRepository.save(p);
            // 201
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved project");
        } catch (NotExistingIdExpection ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }

    @PostMapping(path="/addTech")
    public @ResponseBody ResponseEntity<String> addTech (@RequestParam String title,
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
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved technology");
    }

    @PatchMapping(path="/updateDev") // PatchMapping
    public @ResponseBody String updateDev (@RequestParam Integer devId,
                                           @RequestParam Integer projectId) {
        User dev = userRepository.findById(devId).get();
        Projects p = projectRepository.findById(projectId).get();
        p.setDeveloper(dev);
        projectRepository.save(p);
        return "Updated";
    }

    @PatchMapping(path="/updatePm") // PatchMapping
    public @ResponseBody String updatePm (@RequestParam Integer pmId,
                                          @RequestParam Integer projectId) {
        User pm = userRepository.findById(pmId).get();
        Projects p = projectRepository.findById(projectId).get();
        p.setProjectManager(pm);
        projectRepository.save(p);
        return "Updated";
    }

    @PatchMapping(path="/rename") // PatchMapping
    public @ResponseBody String renameUser (@RequestParam Integer id,
                                            @RequestParam String name) {
        User user = userRepository.findById(id).get();
            user.setName(name);
            userRepository.save(user);
            return "Renamed";
    }

    @DeleteMapping(path="/delete") // DeleteMapping
    public @ResponseBody ResponseEntity<String> deleteUser (@RequestParam Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
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
