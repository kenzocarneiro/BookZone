package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personne {

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Setter(AccessLevel.NONE)
    private int id;

    @NonNull private String prenom;
    @NonNull private String nom;

    @ManyToMany(mappedBy = "auteurs")
    @ToString.Exclude
    private Set<Ouvrage> ouvrages = new HashSet<>();
}