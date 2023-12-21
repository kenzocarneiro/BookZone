package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
}
