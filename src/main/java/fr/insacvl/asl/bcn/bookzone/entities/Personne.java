package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull private String prenom;
    @NotNull private String nom;
}