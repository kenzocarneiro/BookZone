package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministrateurService {
    @Autowired
    UtilisateurService utilisateurService;

    @Transactional
    public void createAndSaveAdministrateur(String prenom, String nom, String mail, String login, String password, String adresse) {
        Utilisateur a = new Utilisateur();
        utilisateurService.configureAndSaveUtilisateur(a, prenom, nom, mail, login, password, adresse, "ROLE_ADMIN");
        System.out.println("Administrateur " + a + " cree");
    }
}
