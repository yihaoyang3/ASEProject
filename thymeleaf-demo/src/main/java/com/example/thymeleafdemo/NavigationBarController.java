package com.example.thymeleafdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationBarController {

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("something", "this is from controller");
        return "navigationbar";
    }
}
