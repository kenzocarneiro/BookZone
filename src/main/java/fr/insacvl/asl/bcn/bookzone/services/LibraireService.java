package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Commande;
import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Ouvrage;
import fr.insacvl.asl.bcn.bookzone.repositories.CommandeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LibraireService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    CommandeRepository commandeRepository;

    @Transactional
    public Set<Exemplaire> getExemplairesLibraire(Libraire l) {
        return l.getExemplaires();
    }

    @Transactional
    public Set<Ouvrage> getOuvragesLibraire(Libraire l) {
        Set<Ouvrage> ouvrages = new HashSet<>();
        for(Exemplaire exemplaire : l.getExemplaires()){
            ouvrages.add(exemplaire.getOuvrage());
        }
        return ouvrages;
    }

    public Set<Exemplaire> getExemplairesCommandesDuLibraire(Libraire libraire) {

        Set<Exemplaire> allExemplairesCommandesDuLibraire = new HashSet<>();
        List<Commande> toutesLesCommandes = commandeRepository.findAll();
        String jpql = "SELECT e FROM Exemplaire e WHERE e.commande = :commande AND e.vendeur = :libraire";

        for (Commande commande : toutesLesCommandes) {
            List<Exemplaire> exemplairesCommandesDuLibraire = em.createQuery(jpql, Exemplaire.class)
                    .setParameter("commande", commande)
                    .setParameter("libraire", libraire)
                    .getResultList();

            allExemplairesCommandesDuLibraire.addAll(exemplairesCommandesDuLibraire);
        }

        return allExemplairesCommandesDuLibraire;
    }

    public double getNoteMoyenne(Libraire libraire) {

        String jpql = "SELECT AVG(e.avis.note) FROM Exemplaire e JOIN e.commande c WHERE e.vendeur = :libraire AND e.commande IS NOT NULL";

        return em.createQuery(jpql, Double.class)
                    .setParameter("libraire", libraire)
                    .getSingleResult();
    }

    @Transactional
    public void setPrixVente(Libraire l, float prixVente, Exemplaire e){
        if(e != null){
            l.getExemplaires().stream()
                    .filter(element -> element.equals(e))
                    .findFirst()
                    .ifPresent(exemplaireRecherche -> exemplaireRecherche.setPrixVente(prixVente));
        }
    }

    @Transactional
    public void setFraisPort(Libraire l, float fraisPort, Exemplaire e){
        if(e != null){
            l.getExemplaires().stream()
                    .filter(element -> element.equals(e))
                    .findFirst()
                    .ifPresent(exemplaireRecherche -> exemplaireRecherche.setFraisPort(fraisPort));
        }
    }
}
