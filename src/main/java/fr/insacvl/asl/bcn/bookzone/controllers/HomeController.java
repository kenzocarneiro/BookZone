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
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private OuvrageService ouvrageService;

    @GetMapping("")
    //page d'accueil
    public String index(Model model) {
        RechercheDTO rechercheDTO = new RechercheDTO();
        List<Exemplaire> exemplaires = ouvrageService.getExemplairesEnVente();

        model.addAttribute("recherche", rechercheDTO);
        model.addAttribute("categories", CategorieEnum.values());
        model.addAttribute("exemplaires", exemplaires);
        return("index");
    }

    @PostMapping("")
    public String recherche(Model model, @ModelAttribute("recherche") @Valid RechercheDTO rechercheDTO) {

        List<CategorieEnum> categories = rechercheDTO.getCategories();
        String contenu = rechercheDTO.getContenu();

        List<Exemplaire> exemplaires = ouvrageService.getExemplairesEnVente();

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
}