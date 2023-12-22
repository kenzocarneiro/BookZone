package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.CategorieEnum;
import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.entities.Personne;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OuvrageRepository extends JpaRepository<Ouvrage, Integer> {
    // List<Ouvrage> findByTitreContaining(String titre);
    // List<Ouvrage> findByTitreContainingAndCategories(String titre, List<CategorieEnum> categories);
    // List<Ouvrage> findByCategories(List<CategorieEnum> categories);
    // List<Ouvrage> findByAuteurs(List<Personne> auteurs);
}
