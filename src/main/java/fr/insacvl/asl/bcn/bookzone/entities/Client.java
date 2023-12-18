package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
public class Client extends Utilisateur {

    @OneToMany(mappedBy = "idCommande")
    List<Commande> listeCommandes = new ArrayList<>();

}