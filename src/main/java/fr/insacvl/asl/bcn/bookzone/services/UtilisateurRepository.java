package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByLogin(String login);
}
