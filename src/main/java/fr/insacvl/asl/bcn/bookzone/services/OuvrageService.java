package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.dtos.AvisDTO;
import fr.insacvl.asl.bcn.bookzone.dtos.ExemplaireDTO;
import fr.insacvl.asl.bcn.bookzone.dtos.OuvrageDTO;
import fr.insacvl.asl.bcn.bookzone.entities.*;
import fr.insacvl.asl.bcn.bookzone.repositories.AvisRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.ExemplaireRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.LibraireRepository;
import fr.insacvl.asl.bcn.bookzone.repositories.OuvrageRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OuvrageService {

    @Autowired
    AvisRepository avisRepository;
    @Autowired
    OuvrageRepository ouvrageRepository;
    @Autowired
    ExemplaireRepository exemplaireRepository;
    @Autowired
    LibraireRepository libraireRepository;

    public List<Ouvrage> findAllOuvrages() {
        return ouvrageRepository.findAll();
    }

    public Exemplaire findExemplaireById(int id) {
        return exemplaireRepository.findById(id).orElse(null);
    }

    @Transactional
    public Avis createAvis(int note, String commentaire) {
        Avis a = new Avis();
        a.setNote(note);
        a.setCommentaire(commentaire);
        avisRepository.save(a);
        System.out.println("L'avis " + a + " a ete cree");
        return a;
    }
    @Transactional
    public Ouvrage createOuvrage(String titre, String editeur, int nbPages) {
        Ouvrage o = new Ouvrage();
        o.setTitre(titre);
        o.setEditeur(editeur);
        o.setNbPages(nbPages);
        ouvrageRepository.save(o);
        System.out.println("L'ouvrage " + o + " a ete creee");
        return o;
    }
    @Transactional
    public Exemplaire createExemplaire(Ouvrage o, Libraire vendeur, EtatExemplaire etat) {
        return createExemplaire(o, vendeur, etat, EtatLivraisonExemplaire.EN_VENTE);
    }

    @Transactional
    public Exemplaire createExemplaire(Ouvrage o, Libraire vendeur, EtatExemplaire etat, EtatLivraisonExemplaire etatLivraisonExemplaire) {
        Exemplaire e = new Exemplaire();
        e.setOuvrage(o);
        o.getExemplaires().add(e);
        e.setVendeur(vendeur);
        e.setEtat(etat);
        e.setEtatLivraisonExemplaire(etatLivraisonExemplaire);
        e.setPrixVente(10.0f);
        e.setFraisPort(2.0f);
        exemplaireRepository.save(e);
        System.out.println("L'exemplaire " + e + " a ete cree");
        return e;
    }

    public void saveExemplaire(Exemplaire e) {
        exemplaireRepository.save(e);
    }

    @Transactional
    public void saveExemplaireDto(ExemplaireDTO exemplaireDTO, Libraire libraire) {
        Exemplaire e = new Exemplaire();
        e.setOuvrage(ouvrageRepository.findByTitre(exemplaireDTO.getOuvrage()));
        e.setEtat(exemplaireDTO.getEtat());
        e.setEtatLivraisonExemplaire(EtatLivraisonExemplaire.EN_VENTE);
        e.setVendeur(libraire);
        e.setPrixVente(exemplaireDTO.getPrixVente());
        e.setFraisPort(exemplaireDTO.getFraisPort());
        exemplaireRepository.save(e);
    }

    @Transactional
    public void addAuteurToOuvrage(Personne p, Ouvrage o) {
        o.getAuteurs().add(p);
        System.out.println("Ajout de l'auteur " + p.getPrenom() + " " + p.getNom() + " pour l'ouvrage " + o.getTitre());
    }

//    @Transactional
//    public void addExemplaireToOuvrage(Exemplaire e, Ouvrage o) {
//        e.setOuvrage(o);
//        o.getExemplaires().add(e);
//        System.out.println("Ajout de l'exemplaire " + e.getIdExemplaire() + " pour l'ouvrage " + o.getIdOuvrage());
//    }

    @Transactional
    public void addCategorieToOuvrage(CategorieEnum c, Ouvrage o) {
        o.getCategories().add(c);
    }

    @Transactional
    public void addAvisExemplaire(Avis a, Exemplaire e) {
        e.setAvis(a);
        System.out.println("L'avis " + a.getIdAvis() + " a ete ajoute a l'exemplaire " + e.getIdExemplaire());
    }

    @Transactional
    public Ouvrage getOuvrage(int idOuvrage) {
        return ouvrageRepository.findById(idOuvrage).orElse(null);
    }

    @Transactional
    public Exemplaire getExemplaire(int idExemplaire) {
        return exemplaireRepository.findById(idExemplaire).orElse(null);
    }

    @Transactional
    public List<Exemplaire> getExemplaires() {
        return exemplaireRepository.findAll();
    }

    public void saveOuvrageDto(OuvrageDTO ouvrageDTO) {
        Ouvrage o = new Ouvrage();
        o.setTitre(ouvrageDTO.getTitre());
        o.setEditeur(ouvrageDTO.getEditeur());
        o.setNbPages(ouvrageDTO.getNbPages());
        o.setAuteurs(ouvrageDTO.getAuteurs());
        ouvrageRepository.save(o);
    }

    public Avis saveAvisDTO(AvisDTO avisDTO) {
        Avis a = new Avis();
        a.setNote(avisDTO.getNote());
        a.setCommentaire(avisDTO.getCommentaire());
        avisRepository.save(a);
        return a;
    }
}
