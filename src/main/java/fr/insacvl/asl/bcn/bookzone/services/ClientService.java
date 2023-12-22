package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Client;
import fr.insacvl.asl.bcn.bookzone.entities.Panier;
import fr.insacvl.asl.bcn.bookzone.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilisateurService utilisateurService;

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
    public void addPanier(Client c, Panier p) {
        c.setPanier(p);
        clientRepository.save(c);
    }

}
