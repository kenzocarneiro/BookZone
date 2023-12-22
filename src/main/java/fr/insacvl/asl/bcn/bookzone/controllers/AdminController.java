package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.CommandeService;
import fr.insacvl.asl.bcn.bookzone.services.LibraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private LibraireService libraireService;

    @RequestMapping("commandesDuMois")
    public String voirCommandeDuMois(Model model) {
        List<Commande> commandesDuMois = commandeService.voirCommandeduMois();
        model.addAttribute("commandesDuMois", commandesDuMois);
        return("commandesDuMois");
    }

    @GetMapping("libraires")
    public String listerLibraires(Model model) {
        List<Libraire> libraires = libraireService.findAll();
        model.addAttribute("libraires", libraires);
        return "gererLibraires";
    }

    @PostMapping(value="libraires", params="valider")
    public String validerLibraire(@RequestParam int id) {
        Libraire libraire = libraireService.findById(id);
        if (libraire == null) {
            return "redirect:/error";
        }
        libraireService.validerLibraire(libraire);
        return "redirect:/admin/libraires";
    }

    @PostMapping(value="libraires", params="rejeter")
    public String rejeterLibraire(@RequestParam int id) {
        Libraire libraire = libraireService.findById(id);
        if (libraire == null) {
            return "redirect:/error";
        }
        libraireService.rejeterLibraire(libraire);
        return "redirect:/admin/libraires";
    }

}