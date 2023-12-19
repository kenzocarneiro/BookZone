package fr.insacvl.asl.bcn.bookzone.services;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
public class Facade {
    @PersistenceContext
    private EntityManager em;

    // TODO : create JPA repository for all entities ?

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
        System.out.println("L'avis " + a + " a ete cree");
        return a;
    }
    @Transactional
    public Ouvrage createOuvrage(String titre, String editeur, int nbPages) {
        Ouvrage o = new Ouvrage();
        o.setTitre(titre);
        o.setEditeur(editeur);
        o.setNbPages(nbPages);
        em.persist(o);
        System.out.println("L'ouvrage " + o + " a ete creee");
        return o;
    }
    @Transactional
    public Exemplaire createExemplaire(EtatExemplaire etat, Libraire vendeur) {
        Exemplaire e = new Exemplaire();
        e.setEtat(etat);
        e.setVendeur(vendeur);
        em.persist(e);
        System.out.println("L'exemplaire " + e + " a ete cree");
        return e;
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
    public Personne createPersonne(String prenom, String nom) {
        Personne p = new Personne();
        p.setPrenom(prenom);
        p.setNom(nom);
        em.persist(p);
        System.out.println("Personne " + p + " creee");
        return p;
    }

    @Transactional
    public void createUtilisateur(String mail, String login, String password) {
        Utilisateur u = new Utilisateur();
        u.setMail(mail);
        u.setLogin(login);
        u.setPassword(password);
        u.setRole("user");
        em.persist(u);
        System.out.println("Utilisateur " + u + " cree");
    }
    @Transactional
    public void createLibraire(String mail, String login, String password) {
        Libraire l = new Libraire();
        l.setMail(mail);
        l.setLogin(login);
        l.setPassword(password);
        l.setRole("user");
        em.persist(l);
        System.out.println("Libraire " + l + " cree");
    }
    @Transactional
    public void createClient(String mail, String login, String password) {
        Client c = new Client();
        c.setMail(mail);
        c.setLogin(login);
        c.setPassword(password);
        c.setRole("user");
        em.persist(c);
        System.out.println("Client " + c + " cree");
    }

    @Transactional
    public void createAdministrateur(Utilisateur u) {
        u.setRole("admin");
        em.persist(u);
        System.out.println("Administrateur " + u.getLogin() + " cree");
    }

    @Transactional
    public void addAuteurToOuvrage(Personne p, Ouvrage o) {
        o.getAuteurs().add(p);
        p.getOuvrages().add(o);
        System.out.println("Ajout de l'auteur " + p.getPrenom() + " " + p.getNom() + " pour l'ouvrage " + o.getTitre());
    }

    @Transactional
    public void addExemplaireToOuvrage(Exemplaire e, Ouvrage o) {
        e.setOuvrage(o);
        o.getExemplaires().add(e);
        System.out.println("Ajout de l'exemplaire " + e.getIdExemplaire() + " pour l'ouvrage " + o.getIdOuvrage());
    }

    @Transactional
    public void addAvisExemplaire(Avis a, Exemplaire e) {
        e.setAvis(a);
        System.out.println("L'avis " + a.getIdAvis() + " a ete ajoute a l'exemplaire " + e.getIdExemplaire());
    }

    @Transactional
    public void addExemplaireDansCommande(Exemplaire e, Commande c) {

        c.getExemplaires().add(e);
        e.setCommande(c);
        System.out.println("L'exemplaire " + e.getIdExemplaire() + " a ete ajoute dans la commande " + c.getIdCommande());
    }

    @Transactional
    public void addCommmandetoClient(Commande cmd, Client c) {

        c.getCommandes().add(cmd);
        cmd.setClient(c);
        System.out.println("La commande " + cmd.getIdCommande() + " a ete ajoute au client " + c.getLogin());

    }

    @Transactional
    public void associateAdresseUtilisateur(Adresse a, Utilisateur u) {
        u.setAdresse(a);
        em.persist(a);
        System.out.println("Adresse " + a.getIdAdresse() +  " associee à " + u.getLogin());
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
    public void addCategorieToOuvrage(CategorieEnum c, Ouvrage o) {
        o.getCategories().add(c);
    }


    // TODO : lier les différentes actions aux roles
    // Pour Administrateur
    @Transactional
    public List<Commande> voirCommandeduMois(){

        LocalDate dateDuJour = LocalDate.now();

        String jpql = "SELECT c FROM Commande c WHERE YEAR(c.date) = :annee AND MONTH(c.date) = :mois";
        List<Commande> resultList = em.createQuery(jpql, Commande.class)
                .setParameter("annee", dateDuJour.getYear())
                .setParameter("mois", dateDuJour.getMonthValue())
                .getResultList();

        return resultList;
    }
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
//        int indexCommande = client.getCommandes().indexOf(c);
//
//        if (indexCommande != -1) {
//            int indexExemplaire = client.getCommandes().get(indexCommande).getExemplaires().indexOf(e);
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
//        int index = l.getExemplaires().indexOf(e);
//
//        if (index != -1) {
//            l.getExemplaires().get(index).setPrixVente(prixVente);
//        } else {
//            System.out.println("L'exemplaire n'est pas présent dans la liste.");
//        }
//    }
//
//    @Transactional
//    public void setFraisPort(Libraire l, float fraisPort, Exemplaire e){
//
//        int index = l.getExemplaires().indexOf(e);
//
//        if (index != -1) {
//            l.getExemplaires().get(index).setPrixVente(fraisPort);
//        } else {
//            System.out.println("L'exemplaire n'est pas présent dans la liste.");
//        }
//    }
}