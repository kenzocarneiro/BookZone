package fr.insacvl.asl.bcn.bookzone.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int idPanier;

    @OneToMany(cascade = CascadeType.ALL)
    @NotNull
    @ToString.Exclude
    private Set<Exemplaire> exemplaires = new HashSet<>();
}
