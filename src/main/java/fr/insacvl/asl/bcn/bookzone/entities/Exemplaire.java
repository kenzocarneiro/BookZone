package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Exemplaire {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int idExemplaire;

    @Enumerated(EnumType.STRING)
    @NonNull private EtatExemplaire etat;

    private float prixVente;
    private float fraisPort;

    @ManyToOne
    @NonNull private Libraire vendeur;

    @OneToOne
    @JoinColumn(name="id_avis")
    Avis avis;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    @ToString.Exclude
    Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_ouvrage")
    @NonNull Ouvrage ouvrage;
}