package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
public class Libraire extends Utilisateur {

    @OneToMany(mappedBy = "idExemplaire", cascade = CascadeType.ALL)
    private List<Exemplaire> listeExemplaires = new ArrayList<>();
}