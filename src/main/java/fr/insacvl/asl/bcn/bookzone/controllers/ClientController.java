package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.AvisDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Avis;
import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.repositories.*;
import fr.insacvl.asl.bcn.bookzone.services.OuvrageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private OuvrageService ouvrageService;

    @GetMapping("{loginClient}")
    public String afficherInfoLibraire(@PathVariable String loginClient, Model model) {
       model.addAttribute("loginClient", loginClient);
       model.addAttribute("commandes", clientRepository.findByLogin(loginClient).getCommandes());
       return("afficherInfoClient");
    }

    @GetMapping("{loginClient}/exemplaire/{exemplaireId}")
    public String afficherInfoExemplaire(@PathVariable String loginClient, @PathVariable int exemplaireId, Model model) {
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("loginClient", loginClient);
        return("infoExemplaire");
    }

    @GetMapping("{loginClient}/exemplaire/{exemplaireId}/creerAvis")
    public String creerAvisExemplaire(@PathVariable String loginClient, @PathVariable int exemplaireId, Model model) {
        model.addAttribute("avis", new AvisDTO());
        model.addAttribute("loginClient", loginClient);
        model.addAttribute("exemplaireId", exemplaireId);
        return("creerAvisExemplaire");
    }

    @PostMapping("{loginClient}/exemplaire/{exemplaireId}/creerAvis")
    public String enregistrerAvisExemplaire(@PathVariable String loginClient, @PathVariable int exemplaireId, @ModelAttribute("avis") @Valid AvisDTO avisDTO, Model model) {
        Avis avis = ouvrageService.saveAvisDTO(avisDTO);
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        if(exemplaire != null){
            ouvrageService.addAvisExemplaire(avis, exemplaire);
        }
        else{
            System.out.println("L'exemplaire " + exemplaireId + " n'existe pas");
        }
        model.addAttribute("loginClient", loginClient);
        model.addAttribute("exemplaireId", exemplaireId);
        return("creerAvisExemplaire");
    }
}