package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Adresse;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import fr.insacvl.asl.bcn.bookzone.repositories.AdresseRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    public void saveUtilisateurDto(UtilisateurDTO utilisateurDTO) {
        Utilisateur u;
        if (utilisateurDTO.isLibraire()) {
            u = new Libraire();
            u.setRole("ROLE_LIBRAIRE");
        }
        else {
            u = new Client();
            u.setRole("ROLE_USER");
        }
        u.setPrenom(utilisateurDTO.getPrenom());
        u.setNom(utilisateurDTO.getNom());
        u.setMail(utilisateurDTO.getMail());
        u.setLogin(utilisateurDTO.getLogin());
        u.setPassword(passwordEncoder.encode(utilisateurDTO.getPassword()));
        utilisateurRepository.save(u);
    }

    @Transactional
    public void configureAndSaveUtilisateur(Utilisateur u, String prenom, String nom, String mail, String login, String password, String role) {
        u.setPrenom(prenom);
        u.setNom(nom);
        u.setMail(mail);
        u.setLogin(login);
        u.setPassword(passwordEncoder.encode(password));
        u.setRole(role);
        utilisateurRepository.save(u);
    }

    @Transactional
    public void createAndSaveClient(String prenom, String nom, String mail, String login, String password) {
        Client c = new Client();
        configureAndSaveUtilisateur(c, prenom, nom, mail, login, password, "ROLE_USER");
        System.out.println("Client " + c + " cree");
    }

    @Transactional
    public void createAndSaveLibraire(String prenom, String nom, String mail, String login, String password) {
        Libraire l = new Libraire();
        configureAndSaveUtilisateur(l, prenom, nom, mail, login, password, "ROLE_LIBRAIRE");
        System.out.println("Libraire " + l + " cree");
    }

    @Transactional
    public void createAndSaveAdministrateur(String prenom, String nom, String mail, String login, String password) {
        Utilisateur a = new Utilisateur();
        configureAndSaveUtilisateur(a, prenom, nom, mail, login, password, "ROLE_ADMIN");
        System.out.println("Administrateur " + a + " cree");
    }

    @Transactional
    public void associateAdresseUtilisateur(Adresse a, Utilisateur u) {
        u.setAdresse(a);
        adresseRepository.save(a);
        utilisateurRepository.save(u);
        System.out.println("Adresse " + a.getIdAdresse() +  " associee à " + u.getLogin());
    }

    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }
}
