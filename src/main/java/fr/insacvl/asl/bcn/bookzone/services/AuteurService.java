package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Auteur;
import fr.insacvl.asl.bcn.bookzone.repositories.AuteurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuteurService {
    @Autowired
    AuteurRepository auteurRepository;

    @Transactional
    public Auteur createAuteur(String prenom, String nom) {
        Auteur a = new Auteur();
        a.setPrenom(prenom);
        a.setNom(nom);
        auteurRepository.save(a);
        System.out.println("Auteur " + a + " creee");
        return a;
    }
}
