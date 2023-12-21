package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.UtilisateurDTO;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void saveUtilisateurDto(UtilisateurDTO utilisateurDTO) {
        Utilisateur u;
        if (utilisateurDTO.isLibraire()) {
            u = new Libraire();
            u.setRole("ROLE_LIBRAIRE");
        }
        else {
            u = new Utilisateur();
            u.setRole("ROLE_USER");
        }
        u.setPrenom(utilisateurDTO.getPrenom());
        u.setNom(utilisateurDTO.getNom());
        u.setMail(utilisateurDTO.getMail());
        u.setLogin(utilisateurDTO.getLogin());
        u.setPassword(passwordEncoder.encode(utilisateurDTO.getPassword()));
        utilisateurRepository.save(u);
    }

}
