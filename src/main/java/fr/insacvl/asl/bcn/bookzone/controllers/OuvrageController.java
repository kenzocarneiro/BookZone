package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ouvrage")
public class OuvrageController {

    @Autowired
    private OuvrageService ouvrageService;

    @GetMapping("{idOuvrage}")
    public String afficherInfoLibraire(@PathVariable Integer idOuvrage, Model model) {
        Ouvrage ouvrage = ouvrageService.getOuvrage(idOuvrage);
        model.addAttribute("ouvrage", ouvrage);
        return("ouvrage");
    }
}
