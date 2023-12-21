package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.services.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private Facade facade;

    @RequestMapping("")
    //page d'accueil
    public String index(Model model) {
        Set<Ouvrage> ouvrages = facade.getOuvrages();
        model.addAttribute("ouvrages", ouvrages);
        return("index");
    }

}