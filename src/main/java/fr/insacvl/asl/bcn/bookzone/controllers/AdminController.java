package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private Facade facade;
    @RequestMapping("")
    public String login(Model model) {
        // TODO : faire le login
        return("hello");
    }

    @RequestMapping("commandesDuMois")
    public String voirCommandeDuMois(Model model) {
        List<Commande> commandesDuMois = facade.voirCommandeduMois();
        System.out.println("----------------------------");
        System.out.println("[admin] Commandes du mois : ");
        for(Commande cmd : commandesDuMois){
            System.out.println(cmd);
        }
        model.addAttribute("commandesDuMois", commandesDuMois);
        return("commandesDuMois");
    }
}