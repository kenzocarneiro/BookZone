package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.ExemplaireDTO;
import fr.insacvl.asl.bcn.bookzone.dtos.OuvrageDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.AuteurService;
import fr.insacvl.asl.bcn.bookzone.services.LibraireService;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping("/libraire")
public class LibraireController {

    @Autowired
    private LibraireService libraireService;
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private AuteurService auteurService;

    public String getLoginLibraireName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Libraire getLoginLibraire() {
        return libraireService.findByLogin(getLoginLibraireName());
    }

    @GetMapping("")
    public String afficherInfoLibraire(Model model) {
        Libraire l = getLoginLibraire();
        Set<Exemplaire> exemplaires = libraireService.getExemplairesLibraire(l);
        Set<Ouvrage> ouvrages = libraireService.getOuvragesLibraire(l);
        Set<Exemplaire> exemplairesCommandes = libraireService.getExemplairesCommandesDuLibraire(l);
        double noteMoyenne = libraireService.getNoteMoyenne(l);
        model.addAttribute("loginLibraire", l.getLogin());
        model.addAttribute("exemplaires", exemplaires);
        model.addAttribute("ouvrages", ouvrages);
        model.addAttribute("exemplairesCommandes", exemplairesCommandes);
        model.addAttribute("noteMoyenne", noteMoyenne);
        model.addAttribute("etatCommande", EtatCommande.values());
        return "afficherInfoLibraire";
    }

    @PostMapping("setPrixVente/{idExemplaire}")
    public String setPrixVente(@PathVariable int idExemplaire, @RequestParam float prixVente) {
        Libraire l = getLoginLibraire();
        libraireService.setPrixVente(l, prixVente, ouvrageService.findExemplaireById(idExemplaire));
        return "redirect:/libraire";
    }

    @PostMapping("setFraisPort/{idExemplaire}")
    public String setFraisPort(@PathVariable int idExemplaire, @RequestParam float fraisPort) {
        Libraire l = getLoginLibraire();
        libraireService.setFraisPort(l, fraisPort, ouvrageService.findExemplaireById(idExemplaire));
        return "redirect:/libraire";
    }

    @GetMapping("creerExemplaire")
    public String creerExemplaire(Model model) {
        ExemplaireDTO exemplaireDTO = new ExemplaireDTO();
        model.addAttribute("exemplaire", exemplaireDTO);
        model.addAttribute("allOuvrages", ouvrageService.findAllOuvrages());
        model.addAttribute("etatExemplaire", EtatExemplaire.values());
        return "creerExemplaire";
    }

    @PostMapping("creerExemplaire")
    public String enregistrerExemplaire(@ModelAttribute("exemplaire") @Valid ExemplaireDTO exemplaireDTO) {
        Libraire l = getLoginLibraire();
        ouvrageService.saveExemplaireDto(exemplaireDTO, l);
        return "redirect:/libraire";
    }

    @GetMapping("creerOuvrage")
    public String creerOuvrage(Model model) {
        OuvrageDTO ouvrageDTO = new OuvrageDTO();
        model.addAttribute("ouvrage", ouvrageDTO);
        model.addAttribute("auteurs", auteurService.findAll());
        return "creerOuvrage";
    }

    @PostMapping("creerOuvrage")
    public String enregistrerOuvrage(@ModelAttribute("ouvrage") @Valid OuvrageDTO ouvrageDTO) {
        ouvrageService.saveOuvrageDto(ouvrageDTO);
        return "redirect:/libraire";
    }

    @PostMapping("exemplaire/{exemplaireId}/updateEtatCommande")
    public String updateEtatCommandeExemplaire(@PathVariable int exemplaireId, @RequestParam EtatCommande etatCommande) {
        Exemplaire e = ouvrageService.findExemplaireById(exemplaireId);
        if(e != null){
            e.setEtatCommande(etatCommande);
            ouvrageService.saveExemplaire(e);
        }
        else{
            System.out.println("L'exemplaire" + exemplaireId + "n'existe pas");
        }
        return "redirect:/libraire";
    }
}