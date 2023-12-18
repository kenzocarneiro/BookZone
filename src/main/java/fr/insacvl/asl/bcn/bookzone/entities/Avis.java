package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Avis {

    @Id @GeneratedValue
    private int idAvis;

    @Setter private int note;
    @Setter private String commentaire;

    @OneToOne(mappedBy = "avis")
    private Exemplaire exemplaire;

}