package com.oh.springbootApp.controller;

import com.oh.springbootApp.Entity.User;
import com.oh.springbootApp.Repository.RoleRepository;
import com.oh.springbootApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {


    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping({"userform", "userForm"})
    public String getUserForm(Model model) {
        //    public String getUserForm(Model model)
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";
        //return "/user-view";
        //return "/user-form";
    }
}
