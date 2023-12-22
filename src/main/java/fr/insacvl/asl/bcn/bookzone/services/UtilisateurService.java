package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Adresse;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import fr.insacvl.asl.bcn.bookzone.repositories.AdresseRepository;
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
    private AdresseRepository adresseRepository;

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
        utilisateurDTO.setAdresse("TODO");
//        if (u.getRole().equals("ROLE_FUTUR_LIBRAIRE")) {
//            utilisateurDTO.setLibraire(true);
//        }
//        else {
//            utilisateurDTO.setLibraire(false);
//        }
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
    public Adresse createAdresse(String rue, String ville, int codePostal, String pays, String informationsComplementaires) {
        Adresse a = new Adresse(); // TODO: à remplacer plus tard par le constructeur qui a tous les arguments si possible
        a.setRue(rue);
        a.setVille(ville);
        a.setCodePostal(codePostal);
        a.setPays(pays);
        a.setInformationsComplementaires(informationsComplementaires);
        System.out.println("L'adresse " + a + " a ete cree");
        return a;
    }

    @Transactional
    public void createAndSaveClient(String prenom, String nom, String mail, String login, String password) {
        Client c = new Client();
        configureAndSaveUtilisateur(c, prenom, nom, mail, login, password, "ROLE_USER");
        System.out.println("Client " + c + " cree");
    }

    @Transactional
    public void createAndSaveFuturLibraire(String prenom, String nom, String mail, String login, String password) {
        Libraire l = new Libraire();
        configureAndSaveUtilisateur(l, prenom, nom, mail, login, password, "ROLE_FUTUR_LIBRAIRE");
        System.out.println("Libraire " + l + " cree");
    }

    @Transactional
    public void createAndSaveLibraireValide(String prenom, String nom, String mail, String login, String password) {
        Libraire l = new Libraire();
        configureAndSaveUtilisateur(l, prenom, nom, mail, login, password, "ROLE_LIBRAIRE");
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
