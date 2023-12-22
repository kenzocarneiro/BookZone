package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.CommandeService;
import fr.insacvl.asl.bcn.bookzone.services.UtilisateurService;
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
    private UtilisateurService utilisateurService;

    @RequestMapping("commandesDuMois")
    public String voirCommandeDuMois(Model model) {
        List<Commande> commandesDuMois = commandeService.voirCommandeduMois();
        model.addAttribute("commandesDuMois", commandesDuMois);
        return("commandesDuMois");
    }

    @GetMapping("libraires")
    public String listerLibraires(Model model) {
        List<Libraire> libraires = utilisateurService.findAllLibraires();
        model.addAttribute("libraires", libraires);
        return "gererLibraires";
    }

    @PostMapping(value="libraires", params="valider")
    public String validerLibraire(@RequestParam int id) {
        Libraire libraire = utilisateurService.findByIdLibraire(id);
        if (libraire == null) {
            return "redirect:/error";
        }
        utilisateurService.validerLibraire(libraire);
        return "redirect:/admin/libraires";
    }

    @PostMapping(value="libraires", params="rejeter")
    public String rejeterLibraire(@RequestParam int id) {
        Libraire libraire = (Libraire) utilisateurService.findById(id);
        if (libraire == null) {
            return "redirect:/error";
        }
        utilisateurService.rejeterLibraire(libraire);
        return "redirect:/admin/libraires";
    }

}