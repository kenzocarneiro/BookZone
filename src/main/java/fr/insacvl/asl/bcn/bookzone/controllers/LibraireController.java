package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.repositories.ExemplaireRepository;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import fr.insacvl.asl.bcn.bookzone.repositories.LibraireRepository;
import fr.insacvl.asl.bcn.bookzone.services.UtilisateurService;
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
    private UtilisateurService utilisateurService;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private LibraireRepository libraireRepository;

    @GetMapping("{loginLibraire}")
    public String afficherInfoLibraire(@PathVariable String loginLibraire, Model model) {
        Set<Exemplaire> exemplaires = facade.getExemplairesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Ouvrage> ouvrages = facade.getOuvragesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Exemplaire> exemplairesCommandes = facade.getExemplairesCommandesDuLibraire(libraireRepository.findByLogin(loginLibraire));
       model.addAttribute("loginLibraire", loginLibraire);
       model.addAttribute("exemplaires", exemplaires);
       model.addAttribute("ouvrages", ouvrages);
       model.addAttribute("exemplairesCommandes", exemplairesCommandes);
       return("afficherInfoLibraire");
    }

    @PostMapping("{loginLibraire}/setPrixVente/{idExemplaire}")
    public String setPrixVente(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float prixVente, Model model) {
        facade.setPrixVente((Libraire)utilisateurService.findByLogin(loginLibraire), prixVente, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }

    @PostMapping("{loginLibraire}/setFraisPort/{idExemplaire}")
    public String setFraisPort(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float fraisPort, Model model) {
        facade.setFraisPort((Libraire)utilisateurService.findByLogin(loginLibraire), fraisPort, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }
}