package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Commande {

    @Id @GeneratedValue
    int idCommande;

    @Setter private EtatCommande etat;
    @Setter private String description;
    @NonNull @Setter private LocalDate date;

    @OneToMany(mappedBy = "idExemplaire", cascade = CascadeType.ALL)
    @ToString.Exclude @NonNull @Setter private List<Exemplaire> listeExemplaires = new ArrayList<>();

}