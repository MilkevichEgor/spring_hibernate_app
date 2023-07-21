package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.entity.Projects;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.repository.ProjectRepository;
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

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name,
              @RequestParam String email, @RequestParam String role) {

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setRole(User.Role.valueOf(role));
        userRepository.save(n);
        return "Saved";
    }

    @PostMapping(path="/addNewProject")
    public @ResponseBody String addNewProject (@RequestParam String title,
                                               @RequestParam Integer pmId,
                                               @RequestParam Integer devId) {
        User pm = userRepository.findById(pmId).get();
        User dev = userRepository.findById(devId).get();
        Projects p = new Projects(title, pm, dev);
        projectRepository.save(p);
        return "Saved";
    }

    @PostMapping(path="/updateDev")
    public @ResponseBody String updateUser (@RequestParam Integer devId,
                                            @RequestParam Integer pmId,
                                            @RequestParam Integer projectId) {
        User dev = userRepository.findById(devId).get();
        User pm = userRepository.findById(pmId).get();
        Projects p = projectRepository.findById(projectId).get();
        p.setDeveloper(dev);
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
            return "Имя пользователя успешно изменено.";
    }


    @PostMapping(path="/delete")
    public @ResponseBody String deleteUser (@RequestParam Integer id) {
        userRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
