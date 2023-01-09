package com.oh.springbootApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("userForm")
    public String getUserForm() {
        return "user-form/user-view";
    }
}
