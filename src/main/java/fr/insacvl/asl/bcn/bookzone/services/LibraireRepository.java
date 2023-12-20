package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraireRepository extends JpaRepository<Libraire, Integer> {
    public Libraire findByLogin(String login);
}
