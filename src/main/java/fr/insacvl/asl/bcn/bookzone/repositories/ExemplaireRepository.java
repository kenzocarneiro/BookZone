package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.EtatLivraisonExemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    // List<Exemplaire> findByOuvrage(Integer idOuvrage);
    List<Exemplaire> findByEtatLivraisonExemplaire(EtatLivraisonExemplaire etatLivraisonExemplaire);
}
