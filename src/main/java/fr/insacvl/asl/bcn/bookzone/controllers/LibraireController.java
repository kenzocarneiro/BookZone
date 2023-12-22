package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.ExemplaireDTO;
import fr.insacvl.asl.bcn.bookzone.dtos.OuvrageDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.repositories.*;
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
    @Autowired
    private AuteurRepository auteurRepository;

    @GetMapping("{loginLibraire}")
    public String afficherInfoLibraire(@PathVariable String loginLibraire, Model model) {
        Set<Exemplaire> exemplaires = libraireService.getExemplairesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Ouvrage> ouvrages = libraireService.getOuvragesLibraire(libraireRepository.findByLogin(loginLibraire));
        Set<Exemplaire> exemplairesCommandes = libraireService.getExemplairesCommandesDuLibraire(libraireRepository.findByLogin(loginLibraire));
        double noteMoyenne = libraireService.getNoteMoyenne(libraireRepository.findByLogin(loginLibraire));
       model.addAttribute("loginLibraire", loginLibraire);
       model.addAttribute("exemplaires", exemplaires);
       model.addAttribute("ouvrages", ouvrages);
       model.addAttribute("exemplairesCommandes", exemplairesCommandes);
       model.addAttribute("noteMoyenne", noteMoyenne);
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
    public String enregistrerExemplaire(@PathVariable String loginLibraire, @ModelAttribute("exemplaire") @Valid ExemplaireDTO exemplaireDTO, Model model) {
        ouvrageService.saveExemplaireDto(exemplaireDTO);
        model.addAttribute("loginLibraire", loginLibraire);
        return "creerExemplaire";
    }

    @GetMapping("{loginLibraire}/creerOuvrage")
    public String creerOuvrage(@PathVariable String loginLibraire, Model model) {
        OuvrageDTO ouvrageDTO = new OuvrageDTO();
        model.addAttribute("ouvrage", ouvrageDTO);
        model.addAttribute("auteurs", auteurRepository.findAll());
        model.addAttribute("loginLibraire", loginLibraire);
        return "creerOuvrage";
    }

    @PostMapping("{loginLibraire}/creerOuvrage")
    public String enregistrerOuvrage(@PathVariable String loginLibraire, @ModelAttribute("ouvrage") @Valid OuvrageDTO ouvrageDTO, Model model) {
        ouvrageService.saveOuvrageDto(ouvrageDTO);
        model.addAttribute("loginLibraire", loginLibraire);
        return "creerOuvrage";
    }
}