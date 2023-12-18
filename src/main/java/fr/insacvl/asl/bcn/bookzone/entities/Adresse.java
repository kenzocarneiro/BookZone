package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.NonFinal;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Adresse {

    @Id @GeneratedValue
    private int idAdresse;

    @NonNull @Setter private String rue;
    @NonNull @Setter private String ville;
    @Setter private int codePostal;
    @NonNull @Setter private String pays;
    @Setter private String informationsComplementaires;

    @OneToOne(mappedBy = "adresse")
    Utilisateur utilisateur;

}