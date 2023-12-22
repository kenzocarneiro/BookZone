package fr.insacvl.asl.bcn.bookzone.config;

import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoadingRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DataLoadingRunner.class);

    @Autowired
    private AdministrateurService administrateurService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private LibraireService libraireService;
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private AuteurService auteurService;
    @Autowired
    private CommandeService commandeService;

    @Transactional
    public void run (ApplicationArguments args) {
        Ouvrage o1 = ouvrageService.createOuvrage("Therese Raquin", "Flammarion", 328);
        Ouvrage o2 = ouvrageService.createOuvrage("Germinal", "Hachette", 2447);
        Ouvrage o3 = ouvrageService.createOuvrage("Tintin", "BDLand", 57);
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


        administrateurService.createAndSaveAdministrateur(
                "toto",
                "toto",
                "toto@gmail.com",
                "toto",
                "azerty",
                "750001 Paris - 5 rue Victor Hugo - Appartement 3"
        );

        libraireService.createAndSaveLibraireValide(
                "Super",
                "Book",
                "SuperBook@gmail.com",
                "sb",
                "sb",
                "18000 truc - 10 rue bidule"
        );

        clientService.createAndSaveClient(
                "John",
                "Doe",
                "john.doe@gmail.com",
                "john",
                "john",
                "18000 truc - 8 rue machin"
        );

        clientService.createAndSaveClient(
                "User",
                "Name",
                "user@user.com",
                "user",
                "user",
                "00000 UserVille - 1 rue user"
        );
        libraireService.createAndSaveLibraireValide(
                "Libraire",
                "Valide",
                "libraire@libraire.com",
                "libraire",
                "libraire",
                "00000 LibraireVille - 1 rue libraire"
        );
        libraireService.createAndSaveFuturLibraire(
                "Futur",
                "Libraire",
                "flibraire@flibraire.com",
                "flibraire",
                "flibraire",
                "00000 FuturLibraireVille - 1 rue futurLibraire"
        );
        administrateurService.createAndSaveAdministrateur(
                "Admin",
                "Istrateur",
                "admin@admin.com",
                "admin",
                "admin",
                "00000 AdminVille - 1 rue admin"
        );

        ouvrageService.addAuteurToOuvrage(p, o1);
        ouvrageService.addAuteurToOuvrage(p, o2);
        ouvrageService.addAuteurToOuvrage(p2, o3);
        Exemplaire e1 = ouvrageService.createExemplaire(EtatExemplaire.MOYEN, libraireService.findByLogin("sb"), EtatCommande.EXPEDIE);
        Exemplaire e2 = ouvrageService.createExemplaire(EtatExemplaire.BON, libraireService.findByLogin("sb"), EtatCommande.EXPEDIE);
        Exemplaire e3 = ouvrageService.createExemplaire(EtatExemplaire.MAUVAIS, libraireService.findByLogin("sb"));
        Exemplaire e4 = ouvrageService.createExemplaire(EtatExemplaire.TRES_BON, libraireService.findByLogin("sb"));

        ouvrageService.addExemplaireToOuvrage(e1, o1);
        ouvrageService.addExemplaireToOuvrage(e2, o2);
        ouvrageService.addExemplaireToOuvrage(e3, o2);
        ouvrageService.addExemplaireToOuvrage(e4, o3);

        ouvrageService.addAvisExemplaire(avis1, e1);
        ouvrageService.addAvisExemplaire(avis2, e2);


        Client john = clientService.findByLogin("john");
        Commande c1 = commandeService.createCommande(john, "blabla", LocalDate.now());
        Commande c2 = commandeService.createCommande(john, "bloblo", LocalDate.of(2023, 12, 10));
        Commande c3 = commandeService.createCommande(john, "blublu", LocalDate.of(2023, 9, 10));
        commandeService.addExemplaireDansCommande(e1, c1);
        commandeService.addExemplaireDansCommande(e1, c1); // test redondance
        commandeService.addExemplaireDansCommande(e2, c2);
        commandeService.addExemplaireDansCommande(e4, c2);
        commandeService.addExemplaireDansCommande(e3, c3);

        log.info("Finished Loading Entities");
    }
}
