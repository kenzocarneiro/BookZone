package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraireRepository extends JpaRepository<Libraire, Integer> {
    Libraire findByLogin(String login);
}
