package com.ms.notesapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public String defaultHome() {
        return "Hello, Welcome to the default home";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Hello ADMIN, Welcome to the Admin home";
    }

    @GetMapping("/admin/contact")
    public String contact() {
        return "Hello Admin. Welcome to Contact Dashboard";
    }

    @GetMapping("/mod/home")
    public String modHome() {
        return "Hello MODERATOR, Welcome to the Mod home";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "Hello USER, Welcome to the USER home";
    }

}
