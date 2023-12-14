package fr.insacvl.asl.bcn.bookzone.services;
//import fr.insacvl.asl.bcn.bookzone.entities.Client;
//import fr.insacvl.asl.bcn.bookzone.entities.Commande;
//import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
//import fr.insacvl.asl.bcn.bookzone.entities.Libraire;
import fr.insacvl.asl.bcn.bookzone.entities.Administrateur;
import fr.insacvl.asl.bcn.bookzone.entities.Personne;
import fr.insacvl.asl.bcn.bookzone.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class Facade {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void createPersonne(String prenom, String nom) {
        Personne pers = new Personne();
        pers.setPrenom(prenom);
        pers.setNom(nom);
        em.persist(pers);
        System.out.println("Personne creee");
    }


    @Transactional
    public void createUtilisateur(String mail, String login, String password) {
        Utilisateur u = new Utilisateur();
        u.setMail(mail);
        u.setLogin(login);
        u.setPassword(password);
        em.persist(u);
        System.out.println("Utilisateur cree");
    }

    @Transactional
    public void createAdministrateur(String mail, String login, String password) {
        Administrateur admin = new Administrateur();
        admin.setMail(mail);
        admin.setLogin(login);
        admin.setPassword(password);
        em.persist(admin);
        System.out.println("Administrateur cree");
    }
//    // TODO : lier les différentes actions aux roles
//
//    // Pour Administrateur
//    @Transactional
//    public List<Commande> voirCommandeduMois(){
//
//        LocalDate dateDuJour = LocalDate.now();
//
//        String jpql = "SELECT c FROM Commande c WHERE YEAR(c.date) = :annee AND MONTH(c.date) = :mois";
//        List<Commande> resultList = em.createQuery(jpql, Commande.class)
//                .setParameter("annee", dateDuJour.getYear())
//                .setParameter("mois", dateDuJour.getMonth())
//                .getResultList();
//
//        return resultList;
//    }
//
//    public void ValiderInscription(Libraire l){
//        System.out.println("Vous avez valide l'inscription d'un nouveau libraire !");
//    }
//
//    // Pour Client
//    public void inscrireClient(){
//        System.out.println("Bravo, vous etes inscrit sur la plateforme !");
//    }
//
//    @Transactional
//    public void noterExemplaire(Client client, Commande c, Exemplaire e, int note){
//
//        int indexCommande = client.getListeCommandes().indexOf(c);
//
//        if (indexCommande != -1) {
//            int indexExemplaire = client.getListeCommandes().get(indexCommande).getListeExemplaires().indexOf(e);
//
//            if (indexExemplaire != -1) {
//                setNote(e, note);
//            }
//            else {
//                System.out.println("L'exemplaire n'existe pas dans la commande.");
//            }
//        } else {
//            System.out.println("La commande n'est pas présente dans la liste.");
//        }
//    }
//
//    // Pour Exemplaire
//    @Transactional
//    public void setNote(Exemplaire e, int note){
//
//        if(note>=1 && note<=5){
//            e.getAvis().setNote(note);
//        }
//        else{
//            System.out.println("Note incorrecte. Veuillez mettre une note entre 1 et 5");
//        }
//    }
//
//    // Pour Libraire
//    public void inscrireLibraire(){
//        System.out.println("Bravo, vous etes inscrit sur la plateforme !");
//    }
//
//
//    @Transactional
//    public void setPrixVente(Libraire l, float prixVente, Exemplaire e){
//
//        int index = l.getListeExemplaires().indexOf(e);
//
//        if (index != -1) {
//            l.getListeExemplaires().get(index).setPrixVente(prixVente);
//        } else {
//            System.out.println("L'exemplaire n'est pas présent dans la liste.");
//        }
//    }
//
//    @Transactional
//    public void setFraisPort(Libraire l, float fraisPort, Exemplaire e){
//
//        int index = l.getListeExemplaires().indexOf(e);
//
//        if (index != -1) {
//            l.getListeExemplaires().get(index).setPrixVente(fraisPort);
//        } else {
//            System.out.println("L'exemplaire n'est pas présent dans la liste.");
//        }
//    }
}