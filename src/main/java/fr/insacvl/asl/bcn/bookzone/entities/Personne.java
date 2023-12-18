package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personne {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @NonNull @Setter private String prenom;
    @NonNull @Setter private String nom;

}