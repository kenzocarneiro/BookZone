package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class HelloWorldController {
    @Autowired
    private Facade facade;
    @RequestMapping("")
    public String hello(Model model) {
        return("hello");
    }

    @RequestMapping("test")
    public String test(Model model) {
        Adresse a = facade.createAdresse("Victor Hugo", "Paris", 75, "France", "appartement 3");
        Avis avis1 = facade.createAvis(4, "Tres bon livre !");
        Avis avis2 = facade.createAvis(1, "C'etait nul ...");

        facade.createPersonne("Bob", "Leponge");
        facade.createUtilisateur("toto@gmail.com", "toto", "azerty");
        facade.createAdministrateur(facade.getUtilisateur("toto"));
        facade.associateAdresseUtilisateur(a, facade.getUtilisateur("toto"));
        facade.createLibraire("SuperBook", "sb", "123");
        facade.createClient("john.doe@gmail.com", "john", "jojo456");

        Exemplaire e1 = facade.createExemplaire(EtatExemplaire.MOYEN, (Libraire)facade.getUtilisateur("sb"), avis1);
        Exemplaire e2 = facade.createExemplaire(EtatExemplaire.BON, (Libraire)facade.getUtilisateur("sb"), avis2);

        Commande c = facade.createCommande(EtatCommande.EXPEDIE, "blabla", LocalDate.now());
        facade.addExemplaireDansCommande(e1, c);
        facade.addExemplaireDansCommande(e1, c); // test redondance
        facade.addExemplaireDansCommande(e2, c);

        facade.addCommmandetoClient(c, (Client)facade.getUtilisateur("john"));
        return("hello");
    }
}