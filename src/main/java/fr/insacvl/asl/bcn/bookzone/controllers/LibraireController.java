package fr.insacvl.asl.bcn.bookzone.controllers;

import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.services.ExemplaireRepository;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/libraire")
public class LibraireController {

    @Autowired
    private Facade facade;

    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @RequestMapping("")
    public String login(Model model) {
        // TODO : faire le login avec Spring Security
        return("hello");
    }

    @RequestMapping("setPrixVente")
    public String setPrixVente(Model model) {
        facade.setPrixVente((Libraire)facade.getUtilisateur("sb"), 12, exemplaireRepository.findById(1).orElse(null));
        facade.setPrixVente((Libraire)facade.getUtilisateur("sb"), 5, exemplaireRepository.findById(2).orElse(null));
        facade.setPrixVente((Libraire)facade.getUtilisateur("sb"), 10, exemplaireRepository.findById(3).orElse(null));
        // id=50 n'existe pas, ne fait rien (ne doit pas faire crash le serveur)
        facade.setPrixVente((Libraire)facade.getUtilisateur("sb"), 2, exemplaireRepository.findById(50).orElse(null));

        return("hello");
    }

    @RequestMapping("setFraisPort")
    public String setFraisPort(Model model) {
        facade.setFraisPort((Libraire)facade.getUtilisateur("sb"), 2.50F, exemplaireRepository.findById(1).orElse(null));
        facade.setFraisPort((Libraire)facade.getUtilisateur("sb"), 1.75F, exemplaireRepository.findById(2).orElse(null));
        facade.setFraisPort((Libraire)facade.getUtilisateur("sb"), 1.10F, exemplaireRepository.findById(3).orElse(null));
        // id=50 n'existe pas, ne fait rien (ne doit pas faire crash le serveur)
        facade.setFraisPort((Libraire)facade.getUtilisateur("sb"), 5.0F, exemplaireRepository.findById(50).orElse(null));

        return("hello");
    }
}