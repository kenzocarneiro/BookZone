package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CommandeService commandeService;

    @RequestMapping("commandesDuMois")
    public String voirCommandeDuMois(Model model) {
        List<Commande> commandesDuMois = commandeService.voirCommandeduMois();
        model.addAttribute("commandesDuMois", commandesDuMois);
        return("commandesDuMois");
    }
}