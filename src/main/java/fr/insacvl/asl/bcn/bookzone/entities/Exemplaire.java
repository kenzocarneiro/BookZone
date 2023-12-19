package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Exemplaire {

    @Id @GeneratedValue
    private int idExemplaire;

    @NonNull @Setter private EtatExemplaire etat;
    @Setter private float prixVente;
    @Setter private float fraisPort;

    @ManyToOne
    @NonNull @Setter private Libraire vendeur;

    @OneToOne
    @JoinColumn(name="id_avis")
    @Setter Avis avis;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    @Setter Commande commande;
}