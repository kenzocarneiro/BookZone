package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @ToString.Exclude @NonNull @Setter private List<Exemplaire> listeExemplaires = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id_client")
    @Setter Client client;

}