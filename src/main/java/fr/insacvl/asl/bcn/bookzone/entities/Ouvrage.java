package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ouvrage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int idOuvrage;

    @NotNull String titre;
    @NotNull String editeur;
    int nbPages;

    @Enumerated(EnumType.STRING)
    private Set<CategorieEnum> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ouvrage_auteur",
            joinColumns = @JoinColumn(name = "id_ouvrage"),
            inverseJoinColumns = @JoinColumn(name = "id_auteur")
    )
    private Set<Personne> auteurs = new HashSet<>();
}