package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import fr.insacvl.asl.bcn.bookzone.repositories.LibraireRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private LibraireRepository libraireRepository;

    public UtilisateurDTO utilisateurToDto(Utilisateur u) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setPrenom(u.getPrenom());
        utilisateurDTO.setNom(u.getNom());
        utilisateurDTO.setMail(u.getMail());
        utilisateurDTO.setLogin(u.getLogin());
        utilisateurDTO.setPassword("");
        utilisateurDTO.setRole(u.getRole());
        utilisateurDTO.setAdresse(u.getAdresse());
        return utilisateurDTO;
    }

    @Transactional
    public void saveUtilisateurDto(UtilisateurDTO utilisateurDTO) {
        Utilisateur u;
        if (utilisateurDTO.isLibraire()) {
            Libraire l = new Libraire();
            l.setRole("ROLE_FUTUR_LIBRAIRE");
            u = l;
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
        u.setAdresse(utilisateurDTO.getAdresse());
        utilisateurRepository.save(u);
    }

    @Transactional
    public void configureAndSaveUtilisateur(Utilisateur u, String prenom, String nom, String mail, String login, String password, String adresse, String role) {
        u.setPrenom(prenom);
        u.setNom(nom);
        u.setMail(mail);
        u.setLogin(login);
        u.setPassword(passwordEncoder.encode(password));
        u.setAdresse(adresse);
        u.setRole(role);
        utilisateurRepository.save(u);
    }

    @Transactional
    public void createAndSaveClient(String prenom, String nom, String mail, String login, String password, String adresse) {
        Client c = new Client();
        configureAndSaveUtilisateur(c, prenom, nom, mail, login, password, adresse, "ROLE_USER");
        System.out.println("Client " + c + " cree");
    }

    @Transactional
    public void createAndSaveFuturLibraire(String prenom, String nom, String mail, String login, String password, String adresse) {
        Libraire l = new Libraire();
        configureAndSaveUtilisateur(l, prenom, nom, mail, login, password, adresse, "ROLE_FUTUR_LIBRAIRE");
        System.out.println("Libraire " + l + " cree");
    }

    @Transactional
    public void createAndSaveLibraireValide(String prenom, String nom, String mail, String login, String password, String adresse) {
        Libraire l = new Libraire();
        configureAndSaveUtilisateur(l, prenom, nom, mail, login, password, adresse, "ROLE_LIBRAIRE");
        System.out.println("Libraire " + l + " cree");
    }

    @Transactional
    public void validerLibraire(Libraire l) {
        l.setRole("ROLE_LIBRAIRE");
        utilisateurRepository.save(l);
        System.out.println("Libraire " + l + " valide");
    }

    @Transactional
    public void rejeterLibraire(Libraire l) {
        utilisateurRepository.delete(l);
        System.out.println("Libraire " + l + " rejete");
    }

    @Transactional
    public void createAndSaveAdministrateur(String prenom, String nom, String mail, String login, String password, String adresse) {
        Utilisateur a = new Utilisateur();
        configureAndSaveUtilisateur(a, prenom, nom, mail, login, password, adresse, "ROLE_ADMIN");
        System.out.println("Administrateur " + a + " cree");
    }

    public List<Libraire> findAllLibraires() {
        return libraireRepository.findAll();
    }

    public Libraire findByIdLibraire(int id) {
        return libraireRepository.findById(id).orElse(null);
    }

    public Utilisateur findById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }
}
