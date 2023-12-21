package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("{loginClient}")
    public String afficherInfoLibraire(@PathVariable String loginClient, Model model) {
       model.addAttribute("loginClient", loginClient);
       model.addAttribute("commandes", clientRepository.findByLogin(loginClient).getCommandes());
       return("afficherInfoClient");
    }
}