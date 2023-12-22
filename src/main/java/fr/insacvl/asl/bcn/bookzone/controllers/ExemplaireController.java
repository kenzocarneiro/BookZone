package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.services.LibraireService;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exemplaire")
public class ExemplaireController {

    @Autowired
    private OuvrageService ouvrageService;

    @Autowired
    private LibraireService libraireService;

    @GetMapping("{idExemplaire}")
    public String afficherInfoLibraire(@PathVariable Integer idExemplaire, Model model) {
        Exemplaire exemplaire = ouvrageService.getExemplaire(idExemplaire);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("noteMoyenne", libraireService.getNoteMoyenne(exemplaire.getVendeur()));
        return("exemplaire");
    }
}
