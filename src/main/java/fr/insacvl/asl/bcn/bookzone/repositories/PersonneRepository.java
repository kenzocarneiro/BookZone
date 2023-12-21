package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Integer> {
}
