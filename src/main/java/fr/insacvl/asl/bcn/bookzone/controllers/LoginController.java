package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import fr.insacvl.asl.bcn.bookzone.services.ClientService;
import fr.insacvl.asl.bcn.bookzone.services.PanierService;
import fr.insacvl.asl.bcn.bookzone.services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("sessionPanierId")
public class LoginController {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    ClientService clientService;
    @Autowired
    PanierService panierService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        model.addAttribute("user", utilisateurDTO);
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid UtilisateurDTO utilisateurDTO) {
        utilisateurService.saveUtilisateurDto(utilisateurDTO);
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcomeInformations(@ModelAttribute("sessionPanierId") Integer sessionPanierId, Model model) {
        Utilisateur utilisateur = utilisateurService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", utilisateurService.utilisateurToDto(utilisateur));

        if (utilisateur.getRole().equals("ROLE_CLIENT")) {
            Client c = (Client) utilisateur;
            if (c.getPanier() == null) {
                clientService.addPanierFromId(c, sessionPanierId);
                Integer newSessionPanierId = panierService.createPanier().getIdPanier();
                model.addAttribute("sessionPanierId", newSessionPanierId);
            } else {
                panierService.deletePanierFromId(sessionPanierId);
                Integer newSessionPanierId = panierService.createPanier().getIdPanier();
                model.addAttribute("sessionPanierId", newSessionPanierId);
            }
        } else {
            panierService.deletePanierFromId(sessionPanierId);
            Integer newSessionPanierId = panierService.createPanier().getIdPanier();
            model.addAttribute("sessionPanierId", newSessionPanierId);
        }

        return "welcome";
    }
}
