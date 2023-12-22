package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    int idCommande;

    private String description;
    @NotNull private LocalDate date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @NotNull
    private Set<Exemplaire> exemplaires = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="id_client")
    @NotNull
    private Client client;
}