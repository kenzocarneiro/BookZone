package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.services.ExemplaireRepository;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import fr.insacvl.asl.bcn.bookzone.services.LibraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping("/libraire")
public class LibraireController {

    @Autowired
    private Facade facade;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private LibraireRepository libraireRepository;
    @RequestMapping("")
    public String login(Model model) {
        // TODO : faire le login avec Spring Security
        return("hello");
    }
    @GetMapping("{loginLibraire}")
    public String afficherInfoLibraire(@PathVariable String loginLibraire, Model model) {
        Set<Exemplaire> exemplaires = facade.getExemplairesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Ouvrage> ouvrages = facade.getOuvragesLibraire(libraireRepository.findByLogin(loginLibraire));
       model.addAttribute("loginLibraire", loginLibraire);
       model.addAttribute("exemplaires", exemplaires);
       model.addAttribute("ouvrages", ouvrages);
       return("afficherInfoLibraire");
    }

    @PostMapping("{loginLibraire}/setPrixVente/{idExemplaire}")
    public String setPrixVente(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float prixVente, Model model) {
        facade.setPrixVente((Libraire)facade.getUtilisateur(loginLibraire), prixVente, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }

    @PostMapping("{loginLibraire}/setFraisPort/{idExemplaire}")
    public String setFraisPort(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float fraisPort, Model model) {
        facade.setFraisPort((Libraire)facade.getUtilisateur(loginLibraire), fraisPort, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }
}