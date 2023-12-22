package fr.insacvl.asl.bcn.bookzone.services;

import fr.insacvl.asl.bcn.bookzone.entities.Exemplaire;
import fr.insacvl.asl.bcn.bookzone.entities.Panier;
import fr.insacvl.asl.bcn.bookzone.repositories.PanierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanierService {

    @Autowired
    OuvrageService ouvrageService;
    @Autowired
    PanierRepository panierRepository;

    public Panier findById(int id) {
        return panierRepository.findById(id).orElse(null);
    }

    @Transactional
    public Panier createPanier() {
        Panier p = new Panier();
        panierRepository.save(p);
        return p;
    }

    @Transactional
    public void deletePanier(Panier p) {
        panierRepository.delete(p);
    }

    @Transactional
    public void deletePanierFromId(int id) {
        panierRepository.deleteById(id);
    }

    @Transactional
    public void addExemplaireFromId(int panierId, int idExemplaire) {
        boolean alreadyIn = false;
        Panier p = findById(panierId);
        for (Exemplaire exemplaire : p.getExemplaires()) {
            if (exemplaire.getIdExemplaire() == idExemplaire) {
                alreadyIn = true;
                break;
            }
        }
        if (!alreadyIn) {
            p.getExemplaires().add(ouvrageService.findExemplaireById(idExemplaire));
        }
        panierRepository.save(p);
    }

    @Transactional
    public void removeExemplaireFromId(int panierId, int idExemplaire) {
        Exemplaire exemplaireToRemove = null;
        Panier p = findById(panierId);
        for (Exemplaire exemplaire : p.getExemplaires()) {
            if (exemplaire.getIdExemplaire() == idExemplaire) {
                exemplaireToRemove = exemplaire;
                break;
            }
        }
        p.getExemplaires().remove(exemplaireToRemove);
        panierRepository.save(p);
    }
}
