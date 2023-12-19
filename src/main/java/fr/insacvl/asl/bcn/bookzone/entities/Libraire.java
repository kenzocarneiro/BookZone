package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
public class Libraire extends Utilisateur {

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Exemplaire> exemplaires = new HashSet<>();
}