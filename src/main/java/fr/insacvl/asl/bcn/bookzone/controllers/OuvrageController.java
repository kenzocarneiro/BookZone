package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.services.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ouvrage")
public class OuvrageController {

    @Autowired
    private Facade facade;

    @GetMapping("{idOuvrage}")
    public String afficherInfoLibraire(@PathVariable Integer idOuvrage, Model model) {
        Ouvrage ouvrage = facade.getOuvrage(idOuvrage);
        model.addAttribute("ouvrage", ouvrage);
        return("ouvrage");
    }
}
