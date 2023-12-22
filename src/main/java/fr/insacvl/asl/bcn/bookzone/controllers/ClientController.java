package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.AvisDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Avis;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.repositories.*;
import fr.insacvl.asl.bcn.bookzone.services.ClientService;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private ClientService clientService;

    public String getLoginClientName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Client getLoginClient() {
        return clientService.findByLogin(getLoginClientName());
    }

    @GetMapping("")
    public String afficherInfoLibraire(Model model) {
        Client c = getLoginClient();
        model.addAttribute("loginClient", c.getLogin());
        model.addAttribute("commandes", c.getCommandes());
        return "afficherInfoClient";
    }

    @GetMapping("exemplaire/{exemplaireId}")
    public String afficherInfoExemplaire(@PathVariable int exemplaireId, Model model) {
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        model.addAttribute("exemplaire", exemplaire);
        return "infoExemplaire";
    }

    @GetMapping("exemplaire/{exemplaireId}/creerAvis")
    public String creerAvisExemplaire(@PathVariable int exemplaireId, Model model) {
        model.addAttribute("avis", new AvisDTO());
        model.addAttribute("exemplaireId", exemplaireId);
        return "creerAvisExemplaire";
    }

    @PostMapping("exemplaire/{exemplaireId}/creerAvis")
    public String enregistrerAvisExemplaire(@PathVariable int exemplaireId, @ModelAttribute("avis") @Valid AvisDTO avisDTO, Model model) {
        Avis avis = ouvrageService.saveAvisDTO(avisDTO);
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        if(exemplaire != null){
            ouvrageService.addAvisExemplaire(avis, exemplaire);
        }
        else{
            System.out.println("L'exemplaire " + exemplaireId + " n'existe pas");
        }
        model.addAttribute("exemplaireId", exemplaireId);
        return "redirect:/client";
    }
}