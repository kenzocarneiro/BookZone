package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.Facade;
import fr.insacvl.asl.bcn.bookzone.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class HelloWorldController {

    @Autowired
    private Facade facade;

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping("")
    public String hello() {
        return "hello";
    }

    @RequestMapping("test")
    public String test() {

        Ouvrage o1 = facade.createOuvrage("Therese Raquin", "Flammarion", 328);
        Ouvrage o2 = facade.createOuvrage("Germinal", "Hachette", 2447);
        Adresse a = facade.createAdresse("Victor Hugo", "Paris", 75, "France", "appartement 3");
        Avis avis1 = facade.createAvis(4, "Tres bon livre !");
        Avis avis2 = facade.createAvis(1, "C'etait nul ...");
        Personne p = facade.createPersonne("Emile", "Zola");

        facade.addCategorieToOuvrage(CategorieEnum.REALISTE, o1);
        facade.addCategorieToOuvrage(CategorieEnum.FICTION, o1);
        facade.addCategorieToOuvrage(CategorieEnum.HISTORIQUE, o2);
        facade.addCategorieToOuvrage(CategorieEnum.REALISTE, o2);

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

        facade.addAuteurToOuvrage(p, o1);
        facade.addAuteurToOuvrage(p, o2);
        Exemplaire e1 = facade.createExemplaire(EtatExemplaire.MOYEN, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e2 = facade.createExemplaire(EtatExemplaire.BON, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e3 = facade.createExemplaire(EtatExemplaire.MAUVAIS, (Libraire)utilisateurService.findByLogin("sb"));

        facade.addExemplaireToOuvrage(e1, o1);
        facade.addExemplaireToOuvrage(e2, o2);
        facade.addExemplaireToOuvrage(e3, o2);

        facade.addAvisExemplaire(avis1, e1);
        facade.addAvisExemplaire(avis2, e2);

        Commande c1 = facade.createCommande(EtatCommande.EXPEDIE, "blabla", LocalDate.now());
        Commande c2 = facade.createCommande(EtatCommande.ANNULE, "bloblo", LocalDate.of(2023, 12, 10));
        Commande c3 = facade.createCommande(EtatCommande.EXPEDIE, "blublu", LocalDate.of(2023, 9, 10));
        facade.addExemplaireDansCommande(e1, c1);
        facade.addExemplaireDansCommande(e1, c1); // test redondance
        facade.addExemplaireDansCommande(e2, c2);
        facade.addExemplaireDansCommande(e3, c3);

        facade.addCommmandetoClient(c1, (Client)utilisateurService.findByLogin("john"));
        facade.addCommmandetoClient(c2, (Client)utilisateurService.findByLogin("john"));
        facade.addCommmandetoClient(c3, (Client)utilisateurService.findByLogin("john"));
        return "hello";
    }


    @RequestMapping("/welcome")
    public String hello_login() {
        return "welcome";
    }
}