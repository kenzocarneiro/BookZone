package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personne {

    @Id @GeneratedValue
    private int id;

    @Setter private String prenom;
    @Setter private String nom;

    public Personne() {
    }
}