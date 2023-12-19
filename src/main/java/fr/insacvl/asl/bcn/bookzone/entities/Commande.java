package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Commande {

    @Id @GeneratedValue
    @Setter(AccessLevel.NONE)
    int idCommande;

    @Enumerated(EnumType.STRING)
    EtatCommande etat;

    private String description;
    @NonNull private LocalDate date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @NonNull
    @ToString.Exclude
    private Set<Exemplaire> exemplaires = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="id_client")
    @ToString.Exclude
    private Client client;
}