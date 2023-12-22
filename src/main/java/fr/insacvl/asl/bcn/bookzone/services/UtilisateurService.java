package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
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
    private UtilisateurRepository utilisateurRepository;

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
            u.setRole("ROLE_CLIENT");
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

    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }
}
