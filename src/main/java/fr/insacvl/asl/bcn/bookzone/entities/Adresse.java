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
public class Adresse {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int idAdresse;

    @NotNull private String rue;
    @NotNull private String ville;
    private int codePostal;
    @NotNull private String pays;
    private String informationsComplementaires;

    @OneToOne(mappedBy = "adresse")
    @ToString.Exclude Utilisateur utilisateur;

}