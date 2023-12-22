package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.dtos.RechercheDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("")
    //page d'accueil
    public String index(Model model) {
        RechercheDTO rechercheDTO = new RechercheDTO();
        List<Exemplaire> exemplaires = ouvrageService.getExemplaires();

        model.addAttribute("recherche", rechercheDTO);
        model.addAttribute("categories", CategorieEnum.values());
        model.addAttribute("exemplaires", exemplaires);
        return("index");
    }

    @PostMapping("")
    public String recherche(Model model, @ModelAttribute("recherche") @Valid RechercheDTO rechercheDTO) {

        List<CategorieEnum> categories = rechercheDTO.getCategories();
        String contenu = rechercheDTO.getContenu();

        List<Exemplaire> exemplaires = ouvrageService.getExemplaires();

        if (categories != null && !categories.isEmpty()) {
            // pour chaque exemplaire, on regarde si il a une categorie qui correspond à une des categories de la recherche
            exemplaires.removeIf(exemplaire -> {
                for (CategorieEnum categorie : categories) {
                    if (exemplaire.getOuvrage().getCategories().contains(categorie)) {
                        return false;
                    }
                }
                return true;
            });
        }


        // Test pour l'instant seulement avec catégorie
        if (contenu != null && !contenu.isEmpty()) {
            String lowercaseContenu = contenu.toLowerCase();
            // pour chaque exemplaire, on regarde si le contenu de la recherche est dans le titre ou dans le nom de l'auteur
            exemplaires.removeIf(exemplaire -> {
                Ouvrage ouvrage = exemplaire.getOuvrage();
                if (ouvrage.getTitre().toLowerCase().contains(lowercaseContenu)) {
                    return false;
                }
                for (Personne auteur : ouvrage.getAuteurs()) {
                    if (auteur.getNom().toLowerCase().contains(lowercaseContenu) || auteur.getPrenom().toLowerCase().contains(lowercaseContenu)) {
                        return false;
                    }
                }
                return true;
            });
        }

        // model.addAttribute("recherche", rechercheDTO);
        model.addAttribute("categories", CategorieEnum.values());
        model.addAttribute("exemplaires", exemplaires);
        return("index");
    }


    @RequestMapping("test")
    public String test() {

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


        utilisateurService.createAndSaveAdministrateur(
                "toto",
                "toto",
                "toto@gmail.com",
                "toto",
                "azerty",
                "750001 Paris - 5 rue Victor Hugo - Appartement 3"
        );

        utilisateurService.createAndSaveLibraireValide(
                "Super",
                "Book",
                "SuperBook@gmail.com",
                "sb",
                "sb",
                "18000 truc - 10 rue bidule"
        );

        utilisateurService.createAndSaveClient(
                "John",
                "Doe",
                "john.doe@gmail.com",
                "john",
                "john",
                "18000 truc - 8 rue machin"
        );

        utilisateurService.createAndSaveClient(
                "User",
                "Name",
                "user@user.com",
                "user",
                "user",
                "00000 UserVille - 1 rue user"
        );
        utilisateurService.createAndSaveLibraireValide(
                "Libraire",
                "Valide",
                "libraire@libraire.com",
                "libraire",
                "libraire",
                "00000 LibraireVille - 1 rue libraire"
        );
        utilisateurService.createAndSaveFuturLibraire(
                "Futur",
                "Libraire",
                "flibraire@flibraire.com",
                "flibraire",
                "flibraire",
                "00000 FuturLibraireVille - 1 rue futurLibraire"
        );
        utilisateurService.createAndSaveAdministrateur(
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
        Exemplaire e1 = ouvrageService.createExemplaire(EtatExemplaire.MOYEN, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e2 = ouvrageService.createExemplaire(EtatExemplaire.BON, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e3 = ouvrageService.createExemplaire(EtatExemplaire.MAUVAIS, (Libraire)utilisateurService.findByLogin("sb"));
        Exemplaire e4 = ouvrageService.createExemplaire(EtatExemplaire.TRES_BON, (Libraire)utilisateurService.findByLogin("sb"));

        ouvrageService.addExemplaireToOuvrage(e1, o1);
        ouvrageService.addExemplaireToOuvrage(e2, o2);
        ouvrageService.addExemplaireToOuvrage(e3, o2);
        ouvrageService.addExemplaireToOuvrage(e4, o3);

        ouvrageService.addAvisExemplaire(avis1, e1);
        ouvrageService.addAvisExemplaire(avis2, e2);

        Commande c1 = commandeService.createCommande("blabla", LocalDate.now());
        Commande c2 = commandeService.createCommande("bloblo", LocalDate.of(2023, 12, 10));
        Commande c3 = commandeService.createCommande("blublu", LocalDate.of(2023, 9, 10));
        commandeService.addExemplaireDansCommande(e1, c1);
        commandeService.addExemplaireDansCommande(e1, c1); // test redondance
        commandeService.addExemplaireDansCommande(e2, c2);
        commandeService.addExemplaireDansCommande(e4, c2);
        commandeService.addExemplaireDansCommande(e3, c3);

        commandeService.addCommmandetoClient(c1, (Client)utilisateurService.findByLogin("john"));
        commandeService.addCommmandetoClient(c2, (Client)utilisateurService.findByLogin("john"));
        commandeService.addCommmandetoClient(c3, (Client)utilisateurService.findByLogin("john"));
        return "redirect:/";
    }
}