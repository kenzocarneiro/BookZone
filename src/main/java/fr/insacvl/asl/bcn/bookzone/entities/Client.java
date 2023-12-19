package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
public class Client extends Utilisateur {

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    Set<Commande> commandes = new HashSet<>();

}