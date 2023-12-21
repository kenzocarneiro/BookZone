package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Personne;
import fr.insacvl.asl.bcn.bookzone.repositories.PersonneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonneService {
    @Autowired
    PersonneRepository personneRepository;

    @Transactional
    public Personne createPersonne(String prenom, String nom) {
        Personne p = new Personne();
        p.setPrenom(prenom);
        p.setNom(nom);
        personneRepository.save(p);
        System.out.println("Personne " + p + " creee");
        return p;
    }
}
