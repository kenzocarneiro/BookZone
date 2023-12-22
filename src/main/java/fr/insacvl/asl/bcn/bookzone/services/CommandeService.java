package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Commande;
import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.repositories.CommandeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommandeService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    CommandeRepository commandeRepository;

    @Transactional
    public Commande createCommande(Client client, String description, LocalDate date) {
        Commande c = new Commande();
        c.setDescription(description);
        c.setDate(date);
        c.setClient(client);
        commandeRepository.save(c);
        System.out.println("La commande " + c + " a ete creee");
        return c;
    }

    @Transactional
    public void addExemplaireDansCommande(Exemplaire e, Commande c) {
        c.getExemplaires().add(e);
        e.setCommande(c);
        System.out.println("L'exemplaire " + e.getIdExemplaire() + " a ete ajoute dans la commande " + c.getIdCommande());
    }

    // Pour Administrateur
    @Transactional
    public List<Commande> voirCommandeduMois(){
        LocalDate dateDuJour = LocalDate.now();
        String jpql = "SELECT c FROM Commande c WHERE YEAR(c.date) = :annee AND MONTH(c.date) = :mois";

        return em.createQuery(jpql, Commande.class)
                .setParameter("annee", dateDuJour.getYear())
                .setParameter("mois", dateDuJour.getMonthValue())
                .getResultList();
    }
}
