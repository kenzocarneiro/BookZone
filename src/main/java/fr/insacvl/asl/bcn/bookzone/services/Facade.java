package fr.insacvl.asl.bcn.bookzone.services;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class Facade {
    @PersistenceContext
    private EntityManager em;

    // TODO : create JPA repository for all entities

    @Transactional
    public Adresse createAdresse(String rue, String ville, int codePostal, String pays, String informationsComplementaires) {
        Adresse a = new Adresse(); // TODO: à remplacer plus tard par le constructeur qui a tous les arguments si possible
        a.setRue(rue);
        a.setVille(ville);
        a.setCodePostal(codePostal);
        a.setPays(pays);
        a.setInformationsComplementaires(informationsComplementaires);
        System.out.println("L'adresse " + a + " a ete cree");
        return a;
    }

    @Transactional
    public Avis createAvis(int note, String commentaire) {
        Avis a = new Avis();
        a.setNote(note);
        a.setCommentaire(commentaire);
        em.persist(a);
        System.out.println("L'avis " + a.getIdAvis() + " a ete cree");
        return a;
    }

    @Transactional
    public Exemplaire createExemplaire(EtatExemplaire etat, Libraire vendeur, Avis avis) {
        Exemplaire e = new Exemplaire();
        e.setEtat(etat);
        e.setVendeur(vendeur);
        addAvisExemplaire(avis, e);
        em.persist(e);
        System.out.println("L'exemplaire " + e.getIdExemplaire() + " a ete cree");
        return e;
    }
    @Transactional
    public void addAvisExemplaire(Avis a, Exemplaire e) {
        e.setAvis(a);
        System.out.println("L'avis " + a.getIdAvis() + " a ete ajoute a l'exemplaire " + e.getIdExemplaire());
    }

    @Transactional
    public Commande createCommande(EtatCommande etat, String description, LocalDate date) {
        Commande c = new Commande();
        c.setEtat(etat);
        c.setDescription(description);
        c.setDate(date);
        em.persist(c);
        System.out.println("La commande " + c + " a ete creee");
        return c;
    }

    @Transactional
    public void addExemplaireDansCommande(Exemplaire e, Commande c) {
        if(c.getListeExemplaires().contains(e)){
            System.out.println("L'exemplaire " + e.getIdExemplaire() + " existe deja dans la commande " + c.getIdCommande());
        }
        else{
            c.getListeExemplaires().add(e);
            e.setCommande(c);
            System.out.println("L'exemplaire " + e.getIdExemplaire() + " a ete ajoute dans la commande " + c.getIdCommande());
        }
    }

    @Transactional
    public void createPersonne(String prenom, String nom) {
        Personne p = new Personne();
        p.setPrenom(prenom);
        p.setNom(nom);
        em.persist(p);
        System.out.println("Personne " + p + " creee");
    }

    @Transactional
    public void createLibraire(String mail, String login, String password) {
        Libraire l = new Libraire();
        l.setMail(mail);
        l.setLogin(login);
        l.setPassword(password);
        l.setRole("user");
        em.persist(l);
        System.out.println("Libraire " + l.getLogin() + " cree");
    }

    @Transactional
    public void createClient(String mail, String login, String password) {
        Client c = new Client();
        c.setMail(mail);
        c.setLogin(login);
        c.setPassword(password);
        c.setRole("user");
        em.persist(c);
        System.out.println("Client " + c.getLogin() + " cree");
    }

    @Transactional
    public void addCommmandetoClient(Commande cmd, Client c) {

        if(c.getListeCommandes().contains(cmd)){
            System.out.println("La commande " + cmd.getIdCommande() + " existe deja pour le client " + c.getId());
        }
        else{
            c.getListeCommandes().add(cmd);
            cmd.setClient(c);
            System.out.println("La commande " + cmd + " a ete ajoute au client " + c.getLogin());
        }

    }

    @Transactional
    public void createUtilisateur(String mail, String login, String password) {
        Utilisateur u = new Utilisateur();
        u.setMail(mail);
        u.setLogin(login);
        u.setPassword(password);
        u.setRole("user");
        em.persist(u);
        System.out.println("Utilisateur " + u.getLogin() + " cree");
    }

    @Transactional
    public void associateAdresseUtilisateur(Adresse a, Utilisateur u) {
        u.setAdresse(a);
        em.persist(a);
        System.out.println("Adresse " + a.toString() +  " associee à " + u.getLogin());
    }

    @Transactional
    public Utilisateur getUtilisateur(String login) {
        String jpql = "SELECT u FROM Utilisateur u WHERE u.login=:login";
        Utilisateur result = em.createQuery(jpql, Utilisateur.class)
                .setParameter("login", login)
                .getSingleResult();

        System.out.println("Utilisateur " + result.getLogin() + " recupere");
        return result;
    }

    @Transactional
    public void createAdministrateur(Utilisateur u) {
        u.setRole("admin");
        em.persist(u);
        System.out.println("Administrateur " + u.getLogin() + " cree");
    }


    // TODO : lier les différentes actions aux roles
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
//    public void ValiderInscription(Libraire l){ // TODO
//        System.out.println("Vous avez valide l'inscription d'un nouveau libraire !");
//    }
//
//    // Pour Client
//    public void inscrireClient(){ // TODO
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
//    public void inscrireLibraire(){ // TODO
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