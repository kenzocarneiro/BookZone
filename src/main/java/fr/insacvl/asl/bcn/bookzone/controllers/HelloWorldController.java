package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.services.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloWorldController {
    @Autowired
    private Facade facade;
    @RequestMapping("")
    public String hello(Model model) {
        facade.createPersonne("Bob", "bob");
        facade.createUtilisateur("toto@gmail.com", "toto", "azerty");
        facade.createAdministrateur("admin@gmail.com", "admin", "root");
        // TODO: tester Libraire et Client
        return("hello");
    }
}