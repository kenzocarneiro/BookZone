package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private AuteurService auteurService;

    @RequestMapping("")
    public String hello() {
        return "index";
    }

    @RequestMapping("test")
    public String test() {

        Ouvrage o1 = ouvrageService.createOuvrage("Therese Raquin", "Flammarion", 328);
        Ouvrage o2 = ouvrageService.createOuvrage("Germinal", "Hachette", 2447);
        Ouvrage o3 = ouvrageService.createOuvrage("Tintin", "BDLand", 57);
        Adresse a = utilisateurService.createAdresse("Victor Hugo", "Paris", 75, "France", "appartement 3");
        Avis avis1 = ouvrageService.createAvis(4, "Tres bon livre !");
        Avis avis2 = ouvrageService.createAvis(1, "C'etait nul ...");
        Auteur p = auteurService.createAuteur("Emile", "Zola");
        Auteur p2 = auteurService.createAuteur("Georges", "Remi");

        ouvrageService.addCategorieToOuvrage(CategorieEnum.REALISTE, o1);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.FICTION, o1);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.HISTORIQUE, o2);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.REALISTE, o2);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.AVENTURE, o3);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.FICTION, o3);


        utilisateurService.createAndSaveAdministrateur(
                "toto",
                "toto",
                "toto@gmail.com",
                "toto",
                "azerty"
        );
        utilisateurService.associateAdresseUtilisateur(a, utilisateurService.findByLogin("toto"));

        utilisateurService.createAndSaveLibraire(
                "Super",
                "Book",
                "SuperBook@gmail.com",
                "sb",
                "123"
        );

        utilisateurService.createAndSaveClient(
                "John",
                "Doe",
                "john.doe@gmail.com",
                "john",
                "jojo456"
        );

        utilisateurService.createAndSaveClient(
                "User",
                "Name",
                "user@user.com",
                "user",
                "user"
        );
        utilisateurService.createAndSaveLibraire(
                "Libraire",
                "Libraire",
                "libraire@libraire.com",
                "libraire",
                "libraire"
        );
        utilisateurService.createAndSaveAdministrateur(
                "Admin",
                "Istrateur",
                "admin@admin.com",
                "admin",
                "admin"
        );

        ouvrageService.addAuteurToOuvrage(p, o1);
        ouvrageService.addAuteurToOuvrage(p, o2);
        ouvrageService.addAuteurToOuvrage(p2, o3);
        Exemplaire e1 = ouvrageService.createExemplaire(EtatExemplaire.MOYEN, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e2 = ouvrageService.createExemplaire(EtatExemplaire.BON, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e3 = ouvrageService.createExemplaire(EtatExemplaire.MAUVAIS, (Libraire)utilisateurService.findByLogin("sb"));

        ouvrageService.addExemplaireToOuvrage(e1, o1);
        ouvrageService.addExemplaireToOuvrage(e2, o2);
        ouvrageService.addExemplaireToOuvrage(e3, o2);

        ouvrageService.addAvisExemplaire(avis1, e1);
        ouvrageService.addAvisExemplaire(avis2, e2);

        Commande c1 = commandeService.createCommande(EtatCommande.EXPEDIE, "blabla", LocalDate.now());
        Commande c2 = commandeService.createCommande(EtatCommande.ANNULE, "bloblo", LocalDate.of(2023, 12, 10));
        Commande c3 = commandeService.createCommande(EtatCommande.EXPEDIE, "blublu", LocalDate.of(2023, 9, 10));
        commandeService.addExemplaireDansCommande(e1, c1);
        commandeService.addExemplaireDansCommande(e1, c1); // test redondance
        commandeService.addExemplaireDansCommande(e2, c2);
        commandeService.addExemplaireDansCommande(e3, c3);

        commandeService.addCommmandetoClient(c1, (Client)utilisateurService.findByLogin("john"));
        commandeService.addCommmandetoClient(c2, (Client)utilisateurService.findByLogin("john"));
        commandeService.addCommmandetoClient(c3, (Client)utilisateurService.findByLogin("john"));
        return "index";
    }


    @RequestMapping("/welcome")
    public String hello_login() {
        return "welcome";
    }
}