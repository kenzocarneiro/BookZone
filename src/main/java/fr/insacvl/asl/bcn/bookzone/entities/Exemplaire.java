package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private EtatExemplaire etat;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EtatCommande etatCommande;

    private float prixVente;
    private float fraisPort;

    @ManyToOne
    @NotNull
    private Libraire vendeur;

    @OneToOne
    @JoinColumn(name="id_avis")
    Avis avis;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    @ToString.Exclude
    Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_ouvrage")
//    @NotNull // TODO: à enlever plus tard, je l'ai commenté car le code de test ne marchait plus
    Ouvrage ouvrage;
}