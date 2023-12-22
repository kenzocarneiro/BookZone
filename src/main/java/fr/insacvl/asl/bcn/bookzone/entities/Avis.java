package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Avis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int idAvis;

    private int note;
    private String commentaire;

    @OneToOne(mappedBy = "avis")
    @ToString.Exclude private Exemplaire exemplaire;

}