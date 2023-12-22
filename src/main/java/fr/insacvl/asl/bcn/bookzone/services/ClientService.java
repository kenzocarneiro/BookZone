package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Commande;
import fr.insacvl.asl.bcn.bookzone.entities.Panier;
import fr.insacvl.asl.bcn.bookzone.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CommandeService commandeService;
    @Autowired
    PanierService panierService;

    public Client findByLogin(String login) {
        return clientRepository.findByLogin(login);
    }

    @Transactional
    public void createAndSaveClient(String prenom, String nom, String mail, String login, String password, String adresse) {
        Client c = new Client();
        utilisateurService.configureAndSaveUtilisateur(c, prenom, nom, mail, login, password, adresse, "ROLE_CLIENT");
        System.out.println("Client " + c + " cree");
    }

    @Transactional
    public void addPanierFromId(Client c, Integer panierId) {
        c.setPanier(panierService.findById(panierId));
        clientRepository.save(c);
    }

    @Transactional
    public void acheterPanier(Client c) {
        Panier p = c.getPanier();
        Commande commande = commandeService.createCommande(c, LocalDate.now());

        p.getExemplaires().forEach(e -> commandeService.addExemplaireDansCommande(e, commande));
        panierService.deletePanier(p);

        Panier newPanier = panierService.createPanier();
        c.setPanier(newPanier);
        clientRepository.save(c);
    }

}
