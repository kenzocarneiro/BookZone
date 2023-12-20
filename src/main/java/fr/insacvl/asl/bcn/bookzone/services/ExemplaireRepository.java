package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
}