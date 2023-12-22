package fr.insacvl.asl.bcn.bookzone.controllers;
import fr.insacvl.asl.bcn.bookzone.dtos.RechercheDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.services.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes("sessionPanierId")
public class HomeController {
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PanierService panierService;

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public String getLoginName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

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

    @ModelAttribute("sessionPanierId")
    public Integer getSessionPanier() {
        Panier p = panierService.createPanier();
        return p.getIdPanier();
    }

    public Panier getCurrentPanier(Integer sessionPanierId) {
        Panier sessionPanier = panierService.findById(sessionPanierId);
        if (isLoggedIn()) {
            Client client = clientService.findByLogin(getLoginName());
            return client.getPanier();
        } else {
            return sessionPanier;
        }
    }

    @GetMapping("/panier")
    public String afficherPanier(@ModelAttribute("sessionPanierId") Integer sessionPanierId, Model model) {
        Panier panier = getCurrentPanier(sessionPanierId);
        model.addAttribute("panier", panier);
        return "panier";
    }

    @GetMapping("/panier/ajouter/{idExemplaire}")
    public String ajouterPanier(@ModelAttribute("sessionPanierId") Integer sessionPanierId, @PathVariable Integer idExemplaire, Model model) {
        Panier panier = getCurrentPanier(sessionPanierId);
        panierService.addExemplaireFromId(panier.getIdPanier(), idExemplaire);
        model.addAttribute("panier", panier);
        return "panier";
    }

    @GetMapping("/panier/retirer/{idExemplaire}")
    public String retirerPanier(@ModelAttribute("sessionPanierId") Integer sessionPanierId, @PathVariable Integer idExemplaire, Model model) {
        Panier panier = getCurrentPanier(sessionPanierId);
        panierService.removeExemplaireFromId(panier.getIdPanier(), idExemplaire);
        model.addAttribute("panier", panier);
        return "panier";
    }

    @GetMapping("/panier/acheter")
    public String acheter(@ModelAttribute("sessionPanierId") Integer sessionPanierId) {
        clientService.acheterPanier(clientService.findByLogin(getLoginName()));
        return "redirect:/panier";
    }

}