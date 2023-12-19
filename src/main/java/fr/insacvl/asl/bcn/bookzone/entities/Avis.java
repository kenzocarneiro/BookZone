package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Avis {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int idAvis;

    private int note;
    private String commentaire;

    @OneToOne(mappedBy = "avis")
    @ToString.Exclude private Exemplaire exemplaire;

}