package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Adresse {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int idAdresse;

    @NonNull private String rue;
    @NonNull private String ville;
    private int codePostal;
    @NonNull private String pays;
    private String informationsComplementaires;

    @OneToOne(mappedBy = "adresse")
    @ToString.Exclude Utilisateur utilisateur;

}