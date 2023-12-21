package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private PersonneService personneService;

    @RequestMapping("")
    //page d'accueil
    public String index(Model model) {
        List<Exemplaire> exemplaires = ouvrageService.getExemplaires();
        model.addAttribute("exemplaires", exemplaires);
        System.out.println("exemplaires : " + exemplaires);
        // Set<Ouvrage> ouvrages = ouvrageService.getOuvrages();
        // model.addAttribute("ouvrages", ouvrages);
        return("index");
    }

    @RequestMapping("test")
    public String test() {

        Ouvrage o1 = ouvrageService.createOuvrage("Therese Raquin", "Flammarion", 328);
        Ouvrage o2 = ouvrageService.createOuvrage("Germinal", "Hachette", 2447);
        Adresse a = utilisateurService.createAdresse("Victor Hugo", "Paris", 75, "France", "appartement 3");
        Avis avis1 = ouvrageService.createAvis(4, "Tres bon livre !");
        Avis avis2 = ouvrageService.createAvis(1, "C'etait nul ...");
        Personne p = personneService.createPersonne("Emile", "Zola");

        ouvrageService.addCategorieToOuvrage(CategorieEnum.REALISTE, o1);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.FICTION, o1);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.HISTORIQUE, o2);
        ouvrageService.addCategorieToOuvrage(CategorieEnum.REALISTE, o2);

        utilisateurService.createAndSaveAdministrateur(
                "toto",
                "toto",
                "toto@gmail.com",
                "toto",
                "azerty"
        );
        utilisateurService.associateAdresseUtilisateur(a, utilisateurService.findByLogin("toto"));

        utilisateurService.createAndSaveLibraireValide(
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
        utilisateurService.createAndSaveLibraireValide(
                "Libraire",
                "Valide",
                "libraire@libraire.com",
                "libraire",
                "libraire"
        );
        utilisateurService.createAndSaveFuturLibraire(
                "Futur",
                "Libraire",
                "flibraire@flibraire.com",
                "flibraire",
                "flibraire"
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