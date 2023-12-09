package fr.insacvl.asl.bcn.bookzone.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloWorldController {
    @RequestMapping("")
    public String hello(Model model) {
        return("hello");
    }
}