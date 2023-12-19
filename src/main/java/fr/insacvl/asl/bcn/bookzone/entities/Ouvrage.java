package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ouvrage {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int idOuvrage;

    @NonNull String titre;
    @NonNull String editeur;
    int nbPages;

   // TODO : ajouter le set de catégories (enum)

    @OneToMany(mappedBy = "ouvrage")
    @ToString.Exclude
    private Set<Exemplaire> exemplaires = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "ouvrage_auteur",
            joinColumns = @JoinColumn(name = "id_ouvrage"),
            inverseJoinColumns = @JoinColumn(name = "id_auteur")
    )
    private Set<Personne> auteurs = new HashSet<>();
}