package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.ExemplaireDTO;
import fr.insacvl.asl.bcn.bookzone.dtos.OuvrageDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.repositories.ExemplaireRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.LibraireRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.OuvrageRepository;
import fr.insacvl.asl.bcn.bookzone.services.LibraireService;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;
import fr.insacvl.asl.bcn.bookzone.services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping("/libraire")
public class LibraireController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private LibraireService libraireService;
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private OuvrageRepository ouvrageRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private LibraireRepository libraireRepository;

    @GetMapping("{loginLibraire}")
    public String afficherInfoLibraire(@PathVariable String loginLibraire, Model model) {
        Set<Exemplaire> exemplaires = libraireService.getExemplairesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Ouvrage> ouvrages = libraireService.getOuvragesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Exemplaire> exemplairesCommandes = libraireService.getExemplairesCommandesDuLibraire(libraireRepository.findByLogin(loginLibraire));
       model.addAttribute("loginLibraire", loginLibraire);
       model.addAttribute("exemplaires", exemplaires);
       model.addAttribute("ouvrages", ouvrages);
       model.addAttribute("exemplairesCommandes", exemplairesCommandes);
       return("afficherInfoLibraire");
    }

    @PostMapping("{loginLibraire}/setPrixVente/{idExemplaire}")
    public String setPrixVente(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float prixVente, Model model) {
        libraireService.setPrixVente((Libraire)utilisateurService.findByLogin(loginLibraire), prixVente, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }

    @PostMapping("{loginLibraire}/setFraisPort/{idExemplaire}")
    public String setFraisPort(@PathVariable String loginLibraire, @PathVariable int idExemplaire, @RequestParam float fraisPort, Model model) {
        libraireService.setFraisPort((Libraire)utilisateurService.findByLogin(loginLibraire), fraisPort, exemplaireRepository.findById(idExemplaire).orElse(null));
        return afficherInfoLibraire(loginLibraire, model);
    }

    @GetMapping("{loginLibraire}/creerExemplaire")
    public String creerExemplaire(@PathVariable String loginLibraire, Model model) {
        ExemplaireDTO exemplaireDTO = new ExemplaireDTO();
        model.addAttribute("exemplaire", exemplaireDTO);
        model.addAttribute("allOuvrages", ouvrageRepository.findAll());
        model.addAttribute("etatExemplaire", EtatExemplaire.values());
        model.addAttribute("loginLibraire", loginLibraire);
        return "creerExemplaire";
    }

    @PostMapping("{loginLibraire}/creerExemplaire")
    public String enregistrerExemplaire(@ModelAttribute("exemplaire") @Valid ExemplaireDTO exemplaireDTO) {
        ouvrageService.saveExemplaireDto(exemplaireDTO);
        return "creerExemplaire";
    }

    @GetMapping("{loginLibraire}/creerOuvrage")
    public String creerOuvrage(@PathVariable String loginLibraire, Model model) {
        OuvrageDTO ouvrageDTO = new OuvrageDTO();
        model.addAttribute("ouvrage", ouvrageDTO);
        model.addAttribute("loginLibraire", loginLibraire);
        return "creerOuvrage";
    }

    @PostMapping("{loginLibraire}/creerOuvrage")
    public String enregistrerOuvrage(@ModelAttribute("ouvrage") @Valid OuvrageDTO ouvrageDTO) {
        ouvrageService.saveOuvrageDto(ouvrageDTO);
        return "creerOuvrage";
    }
}